import axios from 'axios';
import { ElMessage, ElMessageBox,ElLoading } from 'element-plus'
import router from '@/router'

import { apiBaseUrl, localTokenName, headerTokenName, loginUrl } from '@/constants';


const axiosInstance = axios.create({
    baseURL: apiBaseUrl,
});

let loading = false;
// 添加请求拦截器
axiosInstance.interceptors.request.use(function (config) {
    loading = ElLoading.service({
        fullscreen: true,
        lock: true,
        text: '处理中...',
        background: 'rgba(200, 200, 200, 0.3)'
    });
    // 在发送请求之前做些什么
    if(config.headers){
        config.headers['X-Requested-With'] = 'XMLHttpRequest';
        config.headers[headerTokenName] = localStorage.getItem(localTokenName)
    }else{
        config.headers = {
            'X-Requested-With': 'XMLHttpRequest',
            [headerTokenName]:localStorage.getItem(localTokenName)
        }
    }

    if(!config.method || !(config.method instanceof String) ||config.method.toLowerCase() === 'get' ){
        config.params?  config.params['_t'] = new Date().getTime() : config.params = {_t:new Date().getTime() };
    }

    return config;
  }, function (error) {
    // 对请求错误做些什么
    if(loading){
        loading.close();
        loading=false;
    }
    return Promise.reject(error);
  });

// Add a response interceptor 拦截响应
axiosInstance.interceptors.response.use(function (response) {
    if(loading){
        loading.close();
        loading=false;
    }
    console.log("拦截：",response);
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data

    const result = response.data;

    if(response.config && response.config.responseType === 'blob'){
        if(result.type !== 'application/octet-stream'){
            throw new Error(response);
        }
        return result;
    }

    if(result.success){
        return result;//改变axios响应的正向函数的参数
    }

    if(result.code == 520){//未登录，应跳转登录页
        localStorage.removeItem(localTokenName);
        ElMessage.error(result.message);
        if(loginUrl)router.replace({path:loginUrl});
        throw new Error(response);
    }

    //showFailToast(result.message);
    ElMessageBox.alert(result.message, "错误",{type:'error'});

    throw new Error(response);

}, function (error) {
    if(loading){
        loading.close();
        loading=false;
    }
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
});

export default axiosInstance;
