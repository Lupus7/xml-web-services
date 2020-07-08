import Vue from "vue";
import Router from "vue-router";
Vue.use(Router);

function authetication(to, from, next) {
  const token = localStorage.getItem("accessToken");

  if (!(token === null || token === ""))
    return next();

  router.push('/');
}
function noAuthetication(to, from, next) {
    const token = localStorage.getItem("accessToken");
  
    if (!(token === null || token === ""))
        router.push('/dashboard');
    
    return next();
  }

const router = new Router({
    mode: "hash",
    routes: [
        {
            path: "/",
            name: "Home",
            component: () => import("./views/Home"),
            beforeEnter: noAuthetication
        },
        {
            path: "/dashboard",
            name: "MyProfile",
            component: () => import("./components/MyProfile"),
            beforeEnter: authetication

        },
        {
            path: "/dashboard/:mode",
            name: "MyProfileMode",
            props: true,
            component: () => import("./components/MyProfile"),
            beforeEnter: authetication

        },
        {
            path: "/messages",
            name: "Messages",
            props: true,
            component: () => import("./components/Messages"),
            beforeEnter: authetication

        },

        {
            path: "/rates",
            name: "MyRates",
            props: true,
            component: () => import("./components/MyRates"),
            beforeEnter: authetication

        },
    ],
});

export default router;
