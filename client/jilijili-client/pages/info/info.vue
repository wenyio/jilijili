  
<template>
	<view>
		<view class="u_info">
			<view class="title">昵称: </view>
			<input class="uni-input" v-model="user.username" maxlength="10" @input="onKeyInput" />
		</view>
		<view class="u_info">
			<view class="title">性别: </view>
			<view style="flex: 6;">
				<picker @change="bindPickerChange" :value="user.sex" :range="array" range-key="name">
					<view class="uni-input">{{array[user.sex].name}}</view>
				</picker>
			</view>
			<!-- <input class="uni-input" v-model="user.sex" maxlength="10" @input="onKeyInput" /> -->
		</view>
		<view class="u_info" style="color: #666;" @click="nothing()">
			<view class="title">邮箱: </view>
			<input class="uni-input" v-model="user.email" disabled="" maxlength="32" @input="onKeyInput" />
		</view>
		<view class="u_info">
			<view class="title">生日: </view>
			<picker style="flex: 6;" mode="date" v-model="user.birthday" :start="startDate" :end="endDate" @change="bindDateChange">
				<view class="uni-input">{{user.birthday}}</view>
			</picker>
			<!-- <input class="uni-input" v-model="user.birthday" maxlength="10" @input="onKeyInput"  /> -->
		</view>
		<view class="u_info">
			<view class="title">个签: </view>
			<input class="uni-input" v-model="user.signature" maxlength="10" @input="onKeyInput" />
		</view>
		<view style="padding: 30rpx;">
			<button type="default" @click="updateUser()">保存</button>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				baseUrl: this.GLOBAL.baseURL,
				user: '',
				birthday: '',
				array: [{name:'无'},{name: '男'}, {name:'女'}],
				inputValue: '',
				startDate:getDate('start'),
				endDate:getDate('end'),
			}
		},
		onShow() {
			this.user = uni.getStorageSync("user_key")
			this.user.birthday = this.happenTimeFun(this.user.birthday)
		},
		methods: {
			updateUser() {
				// console.log(this.user)
				let that = this
				uni.request({
					url: that.baseUrl + "/users/update",
					method: "POST",
					data: that.user,
					success(res) {
						if (res.data.code == 200) {
							uni.showToast({
							    icon: 'none',
								position: 'bottom',
							    title: res.data.msg
							});
							// console.log(res.data)
							that.user = res.data.result
							that.user.birthday = that.happenTimeFun(that.user.birthday)
							uni.setStorageSync("user_key", res.data.result)
						} else {
							uni.showToast({
							    icon: 'none',
								position: 'bottom',
							    title: res.data.msg
							});
						}
					}
				})
			},
			bindDateChange: function(e) {
				this.user.birthday = e.target.value
				// console.log(e.target.value)
			},
			bindPickerChange: function(e) {
				// console.log('picker发送选择改变，携带值为：' + e.target.value)
				this.user.sex = e.target.value
			},
			nothing() {
				uni.showToast({
				    icon: 'none',
					position: 'bottom',
				    title: '邮箱是登录标识^_^, 暂不支持修改~'
				});
			},
			onKeyInput: function(event) {
				this.inputValue = event.target.value
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
		}
	}
	
	function getDate(type) {
			const date = new Date();
		
			let year = date.getFullYear();
			let month = date.getMonth() + 1;
			let day = date.getDate();
		
			if (type === 'start') {
				year = year - 60;
			} else if (type === 'end') {
				year = year + 2;
			}
			month = month > 9 ? month : '0' + month;;
			day = day > 9 ? day : '0' + day;
		
			return `${year}-${month}-${day}`;
		}
</script>

<style>
	.u_info{
		border-bottom: 1rpx solid #F5F2F0;
		padding: 30rpx;
		display: flex;
		justify-content: center;
	}
	
	.title {
		flex: 2
	}
	.uni-input {
		flex: 6
	}
</style>