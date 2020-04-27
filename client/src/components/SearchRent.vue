<template>
  <div>
    <b-navbar type="light" variant="light">     
      <b-navbar-nav>
          <b-nav-item href="#" data-toggle="modal" data-target="#search" >   
            <b-icon icon="search" aria-hidden="true" variant="dark" ></b-icon> Search
          </b-nav-item>    
      </b-navbar-nav>     
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

   <div>
    <b-table striped hover :fields="fields" :items="adds" v-if="this.adds.length > 0" >
           <template v-slot:cell(images)= "{value}">
            <img style="width:120px;height:120px" thumbnail fluid :src = value[0] />
          </template>
          
          <template v-slot:cell(moredetails)>
            <b-button variant="success"> More Detailed </b-button>
          </template>
    </b-table>
  </div>


  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SearchRent',
  data(){
    return{
      // adds ce da se popuni posle search,onda podesi key u fields i items zameni sa adds
      fields: [
          { key: 'Images', label:'Image', sortable: false},{ key: 'Brand', label:'Car Brand', sortable: false},{ key: 'Model',label:"Car Model", sortable: false},
          { key: 'Place', label:"Place", sortable:false}, { key: 'StartDate', label:"Start", sortable:false},{ key: 'EndDate', label:"End", sortable:false},{ key: 'Fuel', label:'Fuel Type', sortable: false},{ key: 'Transmission', label: 'Transmission Type', sortable: false},
          { key: 'Class',label:"Car Class", sortable: false},{ key:"TotalMileage",label: 'Traveled Km', sortable: true},{key:"AllowedMileage", label: 'Predicted Km', sortable: true},
          { key:'Price',label: 'Price[$]', sortable: true},{ key:'SeatsNumber',label: 'Children Seats', sortable: false},{key:'Rating', label: 'Rate', sortable: true}, {key:'moredetails',label:"More Details"}         
         
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
        brands:[], carClasses:[],transmissions:[],fuels:[],models:[],modelBool:true,brandsResponse:[]
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
      axios.post('/api/ads/', {
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
                       this.adds = response.data;
                   
                    });
    },
    getCarSpec(){

      axios.get('/api/brands').then(response => { 
         // stavi brandove u brands, a mozda samo stringove ubaci     
        this.brandsResponse = response.data;
        this.brands = []
        this.brands.push("");

        for(let b of this.brandsResponse)
          this.brands.push(b.Name);
        
      });

      axios.get('/api/classes').then(response => { 
        this.carClasses = []
        this.carClasses.push("")
        for(let cl of response.data)
          this.carClasses.push(cl)
      });

      axios.get('/api/fuels').then(response => { 
        this.fuels = []
        this.fuels.push("");
        for(let fl of response.data)
          this.fuels.push(fl);
      });

      axios.get('/api/transmissions').then(response => { 
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

    }
  },
  created(){
    this.getCarSpec();
  }
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
