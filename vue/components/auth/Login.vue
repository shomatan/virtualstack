<template>
    <div>
        <div class="heading">
            <h1>Login</h1>
        </div>
        <div>
            <form @submit.prevent="login({ email, password })">
                <input type="text" placeholder="email" v-model="email">
                <input type="password" placeholder="password" v-model="password">
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</template>

<script>

    export default {

        data() {
            return {
                email: "",
                password: ""
            }
        },

        methods: {
            login() {
                this.$store.dispatch("login", {
                    email: this.email,
                    password: this.password
                }).then(() => {
                    this.$router.push("/")
                });
            },
            logout() {
                this.$store.dispatch('logout');
            }
        },

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