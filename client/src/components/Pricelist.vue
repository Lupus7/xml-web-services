<template>
    <div class="container" style=" width:90%">
        <br />

        <table class="table table-hover" style="background:#efefef; border: 3px solid #4c4c4c">
            <thead>
                <tr>
                    <th colspan="6" style="color:#4c4c4c">
                        <h4>{{pricelist.name}}</h4>
                    </th>
                    <th style="width:30%" colspan="1">
                        <center>
                            <b-button
                                class="btn btn-danger"
                                href="#"
                                data-toggle="modal"
                                data-target="#newprice"
                            >
                                <b-icon icon="plus-circle" aria-hidden="true" />New Car Price
                            </b-button>
                        </center>
                    </th>
                </tr>

                <tr class="text-center">
                    <th colspan="1">Car</th>
                    <th colspan="1">Price</th>
                    <th colspan="1">Price Km</th>
                    <th colspan="1">Price CDW</th>
                    <th colspan="1"/>
                    <th colspan="1"/>
                    <th colspan="1"/>
                </tr>
            </thead>

            <tbody>
                <tr
                    v-for="price in this.pricelist.prices"
                    :key="price.id"
                    style="background:	#F6F6F6"
                >
                    <td scope="row">{{getCarName(price.carId)}}</td>
                    <td scope="row">{{price.price}}</td>
                    <td scope="row">{{price.priceKm}}</td>
                    <td scope="row">{{price.priceCdw}}</td>

                    <td style="width:15%" scope="row">
                        <center>
                            <b-button class="btn" variant="primary" v-on:click="viewPrice(price)">
                                <b-icon icon="arrow-up-circle" aria-hidden="true" />View Price
                            </b-button>
                        </center>
                    </td>
                    <td style="width:15%" scope="row">
                        <center>
                            <b-button class="btn btn-warning" v-on:click="editPrice(price)" data-toggle="modal"
                                data-target="#editprice">
                                <b-icon icon="wrench" aria-hidden="true" />Edit Price
                            </b-button>
                        </center>
                    </td>
                    <td style="width:15%" scope="row">
                        <center>
                            <b-button class="btn btn-danger" v-on:click="removePrice(price)">
                                <b-icon icon="x-circle" aria-hidden="true" />Remove Price
                            </b-button>
                        </center>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- NEW PRICE-->
        <div class="modal fade" id="newprice" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Price</h5>
                        <button
                            type="button"
                            class="close"
                            @click="resetForm()"
                            data-dismiss="modal"
                            aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Car</label>
                                    <b-form-select
                                        v-model="carId"
                                        :options="cars"
                                        class="form-control"
                                    />
                                </div>
                                <div class="form-group">
                                    <label>Price</label>
                                    <b-input v-model="priceF" type="number" class="form-control" />
                                </div>
                                <div class="form-group">
                                    <label>PriceKm</label>
                                    <b-input v-model="priceKmF" type="number" class="form-control" />
                                </div>
                                <div class="form-group">
                                    <label>Price Collision Protection Damage</label>
                                    <b-input
                                        v-model="priceCdwF"
                                        type="number"
                                        class="form-control"
                                    />
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            @click="addPrice()"
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Create Price
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

        <!-- Edit PRICE-->
        <div class="modal fade" id="editprice" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Price</h5>
                        <button
                            type="button"
                            class="close"
                            @click="resetFormEdit()"
                            data-dismiss="modal"
                            aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Price</label>
                                    <b-input v-model="priceFE" type="number" class="form-control" />
                                </div>
                                <div class="form-group">
                                    <label>PriceKm</label>
                                    <b-input
                                        v-model="priceKmFE"
                                        type="number"
                                        class="form-control"
                                    />
                                </div>
                                <div class="form-group">
                                    <label>Price Collision Protection Damage</label>
                                    <b-input
                                        v-model="priceCdwFE"
                                        type="number"
                                        class="form-control"
                                    />
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            @click="editPriceFinal()"
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Edit Price
                        </button>
                        <button
                            @click="resetFormEdit()"
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
            priceF: "",
            priceCdwF: "",
            priceKmF: "",
            priceFE: "",
            priceCdwFE: "",
            priceKmFE: "",
            cars: [],
            carId: "",
            cars2: [],
            priceId :"",
        };
    },
    props: {
        pricelist: Object
    },

    methods: {
        getClientCars() {
            axios.get("/cars-ads/cars/client").then(response => {
                this.cars = [];
                this.cars2 = response.data;
                for (let resp of response.data) {
                    var select = {
                        value: resp.carId,
                        text:
                            resp.brand + " " + resp.model + " " + resp.carClass
                    };
                    this.cars.push(select);
                }
            });
        },

        getCarName(id) {
            for (let car of this.cars2) {
                if (car.carId === id)
                    return car.brand + " " + car.model + " " + car.carClass;
            }
        },

        addPrice() {

            event.preventDefault();
            axios
                .post("/cars-ads/price", {
                  carId: this.carId,
                  price: this.priceF,
                  priceKm: this.priceKmF,
                  priceCdw: this.priceCdwF,
                  pricelistId: this.pricelist.id
                })
                .then(response => {
                    this.resetForm();
                    if (response.status === 200) {
                        this.$bvToast.toast("New Price added!", {
                            title: "New Price",
                            variant: "success",
                            solid: true
                        });
                    this.getPricelist();
                    }else {
                        this.$bvToast.toast("Error", {
                            title: "New Price",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        

        },
        editPrice(price) {
            event.preventDefault();
            this.priceFE = price.price;
            this.priceKmFE = price.priceKm;
            this.priceCdwFE = price.priceCdw;
            this.priceId = price.id;
        },
        editPriceFinal() {
            event.preventDefault();
            axios
                .put("/cars-ads/price/"+this.priceId, {
                  price: this.priceFE,
                  priceKm: this.priceKmFE,
                  priceCdw: this.priceCdwFE,
                })
                .then(response => {
                    this.resetFormEdit();
                    if (response.status === 200) {
                        this.$bvToast.toast("Editing succesfull!", {
                            title: "Edit Price",
                            variant: "success",
                            solid: true
                        });
                    this.getPricelist();
                    }else {
                        this.$bvToast.toast("Error", {
                            title: "Edit Price",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        
        },
        removePrice(price) {

            event.preventDefault();
            axios
                .delete("/cars-ads/price/" + price.id+"/"+this.pricelist.id)
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Price",
                            variant: "success",
                            solid: true
                        });
                        this.getPricelist();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Price",
                            variant: "warning",
                            solid: true
                        });
                    }
                });

        },
        resetForm() {
            this.priceF = "";
            this.priceKmF = "";
            this.priceCdwF = "";
            this.carId = "";
        },
        resetFormEdit() {
            this.priceFE = "";
            this.priceKmFE = "";
            this.priceCdwFE = "";
            this.priceId = "";
        },

        getPricelist(){
             axios.get("/cars-ads/pricelist/"+this.pricelist.id).then(response => {
                this.pricelist = response.data;
            });
        },

         viewPrice(pricea) {
            event.preventDefault();
            this.$router.push({
                name: "Price",
                params: { price: pricea, pricelist:this.pricelist}
            });
        },
    },

    created() {
        this.getClientCars();
    }
};
</script>

<style>
</style>