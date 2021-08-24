import axios from 'axios'
import cookie from 'js-cookie'
import store from '../store'
import { MessageBox, Message } from 'element-ui'  
axios.defaults.withCredentials=true
// 创建axios实例
const service = axios.create({
  baseURL: 'http://127.0.0.1:8088', // api的base_url   timeout: 20000 // 请求超时时间
  withCredentials: true
})
service.interceptors.request.use(
  config => {
    if(cookie.get("token")) {
      //吧token放到header里面
      config.headers['token'] = cookie.get('token');
      
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)
service.interceptors.response.use(   
  response => {     //debugger 
    if (response.data.code !== 20000) { 
        Message({
          message: response.data.message || 'error',             
          type: 'error',             
          duration: 5 * 1000
      })
      if(response.data.code == 28004) {
        MessageBox.confirm(
          '请先登录',
          '未登录',
          {
            confirmButtonText: '登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.commit('setDialogVisible')
        })
       
      }
      return Promise.reject('error') 
    } else {        
     return response;
    }
},   
error => {     
  return Promise.reject(error.response)   // 返回接口返回的错误信息
});

export default service
