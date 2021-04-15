<template>
	<view class="page">
		<view class="img-view">
			<image style="width: 100%;" mode="widthFix" src="../../static/icons/bj.jpg"></image>
		</view>
		<view style="display: flex;justify-content: center;align-items: center;">
			<view class="user_info">
				<view style="width: 100%;display: flex;justify-content: center;">
					<image :src="user.icon" mode="widthFix" @click="chooseIcon()"></image>
				</view>
				<text style="color:#CCCCCC;font-size: 26rpx;display: flex;justify-content: center;">{{user.signature}}</text>
				<view style="display: flex;justify-content: space-around;padding: 50rpx;">
					<!-- 动态 -->
					<view class="dongtai" style="display: flex;flex-direction: column;align-items: center;">
						<text>22</text>
						<text>动态</text>
					</view>
					<!-- 关注 -->
					<view class="guanzhu" style="display: flex;flex-direction: column;align-items: center;">
						<text>11</text>
						<text>关注</text>
					</view>
					<!-- 粉丝 -->
					<view class="fensi" style="display: flex;flex-direction: column;align-items: center;">
						<text>22</text>
						<text>粉丝</text>
					</view>
				</view>
			</view>
		</view>
		<text style="color: #E06F93;font-size: 26rpx;">更多功能敬请期待~</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				baseUrl: this.GLOBAL.baseURL,
				user: '',
				imgUrls: '',
				userIcon: '',
				orderIcon: '',
			}
		},
		mounted() {
			this.isLogin()
		},
		onShow() {
			this.user = uni.getStorageSync("user_key")
		},
		methods: {
			updateIcon() {
				let that = this
				// console.log(that.user)
				// var user = that.user
				wx.uploadFile({
					url: that.baseUrl + '/users/updateIcon/' + that.user.id,
					filePath: that.user.icon, 
					name:'file',
					// fromData: user, // 把user传上去, 再后端修改头像路径
					// header: {"content-type": "multipart/form-data"},
					success: (res) =>{
						// console.log(res.data)
						var result = res.data
						var jsonStr= res.data;
						jsonStr = jsonStr.replace(" ","");
						if(typeof jsonStr!= 'object'){
						  jsonStr= jsonStr.replace(/\ufeff/g,"");//重点
						  var jj = JSON.parse(jsonStr);
						  res.data = jj;
						}
						if (res.data.code == 200){
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: res.data.msg
							})
							uni.setStorageSync("user_key", res.data.result)
							that.user = uni.getStorageSync("user_key")
							console.log(that.user)
						} else {
							uni.showToast({
								icon: 'none',
								position: 'bottom',
								title: res.data.msg
							})
						}
					}
				}) 
			},
			chooseIcon() {
				// console.log(11)
				let that = this
				uni.chooseImage({
					count: 1, // 一张头像
					sizeType: ['original'], // 原图
					sourceType:['album'], // 从相册选择
					success: function (res) {
						that.userIcon = res.tempFilePaths[0];
						// 备份院头像路径
						// console.log(res.tempFilePaths[0])
						that.orderIcon = that.user.icon
						// 更换当前头像为新头像
						that.user.icon = that.userIcon
						// 是否确认修改
						uni.showModal({
							content: "确定更换吗~",
							success(res) {
								if(res.confirm) {
									// 上传到后台
									that.updateIcon()
								} else {
									// 恢复旧头像
									that.user.icon = that.orderIcon
								}
							}
						})
					}
				})
				// console.log(that.userIcon)
				// uni.uploadFile({
				// 	url: '',
				// 	filePath: that.userIcon
				// })
			},
			isLogin(){
				//判断缓存中是否登录过，直接登录
				try{
					const value = uni.getStorageSync("user_key")
					if (!value) {
						uni.redirectTo({
							url: "../login/login"
						})
					}
				}catch(e){
					//TODO handle the exception
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '意外错误!'
					})
				}
			},
			radioChange(e) {
				this.showSwiper = e.detail.value === "swiper";
			}
		}
	}
</script>

<style>
	.img-view {
		width: 100%;
	}
	.user_info{
		z-index: 5;
		width: 85%;
		height: 300rpx;
		margin-top: -221rpx;
		background-color: #fff;
		border-radius: 3%;
		box-shadow:0 0 12rpx #C0C0C0;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}
	.user_info image{
		margin-top: -90rpx;
		width: 180rpx;
		border-radius: 50%;
		border: 5rpx solid #fff;
	}
</style>
