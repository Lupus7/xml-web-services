<template>

    <div class="container" style=" width:90%">
        <br>
        <b-card-group deck class="row" >
            <div v-for="user in this.users" :key="user.id"  class="col-12 col-md-3 col-lg-4" >

                <b-card>

                    <b-card-title>User Email</b-card-title>

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
export default {
    data(){
        return{
            users:[1,2,3,4,5,6,7,8,9,10],
        }
    },
    methods:{
       blockUser(){

       },
       unblockUser(){

       },
       removeUser(){

       }

    },
    created(){

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

</style>