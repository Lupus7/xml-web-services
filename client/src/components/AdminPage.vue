<template>
    <div>
      <br>
      <b-button-group>
        <b-button v-on:click="showusers = true" style="margin:5px;">Users</b-button>
        <b-dropdown right text="cars" style="margin:5px;">
          <b-dropdown-item v-on:click="showbrands=true">Brands</b-dropdown-item>
          <b-dropdown-item v-on:click="showmodels=true">Models</b-dropdown-item>
          <b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item v-on:click="showfuels=true"> Fuels</b-dropdown-item>
          <b-dropdown-item v-on:click="showtransmissions=true">Transmissions</b-dropdown-item>
          <b-dropdown-item v-on:click="showclasses=true">Classes</b-dropdown-item>
        </b-dropdown>
        <b-dropdown right text="Register" data-toggle="modal" data-target="#register" style="margin:5px;">
          <b-dropdown-item v-on:click="registeruser=true">User</b-dropdown-item>
          <b-dropdown-item v-on:click="registerfirm=true">Firm</b-dropdown-item>
        </b-dropdown>
        
        <b-button style="margin:5px;">Comments</b-button>
      </b-button-group>
      <br>
       <br>
       <table class="table table-bordered table-dark" id="user-table" v-if="showusers" @mouseleave="showusers=false">
          <thead>
            <tr>
              <th scope="col">Customer</th>
              <th scope="col">First</th>
              <th scope="col">Last</th>
              <th scope="col">Age</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in this.users" :key="user.id">
              <th scope="row">{{user.type}}</th>
              <td>{{user.name.first}}</td>
              <td>{{user.name.last}}</td>
              <td>{{user.age}}</td>
              <td><button type="button" class="btn btn-danger" v-on:click="removeUser(user.name.first)">Remove user</button></td>
              <td>
                <button v-if="!user.blocked" type="button" class="btn btn-warning" v-on:click="user.blocked=true">Block user</button>
                <button v-if="user.blocked" type="button" class="btn btn-info" v-on:click="user.blocked=false">Unblock user</button>
              </td>
            </tr>
          </tbody>
        </table>


         <table class="table table-bordered" id="car-brand" v-if="showbrands" @mouseleave="showbrands=false">
          <thead>
            <tr>
              <th scope="col">Name of the brand</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="brand in this.brands" :key="brand.id">
              <th scope="row">{{brand.name}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeBrand(brand.name)">Remove brand</button></td>
            </tr>
          </tbody>
        </table>

        <table class="table" id="car-model" v-if="showmodels" @mouseleave="showmodels=false">
          <thead>
            <tr>
              <th scope="col">Name of the model</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="model in this.models" :key="model.id">
              <th scope="row">{{model.name}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeModel(model.name)">Remove model</button></td>
            </tr>
          </tbody>
        </table>


        <table class="table" id="car-fuel" v-if="showfuels" @mouseleave="showfuels=false">
          <thead>
            <tr>
              <th scope="col">Type of fuel</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="fuel in this.fuels" :key="fuel.id">
              <th scope="row">{{fuel.name}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeFuel(fuel.name)">Remove fuel type</button></td>
            </tr>
          </tbody>
        </table>

        <table class="table" id="car-transmissions" v-if="showtransmissions" @mouseleave="showtransmissions=false">
          <thead>
            <tr>
              <th scope="col">Type of transmission</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="tr in this.transmissions" :key="tr.id">
              <th scope="row">{{tr.name}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeTransmission(tr.name)">Remove transmission type</button></td>
            </tr>
          </tbody>
        </table>

        <table class="table" id="car-classes" v-if="showclasses" @mouseleave="showclasses=false">
          <thead>
            <tr>
              <th scope="col">Car class</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="cl in this.classes" :key="cl.id">
              <th scope="row">{{cl.name}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeClass(cl.name)">Remove car type</button></td>
            </tr>
          </tbody>
        </table>

        <div class="modal fade" id="register" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">Register</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <b-form>
                  <div class="form-group" v-if="registeruser">
                    <label>First and last name</label>
                    <b-input v-model="firstLastName" type="text" class="form-control"/>
                  </div>
                  <div class="form-group" v-if="registerfirm">
                    <label>Firm name</label>
                    <b-input v-model="firmName" type="text" class="form-control"/>
                  </div>
                  <div class="form-group">
                    <label>Email</label>
                    <b-input v-model="email" type="text" class="form-control"/>
                  </div>
                  <div class="form-group">
                    <label>Password</label>
                    <b-input v-model="password" type="password" class="form-control"/>
                  </div>

                  <div class="form-group">
                    <label>Address</label>
                    <b-input v-model="address" type="text" class="form-control"/>
                  </div>
                </b-form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-success" @click="register()" data-dismiss="modal" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal"> <b-icon icon="x-circle"> </b-icon>  Close</button>
              </div>
            </div>
          </div>
        </div>


    </div>
</template>

<script>
//import axios from 'axios'
export default {
    name: 'AdminPage',
    data(){
      return{
          users: [
          { type: 'user', blocked: true, age: 40, name: { first: 'Dickerson', last: 'Macdonald' } },
          { type:'firm', blocked: false, age: 21, name: { first: 'Larsen', last: 'Shaw' } }
          ],
          brands: [
            { name: 'Opel'}, {name: 'Wolksvagen'},{name: 'Fiat'}
          ],
          models: [
            {name:'Astra'},{name:'Passat'},{name:'Stilo'}
          ],
          fuels: [
            {name:'Gas'},{name:'Diesel'},{name:'Gasoline'}
          ],
          transmissions: [
            {name:'manual'},{name:'automatic'}
          ],
          classes: [
            {name:'old-timer'},{name:'sport'},{name:'economic'}
          ],
          firstLastName: '',
          firmName: '',
          email: '',
          password: '',
          address:'',
          showusers: false,
          showbrands: false,
          showmodels: false,
          showtransmissions: false,
          showfuels: false,
          showclasses: false,
          registeruser: false,
          registerfirm: false,
       }
    },

    methods:{
      removeUser(name){
        console.log("request for removing user : " , name)
      },
      removeBrand(name){
        console.log("request for removing brand : " , name)
      },
      removeModel(name){
        console.log("request for removing model : " , name)
      },
      removeFuel(name){
        console.log("request for removing fuel : " , name)
      },
      removeTransmission(name){
        console.log("request for removing transmission: ", name)
      },
      removeClass(name){
        console.log("request for removing class: ", name)
      },
      register(){
        if(this.registeruser){
            console.log("U have entered user " +" " + this.firstLastName+" " + this.email + " with password: " + this.password )
             this.users.splice(this.users.length+1, 0 ,{blocked:false, type:'user',name:{first:this.email}})
             this.registeruser = !this.registeruser
        }
        if(this.registerfirm){
             console.log("U have entered firm " +" " + this.firmName+" " + this.email + " with password: " + this.password )
             this.users.splice(this.users.length+1, 0 ,{blocked:false, type:'user',name:{first:this.email}})
             this.registerfirm = !this.registerfirm
        }
        
      }
    },
    created(){

    }
}
</script>