<template>
    <div style="width:90%" class="container">
        <br>
        <table class="table table-bordered table-sm" v-if="this.fuels.length>0">
          <thead class="thead-dark">
            <tr>
              <th colspan="3"><h3> Fuel Types </h3></th> 
            </tr>
          </thead>

          <tbody >
            <tr v-for="fuel in this.fuels" :key="fuel.id" >
              <td  scope="row">{{fuel}}</td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-warning" @click="editFuel(fuel)" data-toggle="modal" data-target="#editfuel"  > <b-icon icon="wrench" aria-hidden="true"/> Edit Fuel </b-button> </center> </td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-danger" @click="removeFuel(fuel)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove Fuel </b-button> </center> </td>
            </tr>           
          </tbody>

        </table>

        <div class="modal-footer" style="">
            <b-button class="btn btn-success" href="#" data-toggle="modal" data-target="#newfuel" > <b-icon icon="plus-circle" aria-hidden="true"/> New Fuel Type</b-button>
        </div> 

    <!-- Add New Fuel -->
    <div class="modal fade" id="newfuel" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Fuel Type</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="reset()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Fuel Type</label>
                        <b-input placeholder="Enter Fuel Type" v-model="fuelF" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" @click="addNewFuel()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>

            </div>
        </div>
    </div>

     <!-- Edit Fuel -->
    <div class="modal fade" id="editfuel" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Fuel Type</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Fuel Type</label>
                        <b-input placeholder="Enter Fuel Type" v-model="fuelEdit" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" @click="editFuelFinal()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal"> <b-icon icon="x-circle"> </b-icon>  Close</button>
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
            fuels:[],
            fuelF:"",
            fuelEdit:""
        }
    },
    methods:{

        removeFuel(){

        },

        addNewFuel(){

        },
        editFuel(fuel){
            this.fuelEdit = fuel;
        },
        editFuelFinal(){

        },
        reset(){
            this.fuelF = "";
        }

    },
    created(){
        axios.get('/api/fuels').then(response => { 
        this.fuels = response.data;
       
             
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

</style>