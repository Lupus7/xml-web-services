<template>
    <div id="app">
          <NavBar/>
          <router-view></router-view>
    </div>
</template>

<script>
import auth from './auth'
import NavBar from './components/NavBar'
export default {
  name: 'app',
  data(){
    return{

    }
  },
  methods: {
    logout(){
      auth.logout(this)
    },
    isAuthenticated(){
      return auth.isAuthenticated()
    }
  },
  components: {
    NavBar
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
