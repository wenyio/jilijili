<template>
	<view class="content">
		<!-- 抽屉 -->
		<uni-drawer :visible="showLeft" @close="closeDrawer()">
		    <view style="background-color: #EEEEEE;">
				<!-- 头部 -->
				<view style="padding:30rpx;background-color: #F5F2F0;">
					<!-- 头像 -->
					<view class="user_icon" style="display: flex;justify-content: space-around;align-items: center;">
						<navigator url="../login/login" v-show="!user.icon">
							<image style="width: 120rpx;border-radius: 50%;" src="../../static/icons/user.png" mode="widthFix"></image>
						</navigator>
						<navigator url="../userinfo/userinfo" v-show="user.icon">
							<image style="width: 120rpx;border-radius: 50%;" :src="user.icon" mode="widthFix"></image>
						</navigator>
						<!-- 用户信息 -->
						<text style="font-size: 36rpx;color:#666;" v-show="!user">点击头像登录</text>
						<view class="user_info" style="padding: 10rpx;" v-show="user">
							<view class="user_name">
								{{user.username}} <text style="color:#de688b; font-size: 18rpx;" class="user_grade">. lv {{user.grade}}</text>
							</view> 
							<view class="user_jb" style="color:#666; font-size: 18rpx;">
								VB: 0 积分: {{user.integral}}
							</view>
						</view>
					</view>	
					<view class="user_signature" style="color:#666; font-size: 22rpx; margin-top: 30rpx;margin-left: 36rpx;">
						<text v-show="user">{{user.signature}}</text>
						<text v-show="!user">快来加入我们把~</text>
					</view>
				</view>
				<!-- 大会员模块 -->
				<view class="user_vip" style="background-color: #fff;padding: 30rpx 30rpx;font-size: 28rpx;">
					<view class="vip_title">
						<text style="color: #de688b;">我的大会员</text>
						<text style="margin-left: 20rpx;color: #CCCCCC;">了解更多权益</text>
					</view>
					<view class="vip_content">
						<text>开通大会员畅看番剧国创</text>
					</view>
				</view>
				<!-- 中部 -->
				<view v-show="user" style="margin-top: 22rpx;padding: 25rpx;background-color: #fff;display: flex;justify-content: space-around;font-size: 28rpx;">
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
				<!-- 菜单 -->
				<uni-list style="margin-top: 20rpx;">
					<uni-list-item @click="closeDrawer()" show-extra-icon :extra-icon="indexIcon" title="首页"/>
					<navigator url="../userinfo/userinfo">
						<uni-list-item show-extra-icon :extra-icon="userIcon" title="个人中心" />
					</navigator>
					<uni-list-item show-extra-icon :extra-icon="collectIcon" title="我的收藏" />
					<uni-list-item show-extra-icon :extra-icon="logIcon" title="历史记录" />
					<uni-list-item show-extra-icon :extra-icon="upIcon" title="UP 中心" />
					<navigator url="../set/set">
						<uni-list-item show-extra-icon :extra-icon="setIcon" title="设置" />
					</navigator>
					<uni-list-item show-extra-icon :extra-icon="aboutIcon" title="关于" />
					<!-- <uni-list-item title="我的收藏" badge-text="12" /> -->
				</uni-list>
		    </view>
		</uni-drawer>
		<!-- 公共头部 -->
		<!-- <MyTitle/> -->
		<!-- 首页导航模块 -->
		<view class="nav_wrap">
			<!-- 自己滚动区域的组件 -->
			<scroll-view class="nav" scroll-x="true" @scrolltoupper	>
				<view @click="activeNav(nav.name, nav.id)" class="nav_item " :class=" {active : currentIndexNav == nav.name}" v-for="(nav, index) in navlist" >
					{{nav.name}}
				</view>
			</scroll-view>
		</view>
		<!-- 首页轮播图 -->
		<view class="slides" v-if="currentIndexNav === '首页'">
			<swiper autoplay circular indicator-dots="true" indicator-active-color="#de688b">
				<swiper-item v-for="s in swiperlist">
					<navigator :url="'../detail/detail?id='+s.id">
						<image :src="s.icon" mode="scaleToFill"></image>
					</navigator>
				</swiper-item>
			</swiper>
		</view>
		<!-- 视频列表 -->
		<view class="video_warp">
			<navigator :url="'../detail/detail?id='+video.id" class="video_item" v-for="video in videoslist">
				<!-- 图片容器 -->
				<view class="video_img">
					<!-- 图片 -->
					<image :src="video.icon" mode="widthFix"></image>
					<!-- 播放量 -->
					<view class="video_info">
						<!-- 播放量 -->
						<view class="play_count_warp">
							<!-- 图标 -->
							<font class="iconfont icon-bofang"></font>
							<!-- 数值 -->
							<text class="play_count">{{video.nums}}</text>
						</view>
						<!-- 评论量 -->
						<view class="comment_count">
							<!-- 图标 -->
							<font class="iconfont icon-pinglun"></font>
							<!-- 数值 -->
							<text class="comment_count">{{video.comments}}</text>
						</view>
					</view>
				</view>
				<!-- 标题 -->
				<view class="video_title">{{video.description}}</view>
			</navigator>
		</view>
	</view>
