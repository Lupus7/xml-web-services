<template>
  <div>
    <b-navbar type="light" variant="light">     
      <div class="container">
        <b-navbar-nav style="margin-left:0.9rem" >
            <b-nav-item href="#" data-toggle="modal" data-target="#search" >   
              <b-icon icon="search" aria-hidden="true" variant="dark" ></b-icon> Search
            </b-nav-item>    
        </b-navbar-nav>  
      </div>   
    </b-navbar> 

  <div class="modal fade" id="search" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Search</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
           <b-form>
             <div class="form-group">
              <label>Place</label>
              <b-input v-model="placeF" type="text" class="form-control"/>
            </div>

            <div class="form-row">
              <div class="form-group col-md-6">
                <label>Start Date</label>
                <b-form-datepicker v-model="take_overF" :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }" locale="en"></b-form-datepicker>
              </div>
              <div class="form-group col-md-6">
                <label>End Date</label>
                <b-form-datepicker v-model="returnF"  :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }" locale="en"></b-form-datepicker>
              </div>
            </div>

            <div class="collapse" id="advanced">
             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Car Brand</label>
                 <b-form-select v-model="brandF" :options="brands" @change="fillModels()" class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Car Model</label>
                 <b-form-select v-model="modelF" :options="models" :disabled="modelBool" class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Fuel Type</label>
                 <b-form-select v-model="fuelF" :options="fuels" class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Transmission Type</label>
                 <b-form-select v-model="transmissionF" :options="transmissions" class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Car Class</label>
                 <b-form-select  v-model="classF" :options="carClasses" class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Traveled Km</label>
                <b-input v-model="total_mileageF" type="number" min=0 class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Min Price</label>
                <b-input v-model="price_minF" type="number" min=0 class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Max Price</label>
                <b-input v-model="price_maxF" type="number" min=0 class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Predicted Km</label>
                <b-input v-model="planned_mileageF" type="number" min=0 class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Children Seats</label>
                <b-input v-model="seats_numberF" type="number" min=0 class="form-control"/>
              </div>
              
            </div>

            <div class="form-row">
              <div class="form-group col-md-1">
                <b-form-checkbox  v-model="collision_damageF" style="width:20px; height:25px" type="checkbox"/> 
              </div>
              <div class="form-group col-md-11">
                Collision Damage Waiver Protection 
              </div>
            </div>
           
            </div>
           
          </b-form>
        </div>
      
        <div class="modal-footer">
          <button @click="changeSearch()" type="button" class="btn btn-primary" data-toggle="collapse" href="#advanced" role="button" v-text="searchName">  </button>
          <button type="button" class="btn btn-success" @click="search()" data-dismiss="modal" > <b-icon icon="check-circle" aria-hidden="true"> </b-icon> Confirm</button>
          <button type="button" class="btn btn-danger" data-dismiss="modal"> <b-icon icon="x-circle"> </b-icon>  Close</button>
        </div>
      </div>
    </div>
  </div>
  <!-- Oglasi --> 
    <br>
   <div class="container">
    <b-table hover :fields="fields" :items="adds" :dark='true'  v-if="this.adds.length > 0" >
           <template v-slot:cell(images)= "{value}">
            <img style="width:250px;height:330px" thumbnail fluid :src = value[0] />
          </template>

          <template v-slot:cell(card)= "{value}">
                <b-card-group deck class="row" >
              
                    <b-card style="width:300px;height:330px;border:none;">

                        <b-card-title class="mb-1" style="color: black"> {{value.Brand}} </b-card-title>
                        
                        <hr>
                        <b-card-sub-title class="mb-3"> <b-icon icon="building" aria-hidden="true" variant="dark" ></b-icon> {{value.Advertiser}} </b-card-sub-title>
                        <b-card-sub-title class="mb-3"> <b-icon icon="geo-alt" aria-hidden="true" variant="dark" ></b-icon> {{value.Place}} </b-card-sub-title>
                        <hr>

                        <b-card-sub-title class="mb-2">Model: {{value.Model}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Class: {{value.Class}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Transmission: {{value.Transmission}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Fuel Type: {{value.Fuel}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Children Seats: {{value.SeatsNumber}}</b-card-sub-title>

                        <hr>

                        <b-card-sub-title class="mb-2"><b-icon icon="calendar" aria-hidden="true" variant="dark" /> Start Date: {{value.StartDate}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2"><b-icon icon="calendar-fill" aria-hidden="true" variant="dark" /> End Date: {{value.EndDate}}</b-card-sub-title>

                    </b-card>

            </b-card-group>
          </template>
          
          <template v-slot:cell(moredetails)="value">
            <b-button style="background:#b20000; width:150px" @click="goToAd(value.item.Id)"> Book Car </b-button>
          </template>
          <br>
    </b-table>
  </div>


  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'

export default {
  name: 'SearchRent',
  data(){
    return{
      // adds ce da se popuni posle search,onda podesi key u fields i items zameni sa adds
      fields: [
          { key: 'Images', label:'Image', sortable: false},{ key: 'Card', label:'Car Info', sortable: false}, { key:"TotalMileage",label: 'Traveled Km', sortable: true},{key:"AllowedMileage", label: 'Predicted Km', sortable: true},
          { key:'Price',label: 'Price[€]', sortable: true},{key:'Rating', label: 'Rate', sortable: true}, {key:'moredetails',label:""}         
         
        ],
        adds: [],
        placeF:"",
        take_overF:"",
        returnF:"",
        brandF:"",
        modelF:"",
        fuelF:"",
        transmissionF:"",
        classF:"",
        price_minF:0,
        price_maxF:0,
        total_mileageF:0,
        planned_mileageF:0,
        collision_damageF:false,
        seats_numberF:0,
        searchName: "Advanced Search",
        brands:[], carClasses:[],transmissions:[],fuels:[],models:[],modelBool:true,brandsResponse:[],
        email: "",
    }
  },
  methods:{

    changeSearch(){
      if(this.searchName === "Basic Search")
          this.searchName = "Advanced Search"
      else if(this.searchName === "Advanced Search" )
          this.searchName = "Basic Search"

    },
    
    search(){
      // parsirati datume, checkbox true i false
      console.log(this.collision_damageF)
      let collision_damage_bool = this.collision_damageF == true
      console.log("COL damage : " + collision_damage_bool)

      let takeoverString = ''
      if (this.take_overF != ''){
           let takeover_date = new Date(this.take_overF) 
           takeoverString = ISODateString(takeover_date)
      }
     
      let returnString = ''
      if(this.returnF != ''){
          let returnDate = new Date(this.returnF)
          returnString = ISODateString(returnDate)
      }

      function ISODateString(d){
        function pad(n){return n<10 ? '0'+n : n}
        return d.getUTCFullYear()+'-'
          + pad(d.getUTCMonth()+1)+'-'
          + pad(d.getUTCDate())+'T'
          + pad(d.getUTCHours())+':'
          + pad(d.getUTCMinutes())+':'
          + pad(d.getUTCSeconds())+'Z'
          }

      event.preventDefault();
      axios.post('/cars/api/ads/', {
                       "place":this.placeF,
                       "take_over": takeoverString,
                       "return":returnString,
                       "brand":this.brandF,
                       "model":this.modelF,
                       "fuel":this.fuelF,
                       "transmission":this.transmissionF,
                       "class":this.classF,
                       "price_min":parseFloat(this.price_minF),
                       "price_max":parseFloat(this.price_maxF),
                       "total_mileage":parseFloat(this.total_mileageF),
                       "planned_mileage":parseFloat(this.planned_mileageF),
                       "collision_damage":collision_damage_bool,
                       "seats_number":parseInt(this.seats_numberF),
                      
                      
                    }).then(response=>{
                       //preuzmes rezultate u listu xD
                       this.adds = []
                       for(let res of response.data){
                         if(res.Advertiser !== this.email){
                          let card = {
                              Place : res.Place,         
                              StartDate : res.StartDate,       
                              EndDate : res.EndDate,
                              Brand : res.Brand,
                              Model : res.Model,
                              Fuel : res.Fuel,
                              Transmission : res.Transmission,
                              Class : res.Class,
                              Advertiser: res.Advertiser,
                              Description: res.Description,
                              SeatsNumber : res.SeatsNumber,

                          }
                          let carId;
                          axios.get("/cars-ads/ad/" + res.Id + "/car").then(response => {
                            carId = response.data
                            if (carId)
                              axios.get("/cars-ads/test/pricelist/" + 1 + "/car/" + carId).then(response => {
                                let add = {
                                    Id: res.Id,
                                    Images: res.Images,
                                    Card: card,
                                    TotalMileage : parseFloat(res.TotalMileage).toFixed(2),
                                    AllowedMileage : res.AllowedMileage,
                                    Price : response.data,
                                    Rating : parseFloat(res.Rating).toFixed(2),
                                };

                                if(res.AllowedMileage !== "UNLIMITED"){
                                  add.AllowedMileage =  parseFloat(res.AllowedMileage).toFixed(2);
                                }
                        
                                this.adds.push(add);
                              })
                          })

                        }
                       }
                     
                   
                    });
    },
    getCarSpec(){

      axios.get('/cars/api/brands').then(response => { 
         // stavi brandove u brands, a mozda samo stringove ubaci     
        this.brandsResponse = response.data;
        this.brands = []
        this.brands.push("");

        for(let b of this.brandsResponse)
          this.brands.push(b.Name);
        
      });

      axios.get('/cars/api/classes').then(response => { 
        this.carClasses = []
        this.carClasses.push("")
        for(let cl of response.data)
          this.carClasses.push(cl)
      });

      axios.get('/cars/api/fuels').then(response => { 
        this.fuels = []
        this.fuels.push("");
        for(let fl of response.data)
          this.fuels.push(fl);
      });

      axios.get('/cars/api/transmissions').then(response => { 
        this.transmissions = []
        this.transmissions.push("");
        for(let tr of response.data)
          this.transmissions.push(tr);
      });

    },

    fillModels(){
      if(this.brandF === ""){
        this.modelBool = true;
        this.modelF = "";
        return;
      }
     
      let brandChosen = null;
      for(let brand of this.brandsResponse){
        if(this.brandF === brand.Name){
          brandChosen = brand;
          break;
        }
      }
      this.models = []
      this.models.push("");
      for(let m of brandChosen.Models)
        this.models.push(m);
      this.modelBool = false;

    },
    goToAd(id) {
      console.log(id)
        this.$router.push({
            name: "Ad",
            params: { id: id },
        });
    },

    getEmail(){
        const token = localStorage.getItem("accessToken");
        this.email = jwt_decode(token).sub;

    }
  },
  created(){
    this.getCarSpec();
    this.getEmail();
  }
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


</style>
