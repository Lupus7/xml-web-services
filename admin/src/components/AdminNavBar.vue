<template>
    <div>
      <!-- Navbar -->
      <b-navbar type="light" variant="light">
          <div class="container">
            <b-navbar-nav style="margin-left:0.9rem" >

                <b-nav-item  href="#" @click="showClients()" ><b-icon icon="people-fill" variant="dark" aria-hidden="true"/> Clients</b-nav-item>

                <b-nav-item-dropdown text="Cars">
                <b-dropdown-item @click="showBrands()">Brands</b-dropdown-item>
                <b-dropdown-item @click="showModels()">Models</b-dropdown-item>
                <b-dropdown-divider></b-dropdown-divider>
                <b-dropdown-item  @click="showFuels()"> Fuels</b-dropdown-item>
                <b-dropdown-item @click="showTransmissions()" >Transmissions</b-dropdown-item>
                <b-dropdown-item @click="showClasses()" >Classes</b-dropdown-item>
                </b-nav-item-dropdown>

                <b-nav-item data-toggle="modal" data-target="#register" ><b-icon icon="building" variant="dark" aria-hidden="true"/> Company Registration</b-nav-item>

                <b-nav-item @click="showComments()" > <b-icon icon="chat-dots-fill" aria-hidden="true" variant="dark" /> Comments</b-nav-item>

                <b-nav-item @click="showPermissions()" > <b-icon icon="tools" aria-hidden="true" variant="dark" /> Permissions</b-nav-item>


            </b-navbar-nav>
          </div>
      </b-navbar >


     <!-- Company Registration -->
    <div class="modal fade" id="register" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Company Registration <b-icon  icon="building" variant="dark" aria-hidden="true"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="resetValues()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <b-form>
                        <div class="form-group">
                            <label>Company Name</label>
                            <b-input placeholder="Enter Company Name" v-model="companyNameF" type="text" class="form-control"/>
                        </div>

                            <div class="form-group">
                            <label>Email</label>
                            <b-input placeholder="Enter Email" v-model="emailF" type="email" class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>Address</label>
                            <b-input placeholder="Enter Address" v-model="addressF" type="text" class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>Business Number</label>
                            <b-input  placeholder="Enter Bussines Number" v-model="businessNumberF" type="text" class="form-control"/>
                        </div>

                    </b-form>
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-success" @click="register()" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" @click="resetValues()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
                </div>
            </div>
        </div>
    </div>

      <!-- Komponente-->
      <AdminBrand  v-if="brandBool"/>
      <AdminModel  v-if="modelBool"/>
      <AdminFuel  v-if="fuelBool"/>
      <AdminTransmission  v-if="transBool"/>
      <AdminClass  v-if="classBool"/>
      <AdminComments v-if="commentsBool"/>
      <AdminPermissions v-if="persmissionsBool"/>
      <AdminClients v-if="clientsBool"/>




    </div>
    
</template>

<script>
import AdminBrand from "./AdminBrand"
import AdminModel from "./AdminModel"
import AdminFuel from "./AdminFuel"
import AdminTransmission from "./AdminTransmission"
import AdminClass from "./AdminClass"
import AdminComments from './AdminComments'
import AdminPermissions from "./AdminPermissions"
import AdminClients from "./AdminClients"


export default {
    data(){
        return{
            brandBool:false,
            modelBool:false,
            fuelBool:false,
            transBool:false,
            classBool:false,
            commentsBool:false,
            persmissionsBool:false,
            clientsBool:false,
            companyNameF:"",emailF:"",addressF:"",businessNumberF:"",
        }
    },
    components: {
        AdminBrand,
        AdminModel,
        AdminFuel,
        AdminTransmission,
        AdminClass,
        AdminComments,
        AdminPermissions,
        AdminClients
    },
    methods:{

        showBrands(){
            this.brandBool = true;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;

           
        },
        showModels(){
            this.brandBool = false;
            this.modelBool = true;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showFuels(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = true;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showTransmissions(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = true;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showClasses(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = true;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showComments(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = true;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showPermissions(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = true;
            this.clientsBool = false;
        },
         showClients(){
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = true;
        },
        register(){

        },
        resetValues(){
            this.companyNameF = "";
            this.emailF = "",
            this.addressF = "",
            this.businessNumberF = "";
        }

    },
    created(){

    }
    
}
</script>

<style scoped>
.modal-header{
  border-bottom: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}
.modal-footer{
  border-top: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}
</style>