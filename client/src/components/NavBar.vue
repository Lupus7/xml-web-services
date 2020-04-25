<template>
    <div class="navbar navbar-dark bg-dark">
        
        <a  class="navbar-brand" v-on:click="homepage" href="#">Home</a>
        <a  class="navbar-brand" v-on:click="profile" href="#" >Search</a>
        <a class="navbar-brand" v-on:click="login" href="#"  >Log in</a>
        <a class="navbar-brand" v-on:click="register" href="#"  >Register</a>
        <a class="navbar-brand" v-on:click="logout" href="#"  >Log out</a>
    </div>    
</template>

<script>
export default {
    
}
</script>