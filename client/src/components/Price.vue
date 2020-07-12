<template>
    <div class="container" style=" width:90%">
        <br />
        <table class="table table-hover" style="background:#efefef; border: 3px solid #4c4c4c">
            <thead>
                <tr>
                    <th colspan="2" style="color:#4c4c4c">
                        <h4>{{getCarName(price.carId)}}</h4>
                    </th>
                    <th style="width:30%" colspan="1">
                        <center>
                            <b-button
                                class="btn btn-success"
                                href="#"
                                data-toggle="modal"
                                data-target="#newdiscount"
                            >
                                <b-icon icon="plus-circle" aria-hidden="true" />New Discount
                            </b-button>
                        </center>
                    </th>
                </tr>

                <tr class="text-center">
                    <th colspan="1">Discount Percent</th>
                    <th colspan="1">Days</th>
                    <th colspan="1" />
                </tr>
            </thead>

            <tbody class="text-center"> 
                <tr
                    v-for="discount in this.price.discounts"
                    :key="discount.id"
                    style="background:	#F6F6F6"
                >
                    <td scope="row">{{discount.percentage}}</td>
                    <td scope="row">{{discount.minDays}}</td>

                    <td style="width:30%" scope="row">
                        <center>
                            <b-button class="btn btn-danger" v-on:click="removeDiscount(discount)">
                                <b-icon icon="x-circle" aria-hidden="true" />Remove Discount
                            </b-button>
                        </center>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- NEW Discount-->
        <div class="modal fade" id="newdiscount" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Discount</h5>
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
                                    <label>Percentage</label>
                                    <b-input
                                        v-model="percentageF"
                                        type="number"
                                        class="form-control"
                                    />
                                </div>
                                <div class="form-group">
                                    <label>Days</label>
                                    <b-input v-model="daysF" type="number" class="form-control" />
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            @click="addDiscount()"
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Add Discount
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
            percentageF: "",
            daysF: "",
            cars2: []
        };
    },
    props: {
        price: Object,
        pricelist:Object,
    },
    methods: {
        getPrice() {
            axios.get("/cars-ads/price/" + this.price.id).then(response => {
                this.price = response.data;
            });
        },

        getClientCars() {
            axios.get("/cars-ads/cars/client").then(response => {
                this.cars2 = response.data;
            });
        },

        getCarName(id) {
            for (let car of this.cars2) {
                if (car.carId === id)
                    return car.brand + " " + car.model + " " + car.carClass;
            }
        },

        getPricelist(){
            axios.get("/cars-ads/pricelist/"+this.pricelist.id).then(response => {
                this.pricelist = response.data;
                for(let pric of this.pricelist.prices){
                    if(pric.id === this.price.id){
                        this.price = pric;
                        return;
                    }
                }
            });
        },


        resetForm() {
            this.daysF = "";
            this.percentageF = "";
        },

        addDiscount() {
            event.preventDefault();
            axios
                .post("/cars-ads/discount", {
                    percentage: this.percentageF,
                    minDays: this.daysF,
                    priceId: this.price.id
                })
                .then(response => {
                    this.resetForm();
                    if (response.status === 200) {
                        this.$bvToast.toast("New Discount added!", {
                            title: "New Discount",
                            variant: "success",
                            solid: true
                        });
                        this.getPricelist();
                    } else {
                        this.$bvToast.toast("Error", {
                            title: "New Discont",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        removeDiscount(discount) {
            event.preventDefault();
            axios
                .delete("/cars-ads/discount/" + discount.id + "/" + this.price.id)
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Discount",
                            variant: "success",
                            solid: true
                        });
                        this.getPricelist();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Discount",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        }
    },
    created() {
        this.getClientCars();
    }
};
</script>

<style>
</style>