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
                            <td @click="detail(r.name)">DETAILS</td>
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
                axios.get('/api/v1/repositories')
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
            detail: function (name) {
                console.log("name:" + name);
                this.$router.push({ name: 'repository-detail', params : { name: name }} );
            },
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