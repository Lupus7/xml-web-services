  
<template>
    <div class="container" style=" width:90%">
        <br />

        <b-card v-if="this.ads.length === 0" class="text-center" style="background:#DCDCDC">
            <div>
                <h4 style="color:#696969">You have no ads!</h4>
            </div>
        </b-card>

        <b-card-group deck class="row">
            <div v-for="ad in this.ads" :key="ad.id" class="col-12 col-md-3 col-lg-4">
                <b-card style="width:104%">
                    <b-carousel
                        id="carousel-1"
                        controls
                        :interval="0"
                        indicators
                        background="#ababab"
                        img-width="580"
                        img-height="480"
                        style="text-shadow: 1px 1px 2px #333;"
                        @sliding-start="onSlideStart"
                        @sliding-end="onSlideEnd"
                    >
                        <template>
                            <div v-for="image in ad.images" :key="image.id">
                                <b-carousel-slide
                                    img-width="580"
                                    img-height="480"
                                    img-src="https://picsum.photos/580/480/?image=52"
                                ></b-carousel-slide>
                            </div>
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
                    <b-card-sub-title class="mb-2">Total Milleage: {{ad.totalMilleage}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Allowed Mileage: {{ad.allowedMilleage}}</b-card-sub-title>

                    <hr />
                    <b-card-sub-title class="mb-2">
                        <b-icon icon="calendar" aria-hidden="true" variant="dark" />
                        Start Date: {{ad.startDate}}
                    </b-card-sub-title>
                    <b-card-sub-title class="mb-2">
                        <b-icon icon="calendar-fill" aria-hidden="true" variant="dark" />
                        End Date: {{ad.endDate}}
                    </b-card-sub-title>

                    <div class="modal-footer">
                        <div class="row">
                            <div class="col">
                                <b-button v-if="ad.active" type="button" class="btn btn-dark" style="width:140%">
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Deactive
                                </b-button>
                                <b-button v-if="!ad.active" type="button" class="btn btn-dark" style="width:140%">
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Active
                                </b-button>
                            </div>
                            <div class="col">
                                <b-button type="button" class="btn btn-secondary" style="width:140%">
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Edit
                                </b-button>
                            </div>
                            <div class="col">
                                <b-button type="button" class="btn btn-danger" style="width:140%">
                                    <b-icon icon="x" aria-hidden="true"></b-icon> Delete
                                </b-button>
                            </div>
                        </div>
                    </div>
                </b-card>

                <br />
            </div>
        </b-card-group>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            ads: [
                {
                    brand: "brand",
                    model: "model",
                    carClass: "class",
                    transmission: "trans",
                    fuel: "fuel",
                    idCar: "id",
                    totalMilleage: 5400,
                    allowedMilleage: 5000,
                    childrenSeats: 5,
                    description: "aaaaaaaaaa",
                    colDamProtection: true,
                    images: ["a", "b"],
                    place: "Place",
                    startDate:"aaa",
                    endDate:"aaa",
                },
                {
                    brand: "brand",
                    model: "model",
                    carClass: "class",
                    transmission: "trans",
                    fuel: "fuel",
                    idCar: "id",
                    totalMilleage: 5400,
                    allowedMilleage: 5000,
                    childrenSeats: 5,
                    description: "aaaaaaaaaa",
                    colDamProtection: true,
                    images: ["a", "b"],
                    place: "Place",
                    startDate:"aaa",
                    endDate:"aaa",
                },
                {
                    brand: "brand",
                    model: "model",
                    carClass: "class",
                    transmission: "trans",
                    fuel: "fuel",
                    idCar: "id",
                    totalMilleage: 5400,
                    allowedMilleage: 5000,
                    childrenSeats: 5,
                    description: "aaaaaaaaaa",
                    colDamProtection: true,
                    images: ["a", "b"],
                    place: "Place",
                    startDate:"aaa",
                    endDate:"aaa",
                }
            ]
        };
    },
    methods: {
        getClientCars() {
            axios.get("/cars-ads/ads/client").then(response => {
                this.ads = response.data;
            });
        }
    },
    created() {
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

