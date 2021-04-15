<template>
	<view class="register">
	
		<view class="content">
			<!-- 头部logo -->
			<view class="header">
				<image src="../../static/icons/logo2.png" mode="widthFix"></image>
			</view>
			<!-- 主体 -->
			<view class="main">
				<wInput
						v-model="emailData"
						type="text"
						maxlength="22"
						placeholder="邮箱"
					></wInput>
					<wInput
						v-model="passData"
						type="password"
						maxlength="18"
						placeholder="登录密码"
						isShowPass
					></wInput>
					<wInput
						v-model="verCode"
						type="number"
						maxlength="6"
						placeholder="验证码"
						
						isShowCode
						ref="runCode"
						@setCode="getVerCode()"
					></wInput>
					
				</view>
				
				<wButton 
					text="注 册"
					:rotate="isRotate" 
					@click.native="startReg()"
				></wButton>
			
			<!-- 底部信息 -->
			<view class="footer">
				<text 
					@tap="isShowAgree" 
					class="cuIcon"
					:class="showAgree?'cuIcon-radiobox':'cuIcon-round'"
				>同意</text>
				<!-- 协议地址 -->
				<navigator url="" open-type="navigate">《协议》</navigator>
			</view>
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
				emailData:'', // 用户/邮箱
				passData:'', //密码
				verCode:"", //验证码
				showAgree:true, //协议是否选择
				isRotate: false, //是否加载旋转
			}
		},
		components:{
			wInput,
			wButton,
		},
		mounted() {
			_this= this;
		},
		methods: {
			isShowAgree(){
				//是否选择协议
				_this.showAgree = !_this.showAgree;
			},
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
				
				// 加载
				uni.showLoading({
				})
				// 判断是否重复
				uni.request({
					url: _this.baseUrl + "/users/isRepeat/" + _this.emailData,
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							// console.log("获取验证码")
							// 获取验证码
							uni.request({
								url: _this.baseUrl + "/users/sendMsgCode/" + _this.emailData,
								success(res) {
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
								title: res.data.msg
							});
							// return false;
						}
					}
				})
				
				setTimeout(function () {
				    uni.hideLoading();
				}, 2000);
				
				// setTimeout(function(){
				// 	_this.$refs.runCode.$emit('runCode',0); //假装模拟下需要 终止倒计时
				// 	uni.showToast({
				// 	    icon: 'none',
				// 		position: 'bottom',
				// 	    title: '模拟倒计时终止'
				// 	});
				// },3000)
			},
		    /**
			 * 开始注册
			 */
			startReg() {
				//注册
				if(this.isRotate){
					//判断是否加载中，避免重复点击请求
					return false;
				}
				if (this.showAgree == false) {
				    uni.showToast({
				        icon: 'none',
						position: 'bottom',
				        title: '请先同意《协议》'
				    });
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
				        title: '验证码不正确'
				    });
					_this.$refs.runCode.$emit('runCode',0); // 终止倒计时
				    return false;
				}
				
				_this.isRotate=true // 加载
				// 注册请求
				uni.request({
					url: _this.baseUrl + "/users?code=" + _this.verCode,
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
								content: res.data.msg,
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
				// console.log("注册成功")
				// _this.isRotate=true
				// setTimeout(function(){
				// 	_this.isRotate=false
				// },3000)
		    },
		}
	}
</script>

<style>
	@import url("../../components/watch-login/css/icon.css");
	@import url("./css/main.css");
</style>