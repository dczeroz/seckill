<template>
  <div class="user-info">
    <!-- 用户未登录前 -->
    <div class="wait-login" v-if="!isLogin">
      <waitLogin/>
    </div>
    <!-- 用户已登录 -->
    <div class="login" v-else>
      <Logined :user = userInfo></Logined>
    </div>
  </div>
</template>

<script>
  import cookie from 'js-cookie'
  import waitLogin from './wait-login'
  import user from '@/api/user'
  import Logined from './logined'
  export default {
    components: {
      waitLogin,
      Logined
    },
    computed:{
      // 变isLogin值
      getLogin() {
        this.isLogin = this.$store.getters.login
      }
    },
    data() {
      return{
        // 获取vuex中login的值，判断是否登录，全局传值
        isLogin: this.$store.getters.login,
        userInfo: ''
      }
    },
    
    created(){
      if(cookie.get('token') != null && cookie.get('token').length != 0) {
          this.$store.commit('setIsLogin')
        
      }
    },
    watch: {
      // 监听getLogin计算属性
      getLogin() {
      }
    }
  }
</script>

<style scoped>
</style>