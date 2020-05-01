<template>
    <div>
    <b-navbar toggleable="lg" class="navbar navbar-expand-lg  navbar-dark bg-dark" style="height:70px" >
             <div class="container">
                <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-link class="nav-link active" type="button" v-on:click="homepage()" href="#">  <b-icon icon="house-door-fill" aria-hidden="true"></b-icon> Homepage </b-link>
                    <b-link class="nav-link active" type="button" v-on:click="adminPage()" href="#">  <b-icon icon="briefcase-fill" aria-hidden="true"></b-icon> Admin Panel</b-link>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">

                    <b-link right class="nav-link active" type="button" v-on:click="login()" href="#"> <b-icon icon="person-check-fill" aria-hidden="true"></b-icon> Log in</b-link>
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
     
        homepage(){
            this.$router.push("/");
        },
        login(){

        },
        logout(){

        },
        adminPage(){
            this.$router.push("/admin")
        }
    },
    created(){

    }
    
}
</script>