<template>
    <div id="app">
          <NavBar/>
          <router-view></router-view>

    </div>
</template>

<script>
import NavBar from './components/NavBar'
export default {
  name: 'app',
  data(){
    return{

    }
  },

  components: {
    NavBar
  }
}
</script>

<style>

</style>
