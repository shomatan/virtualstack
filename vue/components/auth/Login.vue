<template>
  <div>
    <div class="heading">
        <h1>Login</h1>
    </div>
    <div>
      <div class="container">
        <div class="row">
          <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
              <div class="panel-heading">Login</div>
              <div class="panel-body">

                <div class="alert alert-danger" role="alert" v-if="failed">
                  Wrong email or password.
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
                    <button v-on:click="login" class="btn btn-primary">
                            Login
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
import { http } from '../../services'
import { userStore } from '../../stores'

export default {

  data() {
    return {
      email: "",
      password: "",
      failed: false,
    }
  },

  methods: {
    login() {
      try {
        userStore.login(this.email, this.password)
        this.$router.push('/')
      } catch (err) {
        this.password = ''
        this.failed = true
      }
    }
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
