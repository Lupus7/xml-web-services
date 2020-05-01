<template>
    <div style="width:90%" class="container">
        <br>
        <table class="table table-bordered table-sm" v-if="this.transmissions.length>0" >
          <thead class="thead-dark">
            <tr>
              <th colspan="3"><h3> Transmissions </h3></th> 
            </tr>
          </thead>

          <tbody >
            <tr v-for="transmission in this.transmissions" :key="transmission.id" >
              <td  scope="row">{{transmission}}</td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-warning" v-on:click="editTransmission(transmission)" data-toggle="modal" data-target="#edittrans" > <b-icon icon="wrench" aria-hidden="true"/> Edit Transmission </b-button> </center> </td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-danger" v-on:click="removeTransmission(transmission)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove transmission </b-button> </center> </td>
            </tr>           
          </tbody>

        </table>

        <div class="modal-footer" style="">
            <b-button class="btn btn-success" href="#" data-toggle="modal" data-target="#newtrans" > <b-icon icon="plus-circle" aria-hidden="true"/> New Transmission</b-button>
        </div> 

    <!-- Add New Transmission -->
    <div class="modal fade" id="newtrans" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Transmission</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="reset()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Transmission Name</label>
                        <b-input placeholder="Enter Transmission Name" v-model="transF" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success"  @click="addNewTransmission()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>

            </div>
        </div>
    </div>

    <!-- Add New Transmission -->
    <div class="modal fade" id="edittrans" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Transmission</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Transmission Name</label>
                        <b-input placeholder="Enter Transmission Name" v-model="transEdit" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success"  @click="editTransmissionFinal()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
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
            transmissions:[],
            transF:"",
            transEdit:"",
        }
    },
    methods:{

        removeTransmission(){

        },

        addNewTransmission(){

        },
        editTransmission(transm){
            this.transEdit = transm;
        },
        editTransmissionFinal(){

        },
        reset(){
            this.transF = "";
        }

    },
    created(){
        axios.get('/api/transmissions').then(response => { 
        this.transmissions = response.data;
       
             
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