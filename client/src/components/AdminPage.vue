<template>
    <div>
      <b-navbar type="light" variant="light">
          <b-navbar-nav>
            <b-nav-item v-on:click="showusers = true" href="#" ><b-icon icon="people-fill" variant="dark" aria-hidden="true"/> Users</b-nav-item>
            <b-nav-item-dropdown  text="Cars">
              <b-dropdown-item v-on:click="showbrands=true">Brands</b-dropdown-item>
              <b-dropdown-item v-on:click="showmodels=true">Models</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item v-on:click="showfuels=true"> Fuels</b-dropdown-item>
              <b-dropdown-item v-on:click="showtransmissions=true">Transmissions</b-dropdown-item>
              <b-dropdown-item v-on:click="showclasses=true">Classes</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item-dropdown text="Register" data-toggle="modal" data-target="#register" >
              <b-dropdown-item v-on:click="registeruser=true"> <b-icon icon="person-fill" aria-hidden="true"/> User</b-dropdown-item>
              <b-dropdown-item v-on:click="registerfirm=true">  <b-icon icon="building" aria-hidden="true"/> Firm</b-dropdown-item>
            </b-nav-item-dropdown>
            
            <b-nav-item v-on:click="showcomments = true"> <b-icon icon="chat-dots-fill" aria-hidden="true" variant="dark" /> Comments</b-nav-item>
          </b-navbar-nav>
      </b-navbar >
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


         <table class="table table-bordered" id="car-brand" v-if="showbrands && this.brands.length>0" @mouseleave="showbrands=false">
          <thead>
            <tr>
              <th scope="col">Name of the brand</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="brand in this.brands" :key="brand.id" >
              <th scope="row">{{brand}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeBrand(brand)">Remove brand</button></td>
            </tr>
            <tr>
              <td><button type="button" class="btn btn-primary" @click="addNewBrand=true">Add new brand</button></td>
              <td v-if="addNewBrand">
                <form> 
                  <b-input type="text" v-model="newBrand"  class="form-control"/><button type="button" class="btn btn-success" @click="AddNewBrand()">Submit</button>
                </form>
              </td>
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
              <th scope="row">{{model}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeModel(model)">Remove model</button></td>
            </tr>
            <tr>
              <td><button type="button" class="btn btn-primary" @click="addNewModel=true">Add new model</button></td>
              <td v-if="addNewModel">
                <form> 
                  <b-input type="text" v-model="newModel"  class="form-control"/><button type="button" class="btn btn-success" @click="AddNewModel()">Submit</button>
                </form>
              </td>
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
              <th scope="row">{{fuel}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeFuel(fuel)">Remove fuel type</button></td>
            </tr>
            <tr>
              <td><button type="button" class="btn btn-primary" @click="addNewFuel=true">Add new fuel</button></td>
              <td v-if="addNewFuel">
                <form> 
                  <b-input type="text" v-model="newFuel"  class="form-control"/><button type="button" class="btn btn-success" @click="AddNewFuel()">Submit</button>
                </form>
              </td>
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
              <th scope="row">{{tr}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeTransmission(tr)">Remove transmission type</button></td>
            </tr>
            <tr>
              <td><button type="button" class="btn btn-primary" @click="addNewTrans=true">Add new transmission type</button></td>
              <td v-if="addNewTrans">
                <form> 
                  <b-input type="text" v-model="newTransms"  class="form-control"/><button type="button" class="btn btn-success" @click="AddNewTransms()">Submit</button>
                </form>
              </td>
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
            <tr v-for="cl in this.carClasses" :key="cl.id">
              <th scope="row">{{cl}}</th>
              <td><button type="button" class="btn btn-danger" v-on:click="removeClass(cl)">Remove car type</button></td>
            </tr>
            <tr>
              <td><button type="button" class="btn btn-primary" @click="addNewClass=true">Add new car class</button></td>
              <td v-if="addNewClass">
                <form> 
                  <b-input type="text" v-model="newClass"  class="form-control"/><button type="button" class="btn btn-success" @click="AddNewClass()">Submit</button>
                </form>
              </td>
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
                <button type="button" class="btn btn-danger" data-dismiss="modal" @click="resetValues()"> <b-icon icon="x-circle"> </b-icon>  Close</button>
              </div>
            </div>
          </div>
        </div>

        <br>
        <div class="row">
          <div v-for="comment in this.comments" :key="comment.id"  class="column">
            <div class="card" style="width: 18rem;">
              <img class="card-img-top" src="../assets/comment.jpg" alt="Card image cap">
              <div class="card-body">
                <h5 class="card-title">{{comment.recezent}} for {{comment.Oglas}}</h5>
                <p class="card-text">{{comment.Komentar}}</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
              </div>
            </div>
          </div>
        </div>


    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'AdminPage',
    data(){
      return{
          users: [
          { type: 'user', blocked: true, age: 40, name: { first: 'Dickerson', last: 'Macdonald' } },
          { type:'firm', blocked: false, age: 21, name: { first: 'Larsen', last: 'Shaw' } }
          ],
          comments:[
            {recezent: 'Marko', Oglas: 'Auto', Komentar: 'Auto je krsina'},
            {recezent: 'Jovan', Oglas: 'Traktor', Komentar: 'Ide, trazi cetvrtu'}
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
          registeruser: false, showcomments: false,
          registerfirm: false,
          addNewBrand: false, addNewModel:false, addNewFuel: false, addNewTrans: false, addNewClass: false,
          newBrand: '', newModel:'', newFuel: '', newTransms: '',newClass: '',
          brands:[], carClasses:[],transmissions:[],fuels:[],models:[],modelBool:true,brandsResponse:[]

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
        
      },
      resetValues(){
        this.registeruser = false 
        this.registerfirm = false
      },

      getCarSpec(){
        axios.get('/api/brands').then(response => { 
         // stavi brandove u brands, a mozda samo stringove ubaci     
        this.brandsResponse = response.data;
        this.brands = []

        for(let b of this.brandsResponse){
          this.brands.push(b.Name);
          for(let m of b.Models){
            this.models.push(m)
          }
        }
      });
      
      axios.get('/api/classes').then(response => { 
        this.carClasses = []
        for(let cl of response.data)
          this.carClasses.push(cl)
      });

      axios.get('/api/fuels').then(response => { 
        this.fuels = []
        for(let fl of response.data)
          this.fuels.push(fl);
      });

      axios.get('/api/transmissions').then(response => { 
        this.transmissions = []
        for(let tr of response.data)
          this.transmissions.push(tr);
      });
      },

      AddNewModel(){
        console.log(this.newModel)
        if (this.newModel != ''){
            this.models.push(this.newModel)
            this.addNewModel = false
        }
        this.addNewModel = false
      },
        AddNewBrand(){
        console.log(this.newBrand)
        if (this.newBrand != ''){
            this.brands.push(this.newBrand)
            this.addNewBrand = false
        }
        this.addNewBrand = false
      },
       AddNewFuel(){
        console.log(this.newFuel)
        if (this.newFuel != ''){
            this.fuels.push(this.newFuel)
            this.addNewFuel = false
        }
        this.addNewFuel = false
      },
      AddNewTransms(){
        console.log(this.newTransms)
        if (this.newTransms != ''){
            this.transmissions.push(this.newTransms)
            this.addNewTrans = false
        }
        this.addNewTrans = false
      },
      AddNewClass(){
        console.log(this.newClass)
        if (this.newClass != ''){
            this.carClasses.push(this.newClass)
            this.addNewClass = false
        }
        this.addNewClass = false
      }




    },
    created(){
      this.getCarSpec();
    }
}
</script>
<style scoped>
.column {
  float: left;
  width: 25%;
  padding: 0 10px;
}
.row {margin: 0 -5px;}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Style the counter cards */
.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); /* this adds the "card" effect */
  padding: 16px;
  text-align: center;
  background-color: #f1f1f1;
}
</style>