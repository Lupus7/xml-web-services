<template>
    <div style="width:90%" class="container">
        <br>
        <table class="table table-bordered table-sm" v-if="this.classes.length>0">
          <thead class="thead-dark">
            <tr>
              <th colspan="2"><h3> Car Classes </h3></th> 
              <th style="width:30%" colspan="1"> <center> <b-button class="btn btn-success" style="width:45%" href="#" data-toggle="modal" data-target="#newclass" > <b-icon icon="plus-circle" aria-hidden="true"/> New Class</b-button> </center> </th>
            </tr>
          </thead>

          <tbody >
            <tr v-for="cclass in this.classes" :key="cclass.id" style="background:	#F5F5F5" >
              <td  scope="row">{{cclass.name}}</td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-warning" v-on:click="editClass(cclass)" data-toggle="modal" data-target="#editclass"  > <b-icon icon="wrench" aria-hidden="true"/> Edit Class </b-button> </center> </td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-danger" v-on:click="removeClass(cclass)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove Class </b-button> </center> </td>
            </tr>           
          </tbody>

        </table>

    <!-- Add New Class -->
    <div class="modal fade" id="newclass" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Class</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="reset()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Car Class Name</label>
                        <b-input placeholder="Enter Class Name" v-model="classF" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" @click="addNewClass()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>

            </div>
        </div>
    </div>

      <!-- Edit Class -->
    <div class="modal fade" id="editclass" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Class</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Car Class Name</label>
                        <b-input placeholder="Enter Class Name" v-model="classEdit" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" @click="editClassFinal()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>

            </div>
        </div>
    </div>


    </div>
    
</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
            fields: [
                { key: 'Name', label:'Name', sortable: false}
                
            ],
            classes:[],
            classF:"",
            classEdit:"",
            classId:"",
        }
    },
    methods:{

        removeClass(cclass){
            event.preventDefault();
              let url = "/codebook/class/"+cclass.id;
              axios.delete(url).then(response => {
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                            title: "Removing Class",
                            variant: "success",
                            solid: true
                    })
                    this.fill();

                }else{

                    this.$bvToast.toast(response.data, {
                        title: "Removing Class",
                        variant: "warning",
                        solid: true
                    })
                }
           });

        },

        addNewClass(){
            event.preventDefault();
            axios.post("/codebook/class",{ 
                "name":this.classF, 
            }).then(response => { 
                if(response.status === 200){
                    this.classF = "";
                    this.$bvToast.toast(response.data, {
                        title: "New Class",
                        variant: "success",
                        solid: true
                    })
                    this.fill();

                }else{

                    
                    this.$bvToast.toast(response.data, {
                        title: "New Class",
                        variant: "warning",
                        solid: true
                    })

                }
            });

        },
        editClass(cclass){
            this.classEdit = cclass.name;
            this.classId = cclass.id;

        },
        editClassFinal(){
            event.preventDefault();
            let url = "/codebook/class/"+this.classId;
            axios.put(url,{ 
                "id":this.classId,
                "name":this.classEdit 
            }).then(response => { 
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                        title: "Class Edit",
                        variant: "success",
                        solid: true
                    })
                    this.fill();

                }else{

                    
                    this.$bvToast.toast(response.data, {
                        title: "Class Edit",
                        variant: "warning",
                        solid: true
                    })

                }
            });


        },
        reset(){
            this.classF = "";
        },
        fill(){
            axios.get('/codebook/class').then(response => { 
                this.classes = response.data;           
            });
        }

    },
    created(){
        this.fill();
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