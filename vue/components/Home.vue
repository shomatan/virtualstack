<template>
  <div>
    <div class="heading">
      <template v-if="userState.authenticated">
        <h1>Repositories</h1>
      </template>
      <template v-else>
        <p>Dev-test pipeline automation and private registries</p>
      </template>
    </div>
    <div v-if="userState.authenticated">
      <div class="panel panel-default">
        <table class="task-tbl table table-striped table-hover">
          <tbody v-for="r in repositories">
            <tr>
              <td>{{ r.name }}</td>
              <td><router-link :to="{ name: 'repository-detail', params: { name: r.name }}">DETAILS</router-link></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div v-else>
      <div class="container">
        <div class="row">
          <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
              <div class="panel-heading">Sign Up</div>
              <div class="panel-body">

                <div class="alert alert-danger" role="alert" v-if="failed">
                  {{ messages }}
                </div>

                <div class="form-group">
                  <label for="email" class="col-md-4 control-label">First name</label>
                  <div class="col-md-6">
                    <input id="first_name" type="text" v-model="firstName" class="form-control" required autofocus>
                  </div>
                </div>

                <div class="form-group">
                  <label for="email" class="col-md-4 control-label">Last name</label>
                  <div class="col-md-6">
                    <input id="last_name" type="text" v-model="lastName" class="form-control" required autofocus>
                  </div>
                </div>

                <div class="form-group">
                  <label for="email" class="col-md-4 control-label">E-Mail Address</label>
                  <div class="col-md-6">
                    <input id="email" type="email" v-model="email" class="form-control" required autofocus>
                  </div>
                </div>

                <div class="form-group">
                  <label for="password" class="col-md-4 control-label">Password</label>
                  <div class="col-md-6">
                    <input id="password" type="password" v-model="password" class="form-control" required autofocus>
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-md-8 col-md-offset-4">
                    <button v-on:click="create" class="btn btn-primary">
                        Create
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { http } from '../services'
import { userStore } from '../stores'

export default {

  data: function () {
    return {
      isLoading: false,
      interval: null,
      repositories: [],
      failed: false,
      messages: '',
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      userState: userStore.state
    }
  },

  created: function () {
    this.fetchData()

    this.interval = setInterval(function () {
      this.fetchData();
    }.bind(this), 10000);
  },

  watch: {
    '$route': 'fetchData'
  },

  methods: {
    fetchData: function () {

      if( ! this.userState.authenticated) return

      var self = this
      self.isLoading = true
      var m = jsRoutes.com.github.virtualstack.controllers.api.v1.ImageRepositoryController.all()
      console.log("Repository API:" + m.url)
      http.get(m.url, response => {
        self.repositories = response.data
        self.isLoading = false
        console.log("repositories: " + JSON.stringify(self.repositories));
      })
    },

    async create () {
      try {
        await userStore.store(this.firstName, this.lastName, this.email, this.password)
        this.firstName = ''
        this.lastName = ''
        this.email = ''
        this.password = ''
        this.$router.push('/')
        this.failed = false
      } catch (err) {
        this.password = ''
        this.failed = true
        this.messages = err.response.data.message
      }
    }
  },

  beforeDestroy: function(){
    clearInterval(this.interval);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .heading {
        overflow: auto;
        padding-bottom: 10px;
    }
    .heading h1 {
        float: left;
    }
    .logo {
        width: 136px;
        height: 136px;
        float: right;
    }
    .task-tbl tr {
        cursor: pointer;
    }
</style>