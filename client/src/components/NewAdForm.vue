<template>
    <div>
        <!-- NEW AD FORM -->
        <div class="modal fade" id="newad" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Ad</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
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
                        <button type="button" class="btn btn-success" data-dismiss="modal">
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
        getCars() {
            axios.get("").then(response => {
                // pokupi sve clientove aute
                this.cars = response.data;
            });
        },

        resetForm() {}
    },
    created() {
        this.getCars();
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
