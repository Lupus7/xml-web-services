<template>
    <div class="container" style="width:90%">

        <br>

        <table class="table table-hover table-bordered" style="background:#efefef; border:none">
            <thead>
                <tr>
                    <th colspan="10" style="color:#4c4c4c" class="text-center">
                        <h4>My Rates</h4>
                    </th>
                </tr>

            </thead>
        
            <tbody v-for="rate in this.carRates" :key="rate.id">

                <tr>

                    <td colspan="4" >
                        <b-card >
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
                                    <div v-for="image in rate.images" :key="image.id">
                                        <b-carousel-slide
                                            img-width="580"
                                            img-height="480"
                                            v-bind:img-src="image"
                                        ></b-carousel-slide>
                                    </div>
                                </template>
                            </b-carousel>

                            <hr />

                            <b-card-sub-title class="mb-2" style="color: black">Brand: {{rate.brand}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Model: {{rate.model}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Class: {{rate.carClass}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Transmission: {{rate.transmission}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Fuel Type: {{rate.fuel}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Children Seats: {{rate.childrenSeats}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Total Milleage: {{rate.totalMileage}}</b-card-sub-title>
                            <b-card-sub-title class="mb-2">Allowed Mileage: {{rate.allowedMileage}}</b-card-sub-title>
                        </b-card>
                    </td>

                    <td colspan="6" >
                        
                    <b-card>

                            <b-card-header class="text-left" >
                                    
                                <b-avatar class="mr-3"></b-avatar>
                                <span class="mr-auto">Rated by: {{rate.user}}</span>


                            </b-card-header>

                            <b-card-body>

                            <div class="form-group">
                                <label>Rating:</label>
                                <b-form-rating variant="danger" readonly v-model= rate.rate stars="10" class="form-control" ></b-form-rating>

                            </div>

                            <div class="form-group">
                                <label>Comment:</label>
                                <b-form-textarea class="form-control" v-if="rate.approved"
                                    rows="6"
                                    readonly
                                    no-resize
                                    v-model="rate.comment"
                                    
                                > </b-form-textarea>
                                <b-form-textarea class="form-control" v-else
                                    rows="6"
                                    disabled
                                    no-resize
                                    v-model="comMessage"
                                    
                                > </b-form-textarea>
                            </div>

                        
                            </b-card-body>
                            
                        
                        </b-card>

                    </td>

                    
                </tr>

            </tbody>

    </table>


    </div>
    
</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
            carRates:[],
            comMessage: "Comment wasnt approved!",
        }
    },
    methods:{

        showComment(rate){
            if(rate.approved)
                return rate.comment;
            else
                return "Comment wasnt approved!"
        }

    },
    created(){

        axios.get("/community/rate").then(response => {
            this.carRates = response.data;
        });

    }
}
</script>

<style scoped>

</style>