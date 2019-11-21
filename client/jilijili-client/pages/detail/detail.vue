<template>
	<view>
		<!-- 视频详情 -->
		<view class="video_info">
			<!-- 视频标签 -->
			<view class="video_tarbar">
				<!-- <iframe src="//player.bilibili.com/player.html?aid=40455083&cid=71030693&page=26" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe> -->
				<video id="myVideo" @timeupdate="nowTime" ref="video" enable-danmu danmu-btn :danmu-list="danmuList" :src="videoDatail.url" v-show="videoDatail.url"></video>
				<text v-show="!videoDatail.url">此视频链接丢失</text>
				<scroll-view class="tar_nav">
					<view @click="activeNav('简介')" class="nav_item " :class=" {active : currentIndexNav == '简介'}">
						简介
					</view>
					<view @click="activeNav('评论')" class="nav_item " :class=" {active : currentIndexNav == '评论'}">
						评论({{danmuList.length}})
					</view>
					<view class="nav_item sendDanmu">
						<input class="danmu-input" placeholder="点击输入弹幕" :value="inputDanmu" @input="getDanmu"/>
					</view>
					<uniIcon class="sendIcon" :color="sendColor" type="paperplane" size="30" @click="sendDanmu"></uniIcon>
				</scroll-view>
			</view>
			<!-- 视频简介信息 -->
			<view class="showInfo" v-show="infoShow">
				<!-- 作者 -->
				<view class="author_warp">
					<view class="author_info">
						<view class="user_icon">
							<image class="user_img" :src="vuser.icon"></image>
						</view>
						<view class="author">
							{{vuser.username}}
							<view class="guanzhu">33粉丝</view>
						</view>
					</view>
					<view class="loveyou">
						<text class="iconfont">&#xe620;</text> 关注
					</view>
				</view>
				<!-- 视频相关 -->
				<view class="video_detail">
					<!-- 视频标题 -->
					<view class="video_title">
						<text>{{videoDatail.title}}</text>
						<text class="iconfont icon-weibiaoti35" @click="showDesc()" v-show="!showDec"></text>
						<text class="iconfont icon-up" @click="showDesc()" v-show="showDec"></text>
					</view>
					<view class="nums">
						<!-- 播放量 -->
						<text class="play_count"><font class="iconfont icon-bofang1"></font>{{videoDatail.nums}}</text>
						<!-- 评论量 -->
						<text class="comment_count"><font class="iconfont icon-pinglun1"></font>{{videoDatail.comments}}</text>
						<!-- 时间 -->
						<text class="date">{{videoDatail.createTime}}</text>
					</view>
					<view class="video_description" v-show="showDec">
						<text>{{videoDatail.description}}</text>
					</view>
					<view class="uoption">
						<!-- 点赞 -->
						<text class="ifont iconfont icon-zan1"></text>
						<!-- 不赞 -->
						<text class="ifont iconfont icon-zan4"></text>
						<!-- 投币 -->
						<text class="ifont iconfont icon-jinbishangcheng"></text>
						<!-- 收藏 -->
						<text class="ifont iconfont icon-wuxing"></text>
						<!-- 分享 -->
						<text class="ifont iconfont">&#xe626;</text>
					</view>
				</view>
				<!-- 推荐视频 -->
				<view class="others_list">
					<navigator v-for="other in othersList" class="item_other" :url="'../detail/detail?id='+other.id">
						<!-- 图片容器 -->
						<view class="other_img_wrap">
							<image :src="other.icon" mode="widthFix"></image>
						</view>
						<!-- 视频详情 -->
						<view class="other_info">
							<!-- 视频标题 -->
							<view class="other_title">{{other.title}}</view>
							<!-- 详情信息 -->
							<view class="other_detail">
								<!-- 播放量 -->
								<text class="other_play_count">
									<font class="iconfont icon-bofang1"></font>{{other.nums}}
								</text>
								<!-- 评论量 -->
								<text class="other_comment_count">
									<font class="iconfont icon-pinglun1"></font>{{other.comments}}
								</text>
							</view>
						</view>
					</navigator>
				</view>
			</view>
			<!-- 视频弹幕 -->
			<view class="showComment" v-show="!infoShow">
				<view class="comment_wrap">
					<view class="comment_title">
						所有评论
					</view>
					<view class="comment_list">
						<view class="comment_item" v-for="danmu in danmuList">
							<!-- 左侧 -->
							<view class="comment_user">
								<image :src="danmu.user.icon" mode="widthFix"></image>
							</view>
							<!-- 右侧 -->
							<view class="comment_info">
								<view class="comment_detail">
									<text class="author">{{danmu.user.username}}</text>
									<text class="date">{{happenTimeFun(danmu.createTime)}}</text>
								</view>
								<view class="comment_content">
									{{danmu.text}}
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uniIcon from "@/components/uni-icons/uni-icons.vue"
	export default {
		data() {
			return {
				vid: '',
				user: '',
				vuser: '',
				// 被点击首页导航菜单的索引
				currentIndexNav: '简介',
				// 显示简介还是评论
				infoShow: true,
				baseUrl: this.GLOBAL.baseURL,
				showDec: false,
				title: '',
				// 视频详情
				videoDatail: [],
				// 推荐视频
				othersList: [],
				// 视频当前播放的时间
				time: 0,
				color: '#fff',
				// 发送按钮颜色
				sendColor: '',
				// 输入的弹幕内容
				inputDanmu: '',
				// 弹幕列表
				danmuList: []
			}
		},
		onShow() {
			this.user = uni.getStorageSync("user_key")
			this.getCommentList(this.vid)
		},
		onReady: function(res) {
		        // #ifndef MP-ALIPAY
		        this.videoContext = uni.createVideoContext('myVideo')
		        // #endif
		},
		onLoad(options) {
			// console.log(options.id)
			// 获取视频详情
			this.vid = options.id
			this.getVideoDatail(options.id)
			// 获取推荐视频
			this.getOthersList(options.id)
			// 获取视频弹幕
			this.getCommentList(options.id)
		},
		methods: {
			danmu: function() {
				this.videoContext.sendDanmu({
					text: this.inputDanmu,
					color: this.color
				});
				// console.log("wofale"+this.inputDanmu+this.color)
				this.inputDanmu = '';
			},
			/**
			 * 发送弹幕
			 */
			sendDanmu: function (event) {
				let that = this;
				
				// 设置按钮颜色
				that.sendColor = "#de688b"
				setTimeout(function() {
					that.sendColor = ""
				}, 180);
				
				if (that.user == '') {
					uni.showToast({
						title: '你还没登陆哦~',
						showCancel: false,
						icon: 'none'
					})
					return false
				}
				
				if (that.inputDanmu === '') {
					uni.showToast({
						title: '弹幕不能为空哦~',
						showCancel: false,
						icon: 'none'
					})
				} else {
					that.color = that.getRandomColor()
					// console.log(that.inputDanmu+that.getRandomColor()+that.time+that.vid)
					uni.request({
						url: that.baseUrl + "/comments",
						method: "POST",
						data: {
							text: that.inputDanmu,
							color: that.color,
							time: that.time,
							vid: that.vid,
							user: that.user
						},success(res) {
							if(res.data.code == 200) {
								// 发送
								that.danmu()
								uni.showToast({
									title: '发送成功~',
									showCancel: false,
									icon: 'none'
								})
								that.getCommentList(that.vid)
							}
						}
					})
				}
				// TODO 登录后才能发送弹幕
				// console.log(that.inputDanmu)
			},
			/**
			 * 获取正在输入的弹幕内容
			 * @param {Object} event
			 */
			getDanmu: function (event) {
				this.inputDanmu = event.target.value;
			},
			/**
			 * 获取播放的进度
			 * @param {Object} e
			 */
			nowTime: function(e) {
				this.time = e.detail.currentTime
				// console.log(this.time)
			},
			/**
			 * 点击首页导航按钮
			 */
			activeNav(name) {
				// console.log("123")
				if (name == this.currentIndexNav) {
					return
				}
				this.currentIndexNav = name
				this.infoShow = !this.infoShow
			},
			/**
			 * 显示视频描述
			 */
			showDesc() {
				let that = this
				that.showDec = !that.showDec
			},
			/**
			 * 获取视频详情
			 */
			getVideoDatail(id) {
				let that = this
				uni.request({
					url: that.baseUrl + "/videos/"+id,
					success(res) {
						// console.log(res)
						if(res.data.code == 200) {
							that.videoDatail = res.data.result
							that.vuser = res.data.result.user
							that.videoDatail.createTime = that.happenTimeFun(that.videoDatail.createTime)
							// that.videoDatail.createTime = new Date(that.videoDatail.createTime.replace(/T/g, ' ').replace(/-/g, '/')).toString('date');
							// that.title = res.data.title
							// 设置导航标题为视频标题
							uni.setNavigationBarTitle({
								title:res.data.result.title
							});
							// console.log(res.data.result.title)
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
			 * 获取推荐视频
			 */
			getOthersList(id) {
				let that = this
				uni.request({
					url: that.baseUrl + "/videos/othersVideo/" + id,
					success(res) {
						// console.log(res)
						if(res.statusCode == 200) {
							that.othersList = res.data.result
							// console.log(that.othersList)
						}
						else {
							uni.showModal({
								content: '获取推荐视频失败, 暂无推荐视频!',
								showCancel: false
							});
						}
					}
				})
			},
			/**
			 * 获取弹幕list
			 * @param {Object} id
			 */
			getCommentList(id) {
				let that = this
				uni.request({
					url: that.baseUrl + "/comments/vid/" + id,
					success(res) {
						// console.log(res)
						if(res.statusCode == 200) {
							that.danmuList = res.data.result
							// console.log(that.danmuList)
						}
						else {
							uni.showModal({
								content: '服务器错误, 获取弹幕失败!',
								showCancel: false
							});
						}
					}
				})
			},
			/**
			 * 随机颜色
			 */
			getRandomColor: function() {
				const rgb = []
				for (let i = 0; i < 3; ++i) {
					let color = Math.floor(Math.random() * 256).toString(16)
					color = color.length == 1 ? '0' + color : color
					rgb.push(color)
				}
				return '#' + rgb.join('')
			},
			/**
			 * 时间戳数据处理
			 * @param {Object} num
			 */
			happenTimeFun(num){
					let date = new Date(num);
					 //时间戳为10位需*1000，时间戳为13位的话不需乘1000
			        let y = date.getFullYear();
			        let MM = date.getMonth() + 1;
			        MM = MM < 10 ? ('0' + MM) : MM;//月补0
			        let d = date.getDate();
			        d = d < 10 ? ('0' + d) : d;//天补0
			        let h = date.getHours();
			        h = h < 10 ? ('0' + h) : h;//小时补0
			        let m = date.getMinutes();
			        m = m < 10 ? ('0' + m) : m;//分钟补0
			        let s = date.getSeconds();
			        s = s < 10 ? ('0' + s) : s;//秒补0
			        return y + '-' + MM + '-' + d;
			},
		},
		components: {
			uniIcon
		}
	}
</script>

<style>
	.page{
		padding: 10rpx;
		color: #666;
	}
	.video_tarbar{
		z-index: 5;
		position: sticky;
		top: 0rpx;
		background-color: #FFFFFF;
		border:0rpx solid darkslategray;
		-webkit-box-shadow: 0 0 5px rgba(202,203,203,0.5)
	}
	.tar_nav {
		white-space: nowrap;
		padding: 5rpx 0;
		background-color: #FFFFFF;
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
	/* 视频容器 */
	.video_info {
		/* margin-top: 10rpx; */
		color: #666;
	}
	.video_info video{
		width: 100%;
	}
	.author_warp{
		display: flex;
		justify-content: center;
	}
	.author_info{
		display: flex;
		padding: 15rpx;
		width: 100%;
	}
	.user_icon {
		margin-right: 15rpx;
	}
	.user_img {
	  width: 76rpx;
	  height: 80rpx;
	}
	.author{
		color: #de688b;
	}
	.guanzhu{
		font-size: 15rpx;
		color: #666;
	}
	.loveyou{
		width: 120rpx;
		margin-top: 12rpx;
		margin-right: 15rpx;
		display: flex;
		height: 48rpx;
		font-size: 30rpx;
		align-items: center;
		background-color: #de688b;
		color: #fff;
		border-radius: 10rpx;
		padding: 10rpx;
	}
	.video_title{
		display: flex;
		color: #000000;
		justify-content: space-between;
		font-size: 35rpx;
		margin-left: 10rpx;
	}
	.nums{
		margin-top: 10rpx;
		margin-left: 8rpx;
	}
	.comment_count {
		margin-left: 15rpx;
	}
	.date{
		margin-left: 15rpx;
	}
	.video_description{
		margin-top: 10rpx;
		margin-left: 12rpx;
		display: flex;
		justify-content: space-between;
		font-size: 25rpx;
	}
	.video_description text{
		color: #666;
	}
	.video_detail{
		/* color: #666; */
		padding: 15rpx;
		font-size: 24rpx;
		border-bottom: #EEEEEE 1rpx solid;
	}
	.uoption{
		margin:0 auto;
		margin-top: 32rpx;
		width: 83%;
		display: flex;
		justify-content: space-between;
	}
	.ifont{
		font-size: 52rpx;
	}
	/* 推荐视频 */
	.others_list{
		margin-top: 15rpx;
	}
	
	.item_other {
		display: flex;
		justify-content: space-between;
		margin-bottom: 15rpx;
	}
	
	.other_img_wrap {
		width: 38%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.other_img_wrap image{
		width: 90%;
		border-radius: 15rpx;
	}
	
	.other_info {
		width: 60%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}
	
	.other_title {
		font-size: 30rpx;
		color: #e06f93;
	}
	
	.other_detail {
		font-size: 22rpx;
		color: #666;
	}
	
	.other_comment_count{
		margin-left: 20rpx;
	}
	/* 评论列表 */
	.comment_wrap{
		margin-top: 10rpx;
	}
	.comment_title{
		padding: 10rpx;
		font-size: 28rpx;
	}
	.comment_list{}
	.comment_item{
		margin-bottom: 10rpx;
		padding: 10rpx;
		display: flex;
		justify-content: space-between;
		border-bottom: 1rpx solid #CCCCCC;
	}
	.comment_user{
		flex: 1;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.comment_user image{
		width: 60%;
		border-radius: 50%;
	}
	.comment_info{
		flex: 5;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}
	.comment_detail{
		display: flex;
		justify-content: space-between;
	}
	
	.comment_detail .author {
		font-size: 28rpx;
		color: #000000;
	}
	.comment_detail .date {
		font-size: 24rpx;
		color: #666;
	}
	.comment_content{
		font-size: 26rpx;
		color: #000000;
	}
	/* 弹幕输入框 */
	.sendDanmu {
		width: 38%;
	}
	.danmu-input {
		width: 98%;
		font-size: 28rpx;
		margin-bottom: -8rpx;
	}
	.sendIcon{
		margin-left: -28rpx;
	}
</style>
