<template>
  <div class="orderList">
     <el-table
      :data="orderList"
      style="width: 90%">
      <el-table-column
        prop="goodsName"
        label="商品名"
        width="200">
      </el-table-column>
      <el-table-column
        prop="goodsPrice"
        label="秒杀价格"
        width="180">
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="下单时间">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import order from '@/api/order'
  export default {
    data() {
      return {
        orderList: []
      }
    },
    computed: {
    },
    methods: {
      
    },
    mounted() {
      order.getOrderList()
      .then(res => {
        this.orderList = res.data.data.orderList
      })
    }
  }
</script>

<style scoped>
  .orderList {
    margin-left: 5rem;
  }
</style>