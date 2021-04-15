import Vue from 'vue'
import App from './App'
import globalVariable from "@/api/global_variable.js"

Vue.prototype.GLOBAL = globalVariable

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
