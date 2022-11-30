import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    redirect: '/home',

    children: [
      {path: 'home',name: 'Home',component: () => import('../views/Home.vue'),},
      {path: 'user',name: 'User',component: () => import('../views/User.vue'),},
      {path: 'person',name: '个人中心',component: () => import('../views/Person.vue'),},
      {path: 'file',name: '文件管理',component: () => import('../views/File.vue'),},
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由首位
router.beforeEach((to,from,next)=>{
  localStorage.setItem('currentPathName',to.name)  //设置当前路由名称
  store.commit('setPath')
  next()
})

export default router
