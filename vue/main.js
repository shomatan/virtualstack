import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'

// element-ui
//import { DatePicker } from 'element-ui'
//import lang from 'element-ui/lib/locale/lang/en'
//import locale from 'element-ui/lib/locale'

// app
import App from './App.vue'
import Home from './components/Home.vue'

import VueRouter from 'vue-router'

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
            component: Home
        }
    ]
})

new Vue({
    el: '#app',
    router: router,
    template: '<App/>',
    components: { App }
})