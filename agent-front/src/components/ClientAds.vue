  
<template>
    <div class="container" style=" width:90%">
        <br />

         <table class="table table-hover" style="background:#efefef; border: 3px solid #4c4c4c">
            <thead>
                <tr>
                <th colspan="2" style="color:#4c4c4c">
                    <h4>My Ads</h4>
                </th>
                <th style="width:30%" colspan="1">
                    <center>
                    <b-button
                        class="btn btn-danger"
                        href="#"
                        data-toggle="modal"
                        data-target="#newad"
                    >
                        <b-icon icon="plus-circle" aria-hidden="true" /> New Ad
                    </b-button>
                    </center>
                </th>
                </tr>
            </thead>
        </table>

        <b-card v-show="this.ads.length === 0" class="text-center" style="background:#DCDCDC">
            <div>
                <h4 style="color:#696969">You have no ads!</h4>
            </div>
        </b-card>

        <!-- SHOWING ADS -->
        <b-card-group deck class="row">
            <div v-for="ad in this.ads" :key="ad.id" class="col-12 col-md-3 col-lg-4">
                <b-card style="width:90%">
                    <b-carousel
                        id="carousel-1"
                        controls
                        :interval="0"
                        indicators
                        background="#ababab"
                        img-width="580"
                        img-height="480"
                        style="text-shadow: 1px 1px 2px #333;"
                        
                    >
                        <template>
                            <span v-if="ad.images && ad.images.length > 0">
                                <div v-for="image in ad.images" :key="image.id">
                                    <b-carousel-slide
                                        img-width="580"
                                        img-height="480"
                                        v-bind:img-src="image"
                                    ></b-carousel-slide>
                                </div>
                            </span>
                            <b-carousel-slide
                                v-else
                                caption="No images"
                                img-blank
                                img-alt="No images"
                                img-width="580"
                                img-height="480"
                            >
                            </b-carousel-slide>
                        </template>
                    </b-carousel>
                     
                    <hr />
                    <b-card-sub-title class="mb-3">
                        <b-icon icon="geo-alt" aria-hidden="true" variant="dark"></b-icon>
                        {{ad.place}}
                    </b-card-sub-title>

                    <hr />

                    <b-card-sub-title class="mb-2" style="color: black">Brand: {{ad.brand}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Model: {{ad.model}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Class: {{ad.carClass}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Transmission: {{ad.transmission}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Fuel Type: {{ad.fuel}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Children Seats: {{ad.childrenSeats}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Total Milleage: {{ad.totalMileage}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Allowed Mileage: {{ad.allowedMileage}}</b-card-sub-title>

                    <hr />
                    <b-card-sub-title class="mb-2">
                        <b-icon icon="calendar" aria-hidden="true" variant="dark" />
                        Start Date: {{ad.startDate.split("T")[0]}}
                    </b-card-sub-title>
                    <b-card-sub-title class="mb-2">
                        <b-icon icon="calendar-fill" aria-hidden="true" variant="dark" />
                        End Date: {{ad.endDate.split("T")[0]}}
                    </b-card-sub-title>

                    <div class="modal-footer">
                        <div class="row">
                            <div class="col">
                                <b-button
                                    @click="deactivateAd(ad)"
                                    v-if="ad.active"
                                    type="button"
                                    class="btn btn-dark"
                                    style="width:120%"
                                >
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Deactive
                                </b-button>
                                <b-button
                                    @click="activateAd(ad)"
                                    v-if="!ad.active"
                                    type="button"
                                    class="btn btn-dark"
                                    style="width:120%"
                                >
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Active
                                </b-button>
                            </div>
                            <div class="col">
                                <b-button
                                    data-toggle="modal"
                                    data-target="#editad"
                                    @click="editAd(ad)"
                                    type="button"
                                    class="btn btn-secondary"
                                    style="width:120%"
                                >
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Edit
                                </b-button>
                            </div>
                        </div>
                    </div>
                </b-card>

                <br />
            </div>
        </b-card-group>



        <!-- EDIT FORM -->

           <div class="modal fade" id="editad" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Ad</h5>
                        <button type="button" class="close" @click="resetEditForm()" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Place</label>
                                    <b-input v-model="placeEditF" type="text" class="form-control" />
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Start Date</label>
                                        <b-form-datepicker
                                            v-model="startDateEditF"
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>End Date</label>
                                        <b-form-datepicker
                                            v-model="endDateEditF"
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                </div>

                           
                               
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button @click="editAdFinal()" type="button" class="btn btn-success" data-dismiss="modal">
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Edit Ad
                        </button>
                        <button
                            @click="resetEditForm()"
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>


    <!-- NEW AD FORM -->
        <div class="modal fade" id="newad" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Ad</h5>
                        <button type="button" class="close" @click="resetForm()" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Place</label>
                                    <b-input v-model="placeF" type="text" class="form-control" />
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Start Date</label>
                                        <b-form-datepicker
                                            v-model="startDateF"
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>End Date</label>
                                        <b-form-datepicker
                                            v-model="endDateF"
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-12">
                                        <label>Select Car</label>
                                        <b-form-select
                                            v-model="carF"
                                            :options="cars"
                                            class="form-control"
                                        />
                                    </div>
                                  
                                </div>

                               
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button @click="createNewAd()" type="button" class="btn btn-success" data-dismiss="modal">
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Create Ad
                        </button>
                        <button
                            @click="resetForm()"
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            // showing ads
            ads: [],
            // edit
            startDateEditF:"",
            endDateEditF:"",
            placeEditF:"",
            editID:"",
            // create
            cars: [],
            carF:"",
            startDateF:"",
            endDateF:"",
            placeF:""
        };
    },
   
    methods: {

        // GETTERS
        getClientAds() {
            axios.get("/api/ad/client").then(response => {
                this.ads = response.data;
            });
        },
        getClientCars() {
            axios.get("/cars/client").then(response => {
                // pokupi sve clientove aute
                this.cars = []
                for(let resp of response.data){
                    var select = {value:resp.carId,text:resp.brand+" "+resp.model+" "+resp.carClass}
                    this.cars.push(select);
                }
            });
        },

        // ACTIVATE & DEACTIVATE
        deactivateAd(ad) {
            event.preventDefault();
            axios
                .delete("/api/ad/deactivate/" + ad.adId)
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Ad Deactivation",
                            variant: "success",
                            solid: true
                        });
                        this.getClientAds();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Ad Deactivation",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        activateAd(ad) {
            event.preventDefault();
            axios.put("/api/ad/activate/" + ad.adId).then(response => {
                if (response.status === 200) {
                    this.$bvToast.toast(response.data, {
                        title: "Ad Activation",
                        variant: "success",
                        solid: true
                    });
                    this.getClientAds();
                } else if (response.status === 402) {
                    this.$bvToast.toast(response.data, {
                        title: "Ad Activation",
                        variant: "danger",
                        solid: true
                    });
                } else {
                    this.$bvToast.toast(response.data, {
                        title: "Ad Activation",
                        variant: "warning",
                        solid: true
                    });
                }
            });
        },

        // EDIT

        editAd(ad) {
            this.getClientCars();
            this.placeEditF = ad.place;
            this.startDateEditF = ad.startDate;
            this.endDateEditF = ad.endDate;
            this.editID = ad.adId;
          
        },

        resetEditForm() {
            this.startDateEditF = "";
            this.endDateEditF = "";
            this.placeEditF = "";
            this.editID = "";

        },
        editAdFinal(){
            event.preventDefault();
            console.log(this.startDateEditF)
            if(this.startDateEditF.includes('T'))
                this.startDateEditF = this.startDateEditF.replace('T'," ");
            else
                this.startDateEditF += " 00:00:00";
            

            if(this.endDateEditF.includes('T'))
                this.endDateEditF = this.endDateEditF.replace('T'," ");
            else
                this.endDateEditF += " 00:00:00";

            axios
                .put("/api/ad/"+this.editID, {
                   startDate:this.startDateEditF,
                   endDate:this.endDateEditF,
                   place:this.placeEditF,
                })
                .then(response => {
                    this.resetEditForm();
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Edit Ad",
                            variant: "success",
                            solid: true
                        });
                        this.getClientAds();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Edit Ad",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },

        // CREATE ADD

        resetForm() {
            this.carF = "";
            this.startDateF = "";
            this.endDateF = "";
            this.placeF = "";

        },
        createNewAd(){
            event.preventDefault();
            axios
                .post("/api/ad", {
                   startDate:this.startDateF+ " 00:00:00",
                   endDate:this.endDateF+ " 00:00:00",
                   place:this.placeF,
                   carId:this.carF
                })
                .then(response => {
                    this.resetForm();
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "New Ad",
                            variant: "success",
                            solid: true
                        });
                        this.getClientAds();
                    } else if(response.status === 402) {
                        this.$bvToast.toast(response.data, {
                            title: "New Car",
                            variant: "danger",
                            solid: true
                        });
                    }else {
                        this.$bvToast.toast(response.data, {
                            title: "New Car",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        }
    },
    created() {
        this.getClientAds();
        this.getClientCars();
    }
};
</script>

<style scoped>
.modal-footer {
    border-top: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}
.modal-header {
    border-bottom: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}
</style>

