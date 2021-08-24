<template>
  <div class="menu">
    <UserInfo />
    <div class="navmenu">
      <NavMenu/>
    </div>
  </div>
</template>

<script>
  import UserInfo from '@/components/menu/user-info'
  import NavMenu from '@/components/menu/nav-menu'
  export default {
    name: 'Menu',
    components: {
      UserInfo,
      NavMenu
    }
  }
</script>

<style scoped>
  .menu {
    width: 16rem;
    background-color: rgb(237, 237, 237);
    height: calc(100vh - 3rem);
  }
  .navmenu {
    margin-top: 1rem;
  }
  
</style>