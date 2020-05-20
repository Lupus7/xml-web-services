import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

/*function authetication(to, from, next) {
  const token = localStorage.getItem("accessToken");

  if (!(token === null || token === ""))
    return next();

  router.push('/');
}*/

const router = new Router({
  mode: 'hash',
  routes: [
    {
      path: '/'
    },
    {
      path: '/search',
      name: 'SearchRent',
      component: () => import('./components/SearchRent'),

    },

  ]
})

export default router;