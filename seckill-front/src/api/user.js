import request from '@/utils/request'
export default{
	// 获取商品列表，调后台接口
	getUser() {
		return request( {
			url:`seckill/user/getUserInfo`,
			method: 'get'
		})
	},
  login(user) {
    return request({
      url: `seckill/user/login`,
      method: 'post',
      data: user
    })
  }
}