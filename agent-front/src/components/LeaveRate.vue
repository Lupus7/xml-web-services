<template>
    <div class="container" style="width:90%">

        <br>

        <table class="table table-hover" style="background:#efefef; border: 3px solid #4c4c4c">
        <thead>
            <tr>
                <th colspan="2" style="color:#4c4c4c">
                    <h4>Leave Rate</h4>
                </th>
            </tr>
        </thead>
        </table>

        <div class="row">

            <div class="col-5">

                    <b-card style="width:90%">
                        <b-carousel
                            controls
                            :interval="0"
                            indicators
                            background="#ababab"
                            img-width="580"
                            img-height="480"
                            style="text-shadow: 1px 1px 2px #333;"
                        
                        >
                            <template>
                                <div v-for="image in this.booking.images" :key="image.id">
                                    <b-carousel-slide
                                        img-width="580"
                                        img-height="480"
                                        v-bind:img-src="image"
                                    ></b-carousel-slide>
                                </div>
                            </template>
                        </b-carousel>

                        <hr />
                        <b-card-sub-title class="mb-3">
                            <b-icon icon="person" aria-hidden="true" variant="dark"></b-icon>
                            {{booking.advertiser}}
                        </b-card-sub-title>

                        <b-card-sub-title class="mb-3">
                            <b-icon icon="geo-alt" aria-hidden="true" variant="dark"></b-icon>
                            {{booking.place}}
                        </b-card-sub-title>
                        

                        <hr />

                        <b-card-sub-title class="mb-2" style="color: black">Brand: {{booking.brand}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Model: {{booking.model}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Class: {{booking.carClass}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Transmission: {{booking.transmission}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Fuel Type: {{booking.fuel}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Children Seats: {{booking.childrenSeats}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Total Milleage: {{booking.totalMileage}}</b-card-sub-title>
                        <b-card-sub-title class="mb-2">Allowed Mileage: {{booking.allowedMileage}}</b-card-sub-title>
                    </b-card>
            </div>
            <div class="col-7">

                  <b-card style="width:90%">

                        <div class="modal-header" style="margin-top:-20px;margin-left:20px;margin-right:20px">
                            <h5 class="modal-title">Leave Rate</h5>
                         </div>

                        <b-card-body>

                        <div class="form-group">
                            <label>Rating:</label>
                            <b-form-rating variant="danger" v-model="rate" stars="10" class="form-control" ></b-form-rating>

                        </div>

                         <div class="form-group">
                            <label>Comment:</label>
                            <b-form-textarea class="form-control" 
                                v-model="comment"
                                rows="6"
                                no-resize
                            ></b-form-textarea>
                        </div>


                        <div class="modal-footer" style="margin-top:20px">
                            <button @click="leaveRate()" type="button" class="btn btn-success" >
                                <b-icon icon="check-circle" aria-hidden="true"></b-icon> Confirm
                            </button>
                        </div>

                      
                        </b-card-body>
                        

                       
                    </b-card>

            </div>

        </div>


    </div>
    
</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
            rate:"",
            comment:"",
        }
    },

    props:{
        booking:Object
    },

    methods:{
        leaveRate(){
            event.preventDefault();
            axios
                .post("/community/rate", {
                    booking:this.booking.id,
                    rate: this.rate,
                    comment:this.comment,
                    carId:this.booking.carId
                  
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast("Rate Delivered!", {
                            title: "Rate",
                            variant: "success",
                            solid: true
                        });
                        
                        this.$router.push("/myprofile");
                    
                    }else {
                        this.$bvToast.toast("Already Rated!", {
                            title: "Rate",
                            variant: "warning",
                            solid: true
                        });
                    }
                });

        }
    },

    created(){
    }
}
</script>

<style scoped>

</style>