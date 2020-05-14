<template>
    <div>
    <b-navbar toggleable="lg" class="navbar navbar-dark bg-dark" style="height:70px" >
            <div class="container">
                <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-link class="nav-link active" type="button" v-on:click="homepage()" href="#">  <b-icon icon="house-door-fill" aria-hidden="true"></b-icon> Homepage </b-link>
                    <b-link class="nav-link active" type="button" v-on:click="searchPage()" href="#">  <b-icon icon="newspaper" aria-hidden="true"></b-icon> Adds</b-link>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">

                    <b-link right class="nav-link active" type="button" v-on:click="login()" href="#"> <b-icon icon="person-check-fill" aria-hidden="true"></b-icon> Log in</b-link>
                    <b-link class="nav-link active" type="button" v-on:click="register()" href="#"> <b-icon icon="person-plus-fill" aria-hidden="true"></b-icon> Register</b-link>
                    <b-link class="nav-link active" type="button" v-on:click="logout()" href="#"><b-icon icon="person-dash-fill" aria-hidden="true"></b-icon> Log out</b-link>

                </b-navbar-nav>
            </b-collapse>
            </div>
        </b-navbar>
    </div>   
</template>

<script>

export default {

    data(){
        return{

        }
    },
    methods:{
        searchPage(){
            this.$router.push("/search");
        },
        homepage(){

        },
        login(){

        },
        register(){

        },
        logout(){

        },
       
    },
    created(){

    }
    
}
</script>