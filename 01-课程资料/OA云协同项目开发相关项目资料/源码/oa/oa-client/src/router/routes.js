const homeChildRoutes = [//home的子路由配置
    {
        path: '/welcome',
        name: 'Welcome',
        component: () => import("@/views/welcome.vue"),
        meta: {
            position: []
        }
    },
    {
        path: '/info/dep',
        name: 'InfoDep',
        component: () => import("@/views/info/dep/index.vue"),
        meta: {
            position: ["基础信息", "部门信息"]
        }
    },
    {
        path: '/info/emp',
        name: 'InfoEmp',
        component: () => import("@/views/info/emp/index.vue"),
        meta: {
            position: ["基础信息", "员工信息"]
        }
    },
    {
        path: '/security/role',
        name: 'Security/Role',
        component: () => import("@/views/security/role/index.vue"),
        meta: {
            position: ["安全管理", "角色管理"]
        }
    },
    {
        path: '/security/user',
        name: 'Security/User',
        component: () => import("@/views/security/user/index.vue"),
        meta: {
            position: ["安全管理", "用户管理"]
        }
    },
    {
        path: '/leave/apprev',
        name: 'LeaveApprev',
        component: () => import("@/views/leave/apprev/index.vue"),
        meta: {
            position: ["请假", "请假申请和销假"]
        }
    },
    {
        path: '/leave/exam',
        name: 'LeaveExam',
        component: () => import("@/views/leave/exam/index.vue"),
        meta: {
            position: ["请假", "请假审批"]
        }
    },
];


const routes = [
    {
        path:'/',
        redirect:'/security/home'
    },
    {
        path: '/test',
        name: 'Test',
        component: () => import("@/views/test.vue")
    },
    {
        path: '/security/login',
        name: 'SecurityLogin',
        component: () => import("@/views/security/login/index.vue")
    },
    {
        path: '/security/home',
        name: 'SecurityHome',
        component: () => import("@/views/security/home/index.vue"),
        children: homeChildRoutes
    },

];
export default routes;