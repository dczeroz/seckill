import request from '@/utils/request'
export default{
  getOrderList() {
    return request( {
			url:'/seckill/order/orderInfo/list',
			method: 'get'
		})
  }
}