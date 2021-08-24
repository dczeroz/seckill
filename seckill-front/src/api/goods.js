import request from '@/utils/request'
export default{
	// 获取商品列表，调后台接口
	getGoodsList() {
		return request( {
			url:`/seckill/goods/list`,
			method: 'get'
		})
	}
}