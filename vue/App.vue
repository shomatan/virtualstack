<template>
  <div>
    <header class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <router-link to="/" class="navbar-brand">Virtual Stack</router-link>
        <ul class="nav navbar-nav pull-right">
          <li><router-link to="/">Dashboard</router-link></li>
          <li><router-link to="/add" v-if="userState.authenticated">Create</router-link></li>
          <li><router-link to="/auth/login" v-if="!userState.authenticated">Login</router-link></li>
          <li><a href="#" v-if="userState.authenticated" @click="logout">Logout</a></li>
        </ul>
      </div>
    </header>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { userStore } from './stores'

export default {

  data () {
    return {
      userState: userStore.state
    }
  },

  methods: {
    logout() {
      try {
        userStore.logout()
        this.$router.push('/')
      } catch (err) {
        this.failed = true
      }
    }
  }
}
</script>
