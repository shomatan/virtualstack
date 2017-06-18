<template>
    <div>
        <div class="heading">
            <h1>Gitbucket</h1>
        </div>
        <h3>Found {{ repositories.length }} repositories</h3>
        <div>
            <div class="panel panel-default">
                <table class="task-tbl table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Git repository name</th>
                            <th>Default branch</th>
                        </tr>
                    </thead>
                    <tbody v-for="r in repositories">
                        <tr>
                            <td>{{ r.name }}</td>
                            <td>{{ r.defaultBranch }}</td>
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
                repositories: [],
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
                var m = jsRoutes.com.github.virtualstack.controllers.api.v1.GitRepositoryController.all()
                console.log("Repository API:" + m.url)
                axios.get(m.url)
                    .then(function (response) {
                        self.repositories = response.data
                        self.isLoading = false
                        console.log("repositories: " + JSON.stringify(self.repositories));
                    })
                    .catch(function (error) {
                        self.fetchError = error
                        self.isLoading = false
                    })
            },
        }
    }
</script>
