<template>
    <div>
        <header class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <router-link to="/" class="navbar-brand">Virtual Stack</router-link>
                <ul class="nav navbar-nav pull-right">
                    <li><router-link to="/">Dashboard</router-link></li>
                    <li><router-link to="/add">Create</router-link></li>
                    <li><router-link :to="{ name: 'Login' }" v-if="!isLoggedIn">Login</router-link></li>
                    <li><a href="#" v-if="isLoggedIn" @click="logout">Logout</a></li>
                </ul>
            </div>
        </header>
        <div class="container">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {

  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  },

  computed: {
    isLoggedIn() {
      return this.$store.getters.isLoggedIn;
    }
  }
}
</script>