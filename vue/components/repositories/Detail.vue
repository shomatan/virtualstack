<template>
    <div>
        <div class="heading">
            <h1>{{ repository.name }}</h1>
        </div>
        <div>
            <div class="panel panel-default">
                <table class="task-tbl table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Tag Name</th>
                            <th>Compressed Size</th>
                            <th>Last Updated</th>
                        </tr>
                    </thead>
                    <tbody v-for="t in repository.tags">
                        <tr>
                            <td>{{ t.name }}</td>
                            <td></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {

        data: function () {
            return {
                isLoading: false,
                repository: {},
                error: null
            }
        },

        created: function () {
            this.fetchData()
        },

        watch: {
            '$route': 'fetchData'
        },

        methods: {
            fetchData: function () {
                var self = this
                self.isLoading = true
                var m = jsRoutes.com.github.virtualstack.controllers.api.v1.ImageRepositoryController.detail(this.$route.params.name)
                console.log("Repository API:" + m.url)
                axios.get(m.url)
                    .then(function (response) {
                        self.repository = response.data
                        self.isLoading = false
                        console.log("repository: " + JSON.stringify(self.repository));
                    })
                    .catch(function (error) {
                        self.fetchError = error
                        self.isLoading = false
                        console.log("repository: " + JSON.stringify(self.fetchError));
                    })
            },
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1 {
        color: #42b983;
    }
</style>