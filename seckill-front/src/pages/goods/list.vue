<template>
  <div class="list">
    <div v-for="(item,index) in goodslists" :key="index" class="goods-list">
      <Card :goods="item" class="goods"/>
    </div>
    
  </div>
</template>

<script>
  import Card from '@/components/pages/card'
  import goods from '@/api/goods'
  export default {
    components: {
      Card
    },
    data() {
      return {
        goodslists: ''
      }
    },
    created() {
      // 页面加载时获取商品列表
      this.getGoodsList()
    },
    methods: {
      //获取商品列表
      getGoodsList() {
        goods.getGoodsList()
        .then(res => {
          this.goodslists = res.data.data.list
        })
      }
    },
    mounted() {
      // 设置定时器，定时刷新秒杀时间
      setInterval(() => {
        this.getGoodsList()
      }, 3000)
    }
  }
</script>

<style scoped>
  .list {
    background-color: rgb(249, 249, 249);
    margin-left: 3.2rem;
    margin-top: 1.2rem;
    width: calc(100% - 6rem);
  }
  .goods {
    float: left;
  }
</style>