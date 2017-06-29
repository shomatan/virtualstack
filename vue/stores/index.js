import Vuex from 'vuex'

const LOGIN = "LOGIN";
const LOGIN_SUCCESS = "LOGIN_SUCCESS";
const LOGOUT = "LOGOUT";

export default new Vuex.Store({
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
    login({ commit }) {
      commit(LOGIN); // show spinner
      return new Promise(resolve => {
        setTimeout(() => {
          commit(LOGIN_SUCCESS);
          resolve();
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