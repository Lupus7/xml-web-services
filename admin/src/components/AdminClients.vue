<template>

    <div class="container" style=" width:90%">
        <br>
        <b-card-group deck class="row" >
            <div v-for="user in this.users" :key="user.id"  class="col-12 col-md-3 col-lg-4" >

                <b-card>

                    <b-card-title> {{user.email}} </b-card-title>

                    <div class="modal-footer">  
                        <div class="row">
                            <div class="col">
                                <b-button v-if="user.blocked" type="button" class="btn btn-dark"  @click="blockUser(user)" style="width:120%" > <b-icon icon="unlock" aria-hidden="true"> </b-icon> Unblock</b-button>
                                <b-button v-if="!user.blocked" type="button" class="btn btn-dark"  @click="unblockUser(user)" style="width:120%" > <b-icon icon="lock" aria-hidden="true"> </b-icon> Block</b-button>


                            </div>
                            <div class="col">
                                <b-button type="button" class="btn btn-danger"  @click="removeUser(user)" style="width:120%" > <b-icon icon="x" aria-hidden="true"> </b-icon> Remove</b-button>
                            </div>
                        </div>                    
                    </div>
                </b-card>

                <br>
            </div>

        </b-card-group>
        
    </div>
    
</template>

<script>
import axios from 'axios'

export default {
    data(){
        return{
            users:[],
        }
    },
    methods:{
       blockUser(user){
           axios.put("/admin/client-control/block", 
                user.email, 
            ).then(response => { 
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                        title: "Blocking User",
                        variant: "info",
                        solid: true
                    })

                }else{

                    this.$bvToast.toast(response.data, {
                        title: "Blocking User",
                        variant: "warning",
                        solid: true
                    })

                }
            });
       },
       unblockUser(user){
             axios.put("/admin/client-control/activate", 
                user.email, 
            ).then(response => { 
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                        title: "Unblocking User",
                        variant: "info",
                        solid: true
                    })

                }else{

                    
                    this.$bvToast.toast(response.data, {
                        title: "Unblocking User",
                        variant: "warning",
                        solid: true
                    })

                }
            });
       },
       removeUser(user){
           axios.delete("/admin/client-control/", { data: { email: user.email }}).then(response => {
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                            title: "Removing User",
                            variant: "info",
                            solid: true
                    })

                }else{

                    this.$bvToast.toast(response.data, {
                        title: "Removing User",
                        variant: "warning",
                        solid: true
                    })
                }
           });

       }

    },
    created(){
        axios.get('/admin/client-control').then(response => { 
            this.users = response.data;
      });
    }
}
</script>

<style scoped>

.modal-footer{
  border-top: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}

.modal-header{
  border-bottom: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}

</style>