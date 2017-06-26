import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

// element-ui
//import { DatePicker } from 'element-ui'
//import lang from 'element-ui/lib/locale/lang/en'
//import locale from 'element-ui/lib/locale'



import App from './App.vue'
import Http from './services/Http'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(Vuex)
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
            name: 'Login',
            path: '/auth/login',
            component: require('./components/auth/Login.vue')
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

const LOGIN = "LOGIN";
const LOGIN_SUCCESS = "LOGIN_SUCCESS";
const LOGOUT = "LOGOUT";

const store = new Vuex.Store({
    state: {
        isLoggedIn: !!localStorage.getItem("jwt-token")
    },
    mutations: {
        [LOGIN] (state) {
            state.pending = true;
        },
        [LOGIN_SUCCESS] (state) {
            state.isLoggedIn = true;
            state.pending = false;
        },
        [LOGOUT](state) {
            state.isLoggedIn = false;
        }
    },
    actions: {
        login({ commit }, creds) {
            commit(LOGIN); // show spinner
            return new Promise(resolve => {
                setTimeout(() => {
                    var login_param = {email: creds.email, password: creds.password, rememberMe: false }
                    var m = jsRoutes.com.github.virtualstack.controllers.api.v1.auth.SignInController.submit()
                    Http.post(m.url, login_param, res => {
                        commit(LOGIN_SUCCESS);
                        resolve();
                    })
                }, 1000);
            });
        },
        logout({ commit }) {
            localStorage.removeItem("jwt-token");
            commit(LOGOUT);
        }
    },
    getters: {
        isLoggedIn: state => {
            return state.isLoggedIn
        }
    }
});

new Vue({
    el: '#app',
    router: router,
    template: '<App/>',
    components: { App },
    store,
    created: function () {
        Http.init()
    },
})