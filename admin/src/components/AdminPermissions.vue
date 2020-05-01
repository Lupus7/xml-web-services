<template>

    <div class="container" style=" width:90%">
        <br>
        <b-card-group deck class="row" >
            <div v-for="user in this.users" :key="user.id"  class="col-12 col-md-3 col-lg-4" >

                <b-card  img-src="../assets/company.png" img-alt="Image" img-top img-height="200px" img-width="50px" style="width:285px">

                    <b-card-title>User Email</b-card-title>

                    <b-card-sub-title class="mb-2">Ime kompanije</b-card-sub-title>
                    <b-card-sub-title class="mb-3">Adresa</b-card-sub-title>
               
                    <div class="modal-footer">  
                        <center> <b-button type="button" class="btn btn-success"  @click="goToPermissions()" style="width:100%" > <b-icon icon="lock-fill" aria-hidden="true"> </b-icon> Permissions</b-button> </center>
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
        goToPermissions(){
            this.$router.push("/permission");
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