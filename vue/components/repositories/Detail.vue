<template>
    <div>
        <div class="heading">
            <h1>{{ repository.name }}</h1>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {

        data: function () {
            return {
                repository: {
                    name: ''
                }
            }
        },

        created: function () {
            var self = this
            if (typeof self.$route.params.name !== 'undefined') {
                var m = jsRoutes.com.github.virtualstack.controllers.api.v1.RepositoryController.detail(this.$route.params.name)
                console.log("Repository API:" + m.url)
                axios.get(m.url)
                    .then(function (response) {
                        self.repository = response.data
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            }
        },

        methods: {

        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1 {
        color: #42b983;
    }
</style>