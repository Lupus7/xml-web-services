<template>
    <div class="container">
        <br>
         <div class="tab-content ml-1" id="myTabContent">
                <div class="tab-pane fade show active" id="basicInfo" role="tabpanel" aria-labelledby="basicInfo-tab">
                    
                    <table class="table table-bordered table-sm" >
                        <thead class="thead-dark">
                            <tr>
                            <th colspan="2"><center><h3> User </h3></center></th> 
                            </tr>
                            <tr>
                            <th colspan="2"><h4> Permissions </h4></th> 
                            </tr>
                        </thead>

                        <tbody >
                            <tr v-for="permission in this.userPermissions" :key="permission.id" >
                            <td  scope="row">{{permission}}</td>
                            <td  style="width:40%" scope="row"> <center><b-button class="btn btn-danger" v-on:click="removePermission(permission)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove Permission </b-button> </center> </td>

                            </tr>           
                        </tbody>

                    </table>


                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success"  data-toggle="modal" data-target="#newperm" > <b-icon icon="plus-circle" aria-hidden="true"> </b-icon> Add New Permission</button>
                </div>
                
            </div>    

        <!-- Add New PERMISSION -->
        <div class="modal fade" id="newperm" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">List of Permissions</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="reset()">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <b-form>
                            <div class="form-group">
                            <label>Select Permission</label>
                            <b-form-select v-model="selected" :options="permissionList" :select-size="10"></b-form-select>
                            </div>
                        
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" @click="addNewPermission()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()" > <b-icon icon="x-circle"> </b-icon>  Close</button>
                    </div>

                </div>
            </div>
        </div>      


    </div>

</template>

<script>
export default {
    data(){
        return{
            userPermissions:[1,2,3],
            permissionList:[1,2,3,4,5,6,7,8,9,10,11],
            selected:"",
        }
    },
    methods:{
        addNewPermission(){

        },
        removePermission(){

        },
        reset(){
            this.selected = '';
        }
    },
    created(){

    }
}
</script>

<style scoped>


</style>