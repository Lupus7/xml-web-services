import Vue from 'vue'
import App from './App.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import jquery from 'vue-jquery'
import axios from 'axios'
import VueRouter from 'vue-router'
import router from './routes'
import VueResource from 'vue-resource'
Vue.use(VueResource)


//Components routes

Vue.use(VueRouter)

axios.defaults.baseURL = "http://localhost:8080/";

 
Vue.use(jquery)
Vue.use(axios)
// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')