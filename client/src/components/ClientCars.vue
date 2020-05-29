  
<template>
    <div class="container" style=" width:90%">
        <br />

        <b-card v-if="this.cars.length === 0" class="text-center" style="background:#DCDCDC">
            <div>
                <h4 style="color:#696969">You have no cars!</h4>
            </div>
        </b-card>

        <b-card-group deck class="row">
            <div v-for="car in this.cars" :key="car.id" class="col-12 col-md-3 col-lg-4">
                <b-card>
                    <b-carousel 
                        id="carousel-1"
                        controls
                        :interval=0
                        indicators
                        background="#ababab"
                        img-width="580"
                        img-height="480"
                        style="text-shadow: 1px 1px 2px #333;"
                        @sliding-start="onSlideStart"
                        @sliding-end="onSlideEnd"
                    >
                        <template>
                            <div v-for="image in car.images" :key="image.id">
                                <b-carousel-slide 
                                    img-width="580"
                                    img-height="480"
                                    img-src="https://picsum.photos/580/480/?image=52"
                                ></b-carousel-slide>
                            </div>
                        </template>

                    
                    </b-carousel>

                     <hr />

                    <b-card-sub-title class="mb-2" style="color: black">Brand: {{car.brand}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Model: {{car.model}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Class: {{car.carClass}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Transmission: {{car.transmission}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Fuel Type: {{car.fuel}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Children Seats: {{car.childrenSeats}}</b-card-sub-title>

                    <hr />

                    <b-card-sub-title class="mb-2">Total Milleage: {{car.totalMilleage}}</b-card-sub-title>
                    <b-card-sub-title class="mb-2">Allowed Mileage: {{car.allowedMilleage}}</b-card-sub-title>

                    <div class="modal-footer">
                        <div class="row">
                            <div class="col">
                                <b-button type="button" class="btn btn-dark" style="width:120%">
                                    <b-icon icon="wrench" aria-hidden="true"></b-icon> Edit
                                </b-button>
                            </div>
                            <div class="col">
                                <b-button type="button" class="btn btn-danger" style="width:120%">
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
            cars: [
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
                    images: ["a", "b"]
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
                    images: ["a", "b"]
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
                    images: ["a", "b"]
                }
            ]
        };
    },
    methods: {
        getClientCars() {
            axios.get("/cars-ads/cars/client").then(response => {
                this.cars = response.data;
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

