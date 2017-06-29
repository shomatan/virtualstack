import { http } from '../services'

export const userStore = {

  state: {
    user: {},
    authenticated: false,
  },

  /**
   * Init the store.
   *
   */
  init () {
    var m = jsRoutes.com.github.virtualstack.controllers.HomeController.profile()
    http.put(m.url, {}, response => {
      this.state.user = response.data
      this.state.authenticated = true
    }, error => {
      this.state.authenticated = false
      // reject(error)
    })
  },

  /**
   * Log a user in.
   *
   * @param  {String}   email
   * @param  {String}   password
   */
  login (email, password) {
    return new Promise((resolve, reject) => {
      var m = jsRoutes.com.github.virtualstack.controllers.api.v1.auth.SignInController.submit()
      http.post(m.url, { email, password, rememberMe: false }, response => {
        this.state.user = response.data
        this.state.authenticated = true
      }, error => {
        reject(error)
      })
    })
  },

  /**
   * Log the current user out.
   */
  logout () {
    return new Promise((resolve, reject) => {
      var m = jsRoutes.com.github.virtualstack.controllers.HomeController.signOut()
      http.delete(m.url, {}, response => {
        // resolve(data)
        this.state.authenticated = false
        localStorage.removeItem('jwt-token')
      }, error => {
        this.state.authenticated = false
        reject(error)
      })
    })
  },
}
