<template>
    <div>
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
            cars: [],
            carF:"",
            startDateF:"",
            endDateF:"",
            placeF:""

        };
    },
    methods: {
        getClientCars() {
            axios.get("/cars-ads/cars/client").then(response => {
                // pokupi sve clientove aute
                this.cars = []
                for(let resp of response.data){
                    var select = {value:resp.carId,text:resp.brand+" "+resp.model+" "+resp.carClass}
                    this.cars.push(select);
                }
            });
        },

        resetForm() {
            this.carF = "";
            this.startDateF = "";
            this.endDateF = "";
            this.placeF = "";

        },
        createNewAd(){
            event.preventDefault();
            axios
                .post("/cars-ads/api/ad", {
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
                        this.getClientCars();
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
        this.getClientCars();
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
