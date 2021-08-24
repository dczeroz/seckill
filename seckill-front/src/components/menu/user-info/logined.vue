<template>
  <!-- 用户已登录 -->
  <div class="logined">
    <div @click="logout" class="logined-user">
      <img  :src="userInfo.head" class="avatar" />
      <p class="user-name">{{ userInfo.nickname }}</p>
    </div>
    
  </div>
</template>

<script>
  import cookie from 'js-cookie'
  import user from '@/api/user'
  export default {
    data() {
      return {
        userInfo: ''
      }
    },
    created(){
      if(cookie.get('token') != null && cookie.get('token').length != 0) {
        user.getUser()
        .then(res => {
          this.userInfo = res.data.data.user
        })
        .catch(error => {
          cookie.set('token','')
          this.$store.commit('setIsLogin')
          
        })
      }
    },
    methods: {
      logout() {
        this.$confirm('是否退出登录?', '注销', {
          confirmButtonText: '确定',
          showCancelButton: false
        }).then(() => {
          this.$store.commit('setLoading',false)
          this.$store.commit('setIsLogin')
          cookie.set("token",'')
        })
      }
    }
  }
</script>

<style scoped>
  .logined-user {
    display: flex;
    padding-top: 1rem;
    cursor: pointer;
  }
  .avatar {
    margin-left: 1.5rem;
    border-radius: 50%;
    width: 4rem;
  }
  .user-name {
    margin-left: 1.2rem;
    margin-top: 1rem;
    color: #4a4a4a;
  }
</style>