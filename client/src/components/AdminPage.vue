<template>
    <div>
        <nav class="navbar navbar-dark bg-dark">     
      <ul class="nav">
        <li class="nav-item">
          <b-link class="nav-link active" type="button" href="#" data-toggle="modal" data-target="#adminpage" >   
            <b-icon aria-hidden="true"></b-icon> Users
          </b-link>

          <b-link class="nav-link active" type="button" href="#" data-toggle="modal" data-target="#search" >   
            <b-icon icon="search" aria-hidden="true"></b-icon> Cars
          </b-link>

          <b-link class="nav-link active" type="button" href="#" data-toggle="modal" data-target="#search" >   
            <b-icon icon="search" aria-hidden="true"></b-icon> Comments
          </b-link>
        </li>
      </ul>      
    </nav>
    </div>
    
</template>

<script>
//import axios from 'axios'
export default {
    name: 'AdminPage',
    data(){

    },

    methods:{

    },
    created(){

    }
}
</script>