<template>
  <div id="app">
    <div class="header">
      <!-- 头组件 -->
      <Header/>
    </div>
    <div class="body">
      <div class="menu">
        <!-- 导航组件 -->
        <Menu/>
      </div>
      <div class="content">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
  import Header from '@/components/header'
  import Menu from '@/components/menu'
  export default {
    name: 'App',
    components: {
      Menu,
      Header
    }
  }
</script>

<style scoped>
  .app {
    overflow: hidden;
  }
  .body {
    display: flex;
  }
  .content {
    width: calc(100% - 16rem);
    height: calc(100vh - 3rem);
    /* 只在主页面内容超出显示屏情况下显示滚动条 */
    overflow-y: scroll;
  }
  /* 滚动条 */
  ::-webkit-scrollbar {
    width: 0.3rem;
    height: 1rem;
  }
  ::-webkit-scrollbar-thumb {
    background-color:#606d71;
    background-clip:padding-box;
    min-height:18px;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius:2em;
  }
</style>
