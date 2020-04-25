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
        name: 'SearchRent',
        component: ()=> import ('./components/SearchRent')
      },
      {
        path: '/adminPage',
        name: 'AdminPage',
        component: ()=> import ('./components/AdminPage')
      },
    ]
  })

  export default router;