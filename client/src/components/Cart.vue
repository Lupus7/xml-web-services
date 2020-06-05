<template>
    <div class="container">
        <b-table
            striped
            bordered
            hover
            :items="bookings"
            :fields="fields"
            @row-clicked="showOptions"
        ></b-table>
        <b-modal
            ref="my-modal"
            hide-footer
            v-bind:title="info.brand + ' ' + info.model"
            v-if="info != null"
        >
            <span class="row">
                <div class="col-6">
                    <b-carousel
                        v-model="slide"
                        :interval="4000"
                        controls
                        indicators
                        background="#ababab"
                        style="text-shadow: 1px 1px 2px #333;"
                        @sliding-start="onSlideStart"
                        @sliding-end="onSlideEnd"
                    >
                        <span v-if="info.images.length > 0">
                            <b-carousel-slide
                                v-for="(item, index) in info.images"
                                :key="index"
                                v-bind:img-src="item"
                                img-alt="Image unavailable"
                            />
                        </span>

                        <b-carousel-slide
                            v-else
                            caption="No images"
                            img-blank
                            img-alt="No images"
                        >
                            <p>
                                Contact car owner or admin for more info.
                            </p>
                        </b-carousel-slide>
                    </b-carousel>
                </div>
                <div class="col-6">
                    Price:
                    <!-- {{ info.price }}  -->0 €
                    <br />
                    <b-icon
                        icon="building"
                        aria-hidden="true"
                        variant="dark"
                    ></b-icon>
                    Advertizer: anonymus
                    <!-- TODO: Advertizer: {{ info.advertiser }} -->
                    <br />
                    <b-icon
                        icon="geo-alt"
                        aria-hidden="true"
                        variant="dark"
                    ></b-icon>
                    Place: {{ info.place }}
                    <br />
                    Class: {{ info.carClass }}
                    <br />
                    Fuel: {{ info.fuel }}
                    <br />
                    Transmission: {{ info.transmission }}
                    <br />
                    Children seats: {{ info.childrenSeats }}
                    <br />
                    <b-icon icon="calendar" aria-hidden="true" variant="dark" />
                    Start Date: {{ info.startDate.split("T")[0] }}
                    <br />
                    <b-icon
                        icon="calendar-fill"
                        aria-hidden="true"
                        variant="dark"
                    />
                    End Date: {{ info.endDate.split("T")[0] }}
                    <hr />
                </div>
            </span>
            <span>
                <b-button
                    class="float-left"
                    style="background:#b20000; width:150px"
                    @click="bookCar()"
                >
                    Book Car
                </b-button>
                <b-button
                    class="float-right"
                    style="background:white; width:150px; color: black"
                    @click="addToCart()"
                >
                    Remove
                </b-button>
            </span>
        </b-modal>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            fields: [
                { key: "car", sortable: true },
                { key: "place", sortable: true },
                { key: "startDate", sortable: true },
                { key: "endDate", sortable: true },
            ],
            bookings: [],
            info: null,
        };
    },
    methods: {
        getCartItems() {
            axios.get("/rent/api/cart").then((response) => {
                this.bookings = response.data;
                this.bookings.forEach((b) => {
                    b.car = b.brand + " " + b.model;
                });
            });
        },
        showOptions(row) {
            console.log(row);
            this.info = row;
            this.$refs["my-modal"].show();
        },
        bookCar() {
            axios.post("/rent/api/booking", {
                // TODO: add date selector for start and end date
                adId: this.info.adId,
                startDate: this.info.startDate + " 00:00",
                endDate: this.info.endDate + " 00:00",
                place: this.info.place,
            });
        },
    },
    created() {
        this.getCartItems();
        this.info = this.bookings[0];
    },
};
</script>

<style scoped>
.container {
    margin-top: 10px;
}
.b-table {
    background-color: white;
}
</style>
