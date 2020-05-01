<template>
    <div style="width:90%" class="container">
        <br>
        <table class="table table-bordered table-sm" v-if="this.brands.length>0">
          <thead class="thead-dark">
            <tr>
              <th colspan="3"><h3> Brands </h3></th> 
            </tr>
          </thead>

          <tbody >
            <tr v-for="brand in this.brands" :key="brand.id" >
              <td  scope="row">{{brand}}</td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-warning" v-on:click="editBrand(brand)"  data-toggle="modal" data-target="#editbrand"  > <b-icon icon="wrench" aria-hidden="true"/> Edit Brand </b-button> </center> </td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-danger" v-on:click="removeBrand(brand)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove Brand </b-button> </center> </td>

            </tr>           
          </tbody>

        </table>

        <div class="modal-footer" style="">
            <b-button class="btn btn-success" href="#" data-toggle="modal" data-target="#newbrand" > <b-icon icon="plus-circle" aria-hidden="true"/> New Brand</b-button>
        </div> 

    <!-- Add New Brand -->
    <div class="modal fade" id="newbrand" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Brand</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="reset()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Name of Brand</label>
                        <b-input  placeholder="Enter Brand Name" v-model="brandF" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" @click="addNewBrand()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()" > <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>

            </div>
        </div>
    </div>

    <!-- Edit Brand -->
    <div class="modal fade" id="editbrand" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">New Brand</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                        <label>Name of Brand</label>
                        <b-input  placeholder="Enter Brand Name" v-model="brandEdit" type="text" class="form-control"/>
                        </div>
                    
                    </b-form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" @click="editBrandFinaly()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" @click="reset()" > <b-icon icon="x-circle"> </b-icon>  Close</button>
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
            brands:[],
            brandF:"",
            brandEdit:"",
        }
    },
    methods:{

        removeBrand(){

        },

        addNewBrand(){

        },
        editBrand(brand){
            this.brandEdit = brand;
        },
        editBrandFinal(){

        },
        reset(){
            this.brandF = "";
        }

    },
    created(){
        axios.get('/api/brands').then(response => { 
        this.brands = []
        for(let b of response.data)
          this.brands.push(b.Name);
             
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