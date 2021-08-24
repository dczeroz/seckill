<template>
  <!-- card组件 -->
  <div class="card" v-loading="loading"
                    element-loading-text="正在排队中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <img :src="goods.goodsImg" :alt="goods.goodsName">
    <div class="good-info">
      <div class="title">{{goods.goodsTitle}}</div>
      <span class="seckill-price">{{changesPrice}}</span>
      <span class="price">{{goods.goodsPrice}}</span>
      <span class="surplusCount">库存数量还有:{{goods.stockCount}}</span>
      <div class="time">{{distanceTime}}</div>
      <div class="footer">
        <div class="progress">
          <el-progress :text-inside="true" 
                       :stroke-width="20" 
                       :percentage="percentage" 
                       status="success"></el-progress>
        
        </div>
        <div class="button">
          <el-button :type="buttonType" size="mini" @click="doSeckill(goods.id)" :disabled="isDisabled" round>马上抢</el-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import common from '@/utils/common'
  import seckill from '@/api/seckill'
  export default {
    props: {
      goods: {
        type: Object
      }
    },
    data() {
      return {
        goodsInfo: this.goods,
        distanceTime: '',
        buttonType: '',
        isDisabled: '',
        loading: false
      }
    },
    created() {
      this.getDistanceTime()
    },
    computed: {
      // 改变字符串，价格变为￥1
      changesPrice() {
        return common.splicingStr('￥',this.goods.seckillPrice)
      },
      percentage() {
        let num = this.goods.goodsCount-this.goods.stockCount   
        return parseInt(num / this.goods.goodsCount * 100)
      }
    },
    methods: {
      getDistanceTime() {
        // 获取当前时间
        let now = new Date()
        let start = new Date(this.goods.startDate)
        let end = new Date(this.goods.endDate)
        let time
        // 在秒杀期间内
        if(now > start && now < end) {
          time = common.formatTime(now,this.goods.endDate)
          this.distanceTime = '距离秒杀结束还有：'+time
          this.buttonType = 'danger'
          if(this.isDisabled == true) 
            this.buttonType = 'info'
          else 
            this.isDisabled = false
          if(this.goods.stockCount == 0){
            this.buttonType = 'info'
            this.isDisabled = true
          }
        }else if(now < start) {
          // 未到秒杀时间
          time = common.formatTime(now,this.goods.startDate)
          this.distanceTime = '距离秒杀开始还有：'+time
          this.buttonType = 'info'
          this.isDisabled = true
        }else if(now >end) {
          // 秒杀结束
          this.distanceTime = '秒杀已结束'
          this.buttonType = 'info'
          this.isDisabled = true
        }
      },
      doSeckill(goodId) {
        //当用户秒杀后，按钮不能点击,此处有个问题，刷新会重新可以点击，但redis会提示重复抢购，不构成影响
        this.buttonType = 'info'
        this.isDisabled = true
        seckill.doSeckill(goodId)
        .then(res => {
          if(res.data.data.seckillCode == 0) {
            this.getSeckillResult(goodId)
          }
        })

      },
      getSeckillResult(goodId) {
        seckill.getSeckillResult(goodId)
        .then(res => {
          let flag = res.data.data.flag
          if(flag == -1) {
            this.$alert('秒杀失败', '秒杀结果', {
                confirmButtonText: '确定'
            });
          }else if(flag ==0 ) {
            this.loading = true
            //如果查询结果为0，表示还在排队中，延迟一秒用户体验比较好，服务器压力小一点
            setTimeout(() => {
              this.getSeckillResult(goodId)
            }, 1000)
          }else {
            this.$confirm('秒杀成功,是否查看商品', '秒杀结果', {
              distinguishCancelAndClose: true,
              confirmButtonText: '确定',
              cancelButtonText: '取消'
            })
            .then(() => {
              this.loading = false
              this.$router.push({path:'/myOrder'})
            })
            .catch(() => {
              this.loading = false
            })
            
          }
        })
      }
    },
    mounted() {
      // 设置定时器，定时刷新秒杀时间
      setInterval(() => {
        this.getDistanceTime()
      }, 1000)
    }
  }
</script>

<style scoped>
  .card {
    cursor: pointer;
    width: 35rem;
    height: 15rem;
    margin-left: 1.2rem;
    display: flex;
  }
  .card img {
    width: 11.5rem;
    height: 11.5rem;
    margin: 1.7rem 0 0 1.7rem;
  }
  .good-info {
    width: 18.5rem;
    margin: 2rem 0 0 1.7rem;
  }
  .title {
    margin-bottom: 1rem;
    color: #333;
  }
  .title:hover {
    color: #fb3434;
  }
  .seckill-price {
    font-size: 18px;
    font-weight: 700;
    color: #333;
    
  }
  .price {
    text-decoration:line-through;
    font-size: 16px;
    color: #999;
    
  }
  .time {
    color: #FB3434;
    font-size: 16px;
    height: 3rem;
    line-height: 3rem;
  }
  .progress {
    margin-top: 0.5rem;
    width: 11rem;
  }
  .footer {
    display: flex;
  } 
  .button {
    margin-top: 0.2rem;
    margin-left: 2rem;
  }
  .surplusCount {
    margin-left: 1.5rem;
    color: #FB3434;
  }
</style>