import Vue from "vue";
import Router from "vue-router";
Vue.use(Router);

function authetication(to, from, next) {
  const token = localStorage.getItem("accessToken");

  if (!(token === null || token === ""))
    return next();

  router.push('/');
}

const router = new Router({
    mode: "hash",
    routes: [
        {
            path: "/",
        },
        {
            path: "/search",
            name: "SearchRent",
            component: () => import("./components/SearchRent"),
        },
        {
            path: "/myprofile",
            name: "MyProfile",
            component: () => import("./components/MyProfile"),
            beforeEnter: authetication

        },
        {
            path: "/ad/:id",
            name: "Ad",
            props: true,
            component: () => import("./views/Ad"),
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

        {
            path: "/leaverate",
            name: "LeaveRate",
            props: true,
            component: () => import("./components/LeaveRate"),
            beforeEnter: authetication

        },
    ],
});

export default router;
