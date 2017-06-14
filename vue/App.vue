<template>
    <div>
        <header class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <router-link to="/" class="navbar-brand">Virtual Stack</router-link>
                <ul class="nav navbar-nav pull-right">
                </ul>
            </div>
        </header>
        <div class="container">
            <router-view></router-view>
        </div>
    </div>
</template>