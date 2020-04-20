<template>
  <div >
    <nav class="navbar navbar-dark bg-dark">     
      <ul class="nav">
        <li class="nav-item">
          <a class="nav-link active" type="button" href="#" data-toggle="modal" data-target="#search" >Search</a>
        </li>
       
      </ul>      
    </nav>

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
                <b-input v-model="brandF" type="text" class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Car Model</label>
                <b-input v-model="modelF" type="text" class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Fuel Type</label>
                <b-input v-model="fuelF" type="text" class="form-control"/>
              </div>
              <div class="form-group col-md-6">
                <label>Gear Box Type</label>
                <b-input v-model="transmissionF" type="text" class="form-control"/>
              </div>
            </div>

             <div class="form-row">
              <div class="form-group col-md-6">
                <label>Car Class</label>
                <b-input  v-model="classF" type="text" class="form-control"/>
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
          <button type="button" class="btn btn-primary" data-toggle="collapse" href="#advanced" role="button">Advanced Search</button>
          <button type="button" class="btn btn-success" @click="search()" data-dismiss="modal" >Confirm</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <!-- Oglasi --> 

   <div>
    <b-table striped hover :fields="fields" :items="adds" v-if="this.adds.length > 0" >
           <template v-slot:cell(image)= "{value}">
            <img style="width:120px;height:120px" thumbnail fluid :src = value />
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
          { key: 'Place', label:"Place", sortable:false},{ key: 'Fuel', label:'Fuel Type', sortable: false},{ key: 'Transmission', label: 'Transmission Type', sortable: false},
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
    }
  },
  methods:{
    
    search(){
      // parsirati datume, checkbox true i false
      console.log(this.collision_damageF)
      let collision_damage_bool = this.collision_damageF == true
      console.log("COL damage : " + collision_damage_bool)

      let takeoverString = ''
      if (this.take_overF != ''){
           let takeover_date = new Date(this.take_overF) 
           takeoverString = takeover_date.toLocaleString()
      }
     
      let returnString = ''
      if(this.returnF != ''){
          let returnDate = new Date(this.returnF)
          returnString = returnDate.toLocaleString()
      }
      event.preventDefault();
      axios.post('/api/cars/', {
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
    }
  },
  created(){

  }
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
