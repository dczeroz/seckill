import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
const VueRouterPush = Router.prototype.push
Router.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/goodsLists',
      component: () => import ('@/pages/goods/list.vue')
    },
    {
      path: '/goodsLists',
      name: 'GoodsLists',
      component: () => import ('@/pages/goods/list.vue')
    },
    {
      path: '/myOrder',
      name: 'MyOrder',
      component: () => import ('@/pages/order/my-order.vue')
    }
  ]
})
