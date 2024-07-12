import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {

  // 根据当前工作目录中的 `mode` 加载 .env 文件
  // 设置第三个参数为 '' 来加载所有环境变量，而不管是否有 `VITE_` 前缀。
  const env = loadEnv(mode, process.cwd(), '');

  return {
    
    plugins: [vue()],

    resolve: {
      //使用“@”表示项目的src目录
      alias: [
        {
          find: '@',
          replacement: resolve(__dirname, './src'),
        }
      ]
    },

    //前端开发服务器配置
    server: {
      host: true,
      port: 8088,
      //服务端代理设置
      proxy: {
        //如果访问地址以"/api"开头，则自动代理到服务端地址http://localhost:9999,同时将"/api"替换为""
        [env.VITE_API_BASE_URL]: {
          target: env.VITE_SERVER_URL,
          changeOrigin: true,
          //rewrite: path => path.replace(/^\/api/, '')
          rewrite: path => path.replace(new RegExp("^"+env.VITE_API_BASE_URL),'')
        }
      }
    }

  };

});
