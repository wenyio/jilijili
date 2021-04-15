<template>
	<view class="forget">
		
		<view class="content">
			<!-- 主体 -->
			<view class="main">
				<view class="tips">若你忘记了密码，可在此重置新密码。</view>
				<wInput
					v-model="emailData"
					type="text"
					maxlength="32"
					placeholder="请输入邮箱地址"
				></wInput>
				<wInput
					v-model="passData"
					type="password"
					maxlength="18"
					placeholder="请输入新密码"
					isShowPass
				></wInput>
				
				<wInput
					v-model="verCode"
					type="number"
					maxlength="6"
					placeholder="验证码"
					
					isShowCode
					codeText="获取重置码"
					setTime="30"
					ref="runCode"
					@setCode="getVerCode()"
				></wInput>
			</view>
			
			<wButton 
				text="重置密码"
				:rotate="isRotate" 
				@click.native="startRePass()"
			></wButton>

		</view>
	</view>
</template>

<script>
	var _this;
	import wInput from '../../components/watch-login/watch-input.vue' //input
	import wButton from '../../components/watch-login/watch-button.vue' //button
	export default {
		data() {
			return {
				baseUrl: this.GLOBAL.baseURL,
				emailData: "", //电话
				passData: "", //密码
				verCode:"", //验证码
				isRotate: false, //是否加载旋转
			}
		},
		components:{
			wInput,
			wButton
		},
		mounted() {
			_this= this;
		},
		methods: {
			getVerCode(){
				//获取验证码
				var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
				if (!regEmail.test(_this.emailData)) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '邮箱不正确'
				    });
				    return false;
				}
				// console.log("获取验证码")
				// 加载
				uni.showLoading({
				})
				// 判断是否重复
				uni.request({
					url: _this.baseUrl + "/users/isRepeat/" + _this.emailData,
					success(res) {
						// console.log(res)
						if(res.data.code != 200) {
							// console.log("获取验证码")
							// 获取验证码
							uni.request({
								url: _this.baseUrl + "/users/sendMsgCode/" + _this.emailData,
								success(res) {
									// console.log(res)
									if(res.data.code == 200) {
										uni.hideLoading();
										_this.$refs.runCode.$emit('runCode'); //触发倒计时（一般用于请求成功验证码后调用）
										uni.showToast({
											icon: 'none',
											position: 'bottom',
											title: res.data.msg
										});
									} else {
										uni.hideLoading();
										uni.showToast({
											icon: 'none',
											position: 'bottom',
											title: res.data.msg
										});
									}
								}
							})
						}
						else {
							uni.hideLoading();
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: "该邮箱还未注册! "
							});
							// return false;
						}
					}
				})
				
				setTimeout(function () {
				    uni.hideLoading();
				}, 2000);
			},
			startRePass() {
				//重置密码
				if(this.isRotate){
					//判断是否加载中，避免重复点击请求
					return false;
				}
				var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
				if (!regEmail.test(_this.emailData)) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '邮箱不正确'
				    });
				    return false;
				}
			    if (this.passData.length < 6) {
			        uni.showToast({
			            icon: 'none',
						position: 'bottom',
			            title: '密码不正确'
			        });
			        return false;
			    }
				if (this.verCode.length != 6) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '验证码不正确!'
				    });
				    return false;
				}
				// console.log("重置密码成功")
				_this.isRotate=true // 加载
				// 注册请求
				uni.request({
					url: _this.baseUrl + "/users/forget?code=" + _this.verCode,
					method: "POST",
					data: {
						password : _this.passData,
						email: _this.emailData
					},
					success(res) {
						if(res.data.code == 200) {
							// console.log(res.data.result)
							// uni.showToast({
							//     icon: 'none',
							// 	position: 'bottom',
							//     title: res.data.msg
							// });
							uni.showModal({
								content: "重置成功!",
								showCancel: false,
								confirmText: "登录",
								success(confirm) {
									// 点击登录按钮进行登录 保存用户信息
									if(confirm) {
										uni.setStorageSync("user_key", res.data.result)
										uni.reLaunch({
											url: "../index/index"
										})
									}
								}
							});	
							_this.isRotate=false // 停止加载
						}
						else {
							uni.showToast({
							    icon: 'none',
								position: 'bottom',
							    title: res.data.msg
							});
							_this.isRotate=false // 停止加载
						}
					}
				})	
			}
		}
	}
</script>

<style>
	@import url("../../components/watch-login/css/icon.css");
	@import url("./css/main.css");
</style>

