<template>
	<view style="background-color: #EEEEEE; margin-top: 20rpx;">
		<view class="set_menu1">
			<uni-list style="margin-top: 20rpx;">
				<navigator url="../info/info" v-if="user">
					<uni-list-item title="账号资料"/>
				</navigator>
				<uni-list-item title="安全隐私"  @click="nothing()"/>
				<uni-list-item title="收货信息"  @click="nothing()"/>
			</uni-list>
		</view>
		<view class="set_menu2" @click="nothing()">
			<uni-list style="margin-top: 20rpx;">
				<uni-list-item title="播放设置"/>
				<uni-list-item title="解码设置" />
				<uni-list-item title="弹幕设置" />
				<uni-list-item title="离线设置" />
				<uni-list-item title="追番设置" />
			</uni-list>
		</view>
		<view class="set_menu3"  @click="nothing()">
			<uni-list style="margin-top: 20rpx;">
				<uni-list-item title="动态设置"/>
				<uni-list-item title="推送与消息" />
				<uni-list-item title="清理存储空间" />
				<uni-list-item title="其他设置" />
			</uni-list>
		</view>
		<view class="set_menu4"  @click="nothing()">
			<uni-list style="margin-top: 20rpx;">
				<uni-list-item title="我的客服"/>
				<uni-list-item title="帮助" />
			</uni-list>
		</view>
		<view class="for_button" style="margin-top: 30rpx;">
			<button type="default" @click="logout('change')">切换账号</button>
			<button type="warn" style="margin-top: 30rpx;" @click="logout()">退出登录</button>
		</view>
	</view>
</template>

<script>
	import uniList from '@/components/uni-list/uni-list.vue'
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
	export default {
		data() {
			return {
				user: ''
			}
		},
		onShow() {
			this.user = uni.getStorageSync('user_key')
		},
		methods: {
			nothing() {
				uni.showToast({
				    icon: 'none',
					position: 'bottom',
				    title: "暂未开通~"
				});
			},
			logout(change) {
				if (uni.getStorageSync("user_key")) {
					uni.showModal({
						content: "确定离开嘛~",
						success(res) {
							if(res.confirm) {
								uni.clearStorageSync()
								if (change) {
									uni.reLaunch({
										url: '../login/login'
									})
								} else {
									uni.reLaunch({
										url: '../index/index'
									})
								}
							} 
						}
					})
					this.showLeft = false
				} else {
					uni.showToast({
					    icon: 'none',
						position: 'bottom',
					    title: "还没有登录哦~"
					});
				}
			}
		},
		components: {
			uniList, uniListItem
		}
	}
</script>

<style>
 .for_button {
 }
</style>