</template>

<script>
	import MyTitle from "@/components/MyTitle/MyTitle.vue"
	import uniDrawer from "@/components/uni-drawer/uni-drawer.vue"
	import uniList from '@/components/uni-list/uni-list.vue'
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
	import uniIcon from "@/components/uni-icons/uni-icons.vue"
	
	export default {
		data() {
			return {
				search: '',
				user: '',
				userIcon: {
					color: '#000',
					size: '22',
					type: 'person'
				},
				indexIcon: {
					color: '#000',
					size: '22',
					type: 'home'
				},
				logIcon: {
					color: '#000',
					size: '20',
					type: 'loop'
				},
				collectIcon: {
					color: '#000',
					size: '22',
					type: 'star'
				},
				setIcon: {
					color: '#000',
					size: '22',
					type: 'gear'
				},
				aboutIcon: {
					color: '#000',
					size: '20',
					type: 'info'
				},
				upIcon: {
					color: '#000',
					size: '20',
					type: 'upload'
				},
				baseUrl: this.GLOBAL.baseURL,
				// 被点击首页导航菜单的索引
				currentIndexNav: '首页',
				active: '',
				//首页导航数据
				navlist: [],
				// 首页轮播图
				swiperlist: [],
				// 视频数据
				videoslist: [],
				// 抽屉
				showLeft: false
			}
		},
		/**
		 * 监听导航按钮事件
		 * @param {Object} e
		 */
		onNavigationBarButtonTap(e) {
			let that = this
			// console.log(e)
			switch(e.id) {
				case "menu":
				that.showLeft = false
				if (uni.getStorageSync("user_key")) {
					that.showLeft = true
					that.getUserInfo()
				} else {
					that.showLeft = true
					that.user = ''
				}
				case "icon":
				case "message":
			}
		},
		/**
		 * 监听导航搜索事件
		 * @param {Object} e
		 */
		onNavigationBarSearchInputChanged (e) {
			// console.log(e.text)
			let that = this
			if (e.text) {
				uni.request({
					url: that.baseUrl + "/videos/search/" + e.text,
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							if(res.data.result) {
								that.videoslist = []
								that.videoslist = res.data.result
								uni.showToast({
								    icon: 'none',
									position: 'bottom',
								    title: res.data.msg
								});
							}
							else {
								uni.showToast({
								    icon: 'none',
									position: 'bottom',
								    title: res.data.msg
								});
							}
							// console.log(this.navlist)
						}
						else {
							that.videoslist = []
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
				that.getVideosList()
			}
		},
		onLoad() {
			// 获取首页导航数据
			this.getNavlist()
			// 获取首页轮播数据
			this.getSwiperList()
			// 获取视频列表
			this.getVideosList()
		},
		onShow() {
			this.showLeft = false
			if (uni.getStorageSync("user_key")) {
				this.user = uni.getStorageSync("user_key")
			}
		},
		methods: {
			/**
			 * 关闭抽屉事件
			 */
			closeDrawer() {
				this.showLeft = false
			},
			/**
			 * 点击首页导航按钮
			 */
			activeNav(name, id) {
				// console.log("123")
				this.currentIndexNav = name
				let that = this
				uni.request({
					url: that.baseUrl + "/videos/cid/" + id,
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							that.videoslist = []
							that.videoslist = res.data.result
							// console.log(this.navlist)
						}
						else {
							that.videoslist = []
							uni.showToast({
							    icon: 'none',
								position: 'bottom',
							    title: res.data.msg
							});
						}
					}
				})
			},
			getUserInfo() {
				let that = this
				if (uni.getStorageSync("user_key")) {
					uni.request({
						url: that.baseUrl + "/users/" + uni.getStorageSync("user_key").id,
						success(res) {
							// console.log(res)
							if (res.data.code == 200) {
								uni.setStorageSync("user_key", res.data.result)
							}
						}
					})
				}
			},
			/**
			 * 获取首页导航数据
			 */
			getNavlist() {
				let that = this
				uni.request({
					url: that.baseUrl + "/categories",
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							that.navlist = res.data.result
							// console.log(this.navlist)
						}
						else {
							uni.showModal({
								content: '服务器错误',
								showCancel: false
							});
						}
					}
				})
			},
			/**
			 * 获取首页轮播图
			 */
			getSwiperList() {
				let that = this
				uni.request({
					url: that.baseUrl + "/videos/randThree",
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							that.swiperlist = res.data.result
							// console.log(this.navlist)
						}
						else {
							uni.showModal({
								content: '服务器错误',
								showCancel: false
							});
						}
					}
				})
			},

			/**
			 * 获取视频列表
			 */
			getVideosList() {
				let that = this
				uni.request({
					url: that.baseUrl + "/videos",
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							that.videoslist = res.data.result
							// console.log(this.navlist)
						}
						else {
							uni.showModal({
								content: '服务器错误',
								showCancel: false
							});
						}
					}
				})
			},
		},
		components: {
			MyTitle, uniDrawer, uniList, uniListItem, uniIcon
		}
	}
