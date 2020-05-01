import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const router = new Router({
    mode: 'history',
    routes: [
      {
        path: '/'
      },
      {
        path: '/admin',
        name: 'AdminPanel',
        component: ()=> import ('./views/AdminPanel')
      },
      {
        path: '/permission',
        name: 'PermissionPage',
        component: ()=> import ('./views/PermissionPage')
      },
    ]
  })

  export default router;