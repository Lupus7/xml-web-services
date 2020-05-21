import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

function authetication(to, from, next) {
  const token = localStorage.getItem("accessToken");

  if (!(token === null || token === ""))
    return next();

  router.push('/');
}

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/'
    },
    {
      path: '/admin',
      name: 'AdminPanel',
      component: () => import('./views/AdminPanel'),
      beforeEnter: authetication

    },
    {
      path: '/permission',
      name: 'PermissionPage',
      props: true,
      component: () => import('./views/PermissionPage'),
      beforeEnter: authetication

    },
  ]
})

export default router;