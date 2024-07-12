import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'

const router = createRouter({
    // 内部提供了 history 模式的实现。
    history: createWebHistory(),
    routes, // `routes: routes` 的简写
  })
export default router;