const routes = [
    {
        path: '/test',
        name: 'Test',
        component: () => import('@/views/test.vue'),
    },
    {
        path: '/security/login',
        name: 'SecurityLogin',
        component: () => import("@/views/security/login/index.vue")
    },
    {
        path: '/security/home',
        name: 'SecurityHome',
        component: () => import("@/views/security/home/index.vue")
    }
];
export default routes;