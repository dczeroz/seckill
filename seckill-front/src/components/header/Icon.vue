<template>
  <!-- 头部功能键 -->
  <div class="buttons">
    <!-- 返回首页 -->
    <div class="home"  @click="goHome">
      <i class="iconfont icon-seckillhome" alt="to-home" />
    </div>
    <!-- 退出全屏 -->
    <div class="exit-full-screen"  @click="exitFullScreen">
      <i class="iconfont icon-seckillminussign" alt="to-home" />
    </div>
    <!-- 全屏 -->
    <div class="full-screen"  @click="fullScreen">
      <i class="iconfont icon-seckillfull-screen" alt="to-home" />
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goHome() {
      this.$router.push("/")
    },
    exitFullScreen() {
      if (document.fullscreenElement) { 
        document.exitFullscreen() 
      } 
    },
    fullScreen() {
      document.documentElement.requestFullscreen() 
    }
  }
}
</script>

<style scoped>
  .buttons {
    display: flex;
    margin-left: 0.5rem;
    margin-top: 1rem;
  }
  .home,
  .exit-full-screen,
  .full-screen {
    width: 0.95rem;
    height: 0.95rem;
    margin-left: 0.25rem;
    background-color: rgb(237, 101, 90);
    border-radius: 50%;
    cursor: pointer;
  }
  .exit-full-screen {
    background-color: rgb(224, 192, 76);
  }
  .full-screen {
    background-color: rgb(114, 190, 71);
  }
  .home:hover i,
  .exit-full-screen:hover i,
  .full-screen:hover i {
    display: block;
  }
  i {
    font-size: 0.9rem;
    display: none;
  }
</style>