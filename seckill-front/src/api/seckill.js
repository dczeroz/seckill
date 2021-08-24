import request from '@/utils/request'
export default{
	// 秒杀操作
	doSeckill(goodsId) {
		return request( {
			url:'/seckill/process/doSeckill/'+goodsId,
			method: 'get'
		})
	},
	getSeckillResult(goodId) {
		return request({
			url: '/seckill/process/result/'+goodId,
			method: 'get'
		})
	}
}