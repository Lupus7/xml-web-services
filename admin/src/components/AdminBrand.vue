<template>
    <div style="width:90%" class="container">
        <br>
        <table class="table table-bordered table-sm " v-if="this.brands.length>0">
          <thead class="thead-dark">
            <tr>
              <th colspan="2"><h3> Brands </h3></th> 
              <th style="width:30%" colspan="1"> <center> <b-button class="btn btn-success" style="width:45%" href="#" data-toggle="modal" data-target="#newbrand" > <b-icon icon="plus-circle" aria-hidden="true"/> New Brand</b-button> </center> </th>
            </tr>
          </thead>

          <tbody >
            <tr v-for="brand in this.brands" :key="brand.id" style="background:	#F5F5F5" >
              <td  scope="row">{{brand.name}}</td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-warning" v-on:click="editBrand(brand)"  data-toggle="modal" data-target="#editbrand"  > <b-icon icon="wrench" aria-hidden="true"/> Edit Brand </b-button> </center> </td>
              <td  style="width:30%" scope="row"> <center><b-button class="btn btn-danger" v-on:click="removeBrand(brand)" > <b-icon icon="x-circle" aria-hidden="true"/> Remove Brand </b-button> </center> </td>

            </tr>           
          </tbody>
           
        </table>

      
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
                    <button type="button" class="btn btn-success" data-dismiss="modal" @click="addNewBrand()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
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
                    <button type="button" class="btn btn-success" data-dismiss="modal" @click="editBrandFinal()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
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
            brandId:"",
        }
    },
    methods:{

        removeBrand(brand){
            event.preventDefault();
            let url = "/codebook/brand/"+brand.id;
            axios.delete(url).then(response => {
                if(response.status === 200){
                    this.$bvToast.toast(response.data, {
                            title: "Removing Brand",
                            variant: "success",
                            solid: true
                    })
                    this.fill();

                }else{

                    this.$bvToast.toast(response.data, {
                        title: "Removing Brand",
                        variant: "warning",
                        solid: true
                    })
                }
           });

        },

        addNewBrand(){
            event.preventDefault();
            axios.post("/codebook/brand",{ 
                "name":this.brandF, 
            }).then(response => { 
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                        title: "New Brand",
                        variant: "success",
                        solid: true
                    })
                    this.fill();
                    

                }else{

                    
                    this.$bvToast.toast(response.data, {
                        title: "New Brand",
                        variant: "warning",
                        solid: true
                    })

                }
            });

        },
        editBrand(brand){
            this.brandEdit = brand.name;
            this.brandId = brand.id;
        },
        editBrandFinal(){
            event.preventDefault();
            let url = "/codebook/brand/"+this.brandId;
            axios.put(url,{ 
                "name":this.brandEdit,
                "id":this.brandId,
            }).then(response => { 
                if(response.status === 200){

                    this.$bvToast.toast(response.data, {
                        title: "Brand Edit",
                        variant: "success",
                        solid: true
                    })
                    this.fill();

                }else{

                    
                    this.$bvToast.toast(response.data, {
                        title: "Brand Edit",
                        variant: "warning",
                        solid: true
                    })

                }
            });

        },
        reset(){
            this.brandF = "";
        },
        fill(){
            axios.get('/codebook/brand').then(response => { 
                this.brands = response.data;           
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