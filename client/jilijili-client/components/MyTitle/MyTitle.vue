<template>
	<view class="my_title">
		<uni-drawer :visible="showLeft" mask="false" @close="closeDrawer()">
		    <view style="padding:30upx;">
		        <view class="uni-title">抽屉式导航</view>
		    </view>
		</uni-drawer>
		<!-- logo -->
		<navigator class="logo" @click="show()">
			<image class="logo_img" src="../../static/icons/logo2.png"></image>
		</navigator>
		<!-- 搜索 -->
		<view class="search_icon">
			<image class="search_img" src="../../static/icons/search.png"></image>
		</view>
		<!-- 用户 -->
		<view class="user_icon">
			<image class="user_img" src="../../static/icons/user.png"></image>
		</view>
		<!-- 投稿按钮 -->
		<view class="upup">
			Up 投稿
		</view>
	</view>
</template>

<script>
	import uniDrawer from "@/components/uni-drawer/uni-drawer.vue"
	
	export default {
		data() {
			return {
				showLeft: false
			}
		},
		methods: {
			show() {
				this.showLeft = true
			},
			closeDrawer() {
				this.showLeft = false
			}
		},
		components: {
			uniDrawer
		}
	}
</script>

<style>
.my_title {
	/* margin-top: 30rpx; */
  display: flex;
  align-items: center;
  padding: 10rpx;
  background-color: #fff;
}

.logo {
  flex: 7;
}

.logo_img {
  width: 140rpx;
  height: 60rpx;
}

.search_icon {
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
}

.search_img {
  width: 60rpx;
  height: 60rpx;
}

.user_icon {
  display: flex;
  flex: 2;
  justify-content: center;
  align-items: center;
}

.user_img {
  width: 54rpx;
  height: 60rpx;
}

.upup {
  display: flex;
  flex: 3;
  font-size: 30rpx;
  justify-content: center;
  align-items: center;
  background-color: pink;
  color: #fff;
  border-radius: 10rpx;
  padding: 10rpx;
}
</style>
