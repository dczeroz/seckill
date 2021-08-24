import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import Vuex from 'vuex'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css';
import "./assets/css/global.css"
import "./assets/css/iconfont.css"

Vue.config.productionTip = false
Vue.use(Vuex)
Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store: store,
  render: h => h(App)
})
