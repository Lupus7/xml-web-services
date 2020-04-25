import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const router = new Router({
    mode: 'hash',
    routes: [
      {
        path: '/'
      },
      {
        path:'/search',
        name: 'search',
        component: ()=> import ('./components/SearchRent')
      },
      {
        path: '/admin',
        name: 'admin',
        component: ()=> import ('./components/AdminPage')
      },
    ]
  })

  export default router;