</script>

<style>	
	page {
		color: #666;
		padding: 10rpx;
	}
	/* 首页导航 */
	.nav_wrap {
		z-index:9;
		position:sticky;
		top: 0rpx;
		background-color: #FFFFFF;
	}
	.nav {
		white-space: nowrap;
		padding: 5rpx 0;
	}
	.nav_item {
		padding: 20rpx 45rpx;
		font-size: 30rpx;
		display: inline-block;
	}
	.nav_item.active {
		border-bottom: 5rpx solid #de688b;
		color: #de688b;
	}
	/* 轮播图 */
	.slides {
		margin-top: 5rpx;
	}
	.slides swiper {
		height: 300rpx;
	}
	.slides navigator {
		width: 100%;
		height: 100%;
	}
	.slides image {
		width: 100%;
		height: 100%;
		border-radius: 15rpx;
	}
	/* 视频列表 */
	.video_warp {
		margin-top: 10rpx;
		display: flex;
		flex-wrap: wrap;
		padding: 5rpx;
		justify-content: space-between;
	}
	.video_item {
		width: 49%;
		margin-bottom: 20rpx;
	}
	.video_img{
		position: relative;
	}
	.video_img image{
		width: 100%;
		border-radius: 15rpx;
	}
	.video_img .video_info{
		position: absolute;
		bottom: 10rpx;
		left: 0;
		width: 100%;
		display: flex;
		justify-content: space-around;
		color: #FFF;
		font-size: 24rpx;
	}
	.video_title{
		font-size: 28rpx;
		
		display: -webkit-box;
		overflow: hidden;
		white-space: normal;
		text-overflow: ellipsis;
		word-wrap: break-word;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
	}
	/deep/.uni-scroll-view::-webkit-scrollbar {
		/* 隐藏滚动条，但依旧具备可以滚动的功能 */
		display: none
	}
</style>
