import Vue from 'vue'
import VueRouter from 'vue-router'

// 路由懒加载
const Login = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Login.vue')
const Home = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Home.vue')
const Welcome = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Welcome.vue')
const Register = () => import('../components/Register.vue')

const Roles = () => import(/* webpackChunkName: "Users_Rights_Roles" */ '../components/user/User.vue')
const Users = () => import('../components/user/profile/index')

const Cate = () => import(/* webpackChunkName: "Cate_Params" */ '../components/goods/Cate.vue')
const Product = () => import(/* webpackChunkName: "Cate_Params" */ '../components/goods/Product.vue')
const Item = () => import(/* webpackChunkName: "Cate_Params" */ '../components/goods/Item.vue')

const GoodsList = () => import(/* webpackChunkName: "GoodsList_Add" */ '../components/goods/List.vue')

const Order = () => import(/* webpackChunkName: "Order_Report" */ '../components/order/Order.vue')

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/home',
    component: Home,
    redirect: '/Welcome',
    children: [
      { path: '/home', component: Welcome },
      { path: '/users', component: Users },
      { path: '/roles', component: Roles },
      { path: '/categories', component: Cate },
      { path: '/product/:categoryId', component: Product },
      { path: '/item/:productId', component: Item },
      { path: '/goods', component: GoodsList },
      { path: '/orders', component: Order }
    ] }

]

const router = new VueRouter({
  routes
})

// 挂载路由导航守卫,to表示将要访问的路径，from表示从哪里来，next是下一个要做的操作 next('/login')强制跳转login
router.beforeEach((to, from, next) => {
  // 访问登录页，放行
  if (to.path === '/login' || to.path === '/register') return next()
  // TODO 获取token
  // const tokenStr = window.sessionStorage.getItem('token')
  // TODO 没有token, 强制跳转到登录页
  // if (!tokenStr) return next('/login')
  next()
})

export default router
