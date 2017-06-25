import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'

// element-ui
//import { DatePicker } from 'element-ui'
//import lang from 'element-ui/lib/locale/lang/en'
//import locale from 'element-ui/lib/locale'


import VueRouter from 'vue-router'
import App from './App.vue'
import Http from './services/Http'

Vue.config.productionTip = false

Vue.use(VueRouter)
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
            name: 'repository-detail',
            path: '/repository/:name',
            component: require('./components/repositories/Detail.vue')
        },
        {
            name: 'add',
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
        Http.init()
    },
})