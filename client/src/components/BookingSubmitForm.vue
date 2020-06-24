<template>
    <div>
        <!-- NEW BOOKING FORM -->
        <div
            class="modal fade"
            id="newbooking"
            tabindex="-1"
            role="dialog"
            aria-hidden="true"
            ref="my-modal"
        >
            <div class="modal-dialog">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Booking</h5>
                        <button
                            type="button"
                            class="close"
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
                                    <label>Ad</label>
                                    <b-form-select
                                        v-model="selected"
                                        :options="options"
                                    ></b-form-select>
                                </div>

                                <div class="form-group">
                                    <label>Place</label>
                                    <b-input
                                        type="text"
                                        class="form-control"
                                        v-model="place"
                                    />
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Start Date</label>
                                        <b-form-datepicker
                                            :date-format-options="{
                                                year: 'numeric',
                                                month: '2-digit',
                                                day: '2-digit',
                                            }"
                                            v-model="startDate"
                                            :min="new Date()"
                                            locale="en"
                                            :disabled="selected == -1"
                                        ></b-form-datepicker>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>End Date</label>
                                        <b-form-datepicker
                                            :date-format-options="{
                                                year: 'numeric',
                                                month: '2-digit',
                                                day: '2-digit',
                                            }"
                                            v-model="endDate"
                                            :min="new Date()"
                                            locale="en"
                                            :disabled="selected == -1"
                                        ></b-form-datepicker>
                                    </div>
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                            @click="createBooking()"
                            :disabled="
                                selected == -1 ||
                                    place == '' ||
                                    startDate == '' ||
                                    endDate == ''
                            "
                        >
                            <b-icon
                                icon="check-circle"
                                aria-hidden="true"
                            ></b-icon
                            >Create Booking
                        </button>
                        <button
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
            selected: -1,
            options: [{ value: -1, text: "Please select an ad..." }],
            startDate: "",
            endDate: "",
            place: "",
        };
    },
    methods: {
        createBooking() {
            axios.post("/rent/api/booking", {
                loaner: "NONE",
                books: [
                    {
                        adId: this.selected,
                        startDate: this.startDate + "T00:00:00",
                        endDate: this.endDate + "T00:00:00",
                        place: this.place,
                    },
                ],
            });
        },
    },
    created() {
        axios.get("/cars-ads/api/ad/client").then((response) => {
            response.data.forEach((element) => {
                this.options.push({
                    value: element.adId,
                    text:
                        "#" +
                        element.adId +
                        ": " +
                        element.brand +
                        " " +
                        element.model +
                        " ",
                });
            });
        });
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
