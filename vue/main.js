import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'
import VueRouter from 'vue-router'

// element-ui
//import { DatePicker } from 'element-ui'
//import lang from 'element-ui/lib/locale/lang/en'
//import locale from 'element-ui/lib/locale'

Vue.use(VueRouter)

import App from './App.vue'
import { http } from './services'
import { userStore } from './stores'

Vue.config.productionTip = false


//Vue.use(DatePicker)
//locale.use(lang)

// Pointing routes to the components they should use
var router = new VueRouter({
  routes: [
    {
      name: 'home',
      path: '/',
      component: require('./components/Home.vue')
    },
    {
      path: '/auth/login',
      component: require('./components/auth/Login.vue')
    },
    {
      name: 'repository-detail',
      path: '/repository/:name',
      component: require('./components/repositories/Detail.vue')
    },
    {
      path: '/add',
      component: require('./components/repositories/Add.vue')
    }
  ]
})

new Vue({
  el: '#app',
  router: router,
  template: '<App/>',
  components: { App },
  created: function () {
    http.init()
    userStore.init()
  },
})
