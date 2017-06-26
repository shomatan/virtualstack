<template>
    <div>
        <div class="heading">
            <h1>Repositories</h1>
        </div>
        <div>
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
    </div>
</template>

<script>
    import Http from '../services/Http'

    export default {

        data: function () {
            return {
                isLoading: false,
                interval: null,
                repositories: [],
                error: null
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
                var self = this
                self.isLoading = true
                var m = jsRoutes.com.github.virtualstack.controllers.api.v1.ImageRepositoryController.all()
                console.log("Repository API:" + m.url)
                Http.get(m.url, response => {
                    self.repositories = response.data
                    self.isLoading = false
                    console.log("repositories: " + JSON.stringify(self.repositories));
                })

            },
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