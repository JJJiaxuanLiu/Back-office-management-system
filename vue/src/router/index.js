import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'


Vue.use(VueRouter)

const routes = [

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

export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if(storeMenus){
    //拼装动态路由
    const manageRoute = {path: '/', component: () => import('../views/Manage.vue'), redirect: '/home', children: []}
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      //当path不为null的时候才去设置
      if(item.path){
        let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
        manageRoute.children.push(itemMenu)
      }else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })
    //动态添加到现在的对象中去
    router.addRoute(manageRoute)
  }
}

// //动态路由
// router.addRoute(  {
//   path: '/',
//   name: 'Manage',
//   component: () => import('../views/Manage.vue'),
//   redirect: '/home',

//   children: [
//     {path: 'home',name: 'Home',component: () => import('../views/Home.vue'),},
//     {path: 'user',name: 'User',component: () => import('../views/User.vue'),},
//     {path: 'role',name: '角色管理',component: () => import('../views/Role.vue'),},
//     {path: 'menu',name: '菜单管理',component: () => import('../views/Menu.vue'),},
//     {path: 'person',name: '个人中心',component: () => import('../views/Person.vue'),},
//     {path: 'file',name: '文件管理',component: () => import('../views/File.vue'),},
//   ]
// },
// )


// 路由首位
router.beforeEach((to,from,next)=>{
  localStorage.setItem('currentPathName',to.name)  //设置当前路由名称
  store.commit('setPath')
  next()
})

export default router
