<template>
    <div>
        <b-card
            v-if="this.bookings.length === 0"
            class="text-center"
            style="background:#DCDCDC"
        >
            <div>
                <h4 style="color:#696969">You have no bookings!</h4>
            </div>
        </b-card>

        <b-table
            striped
            bordered
            hover
            :items="bookings"
            :fields="fields"
            @row-clicked="showOptions"
            v-else
            ref="table"
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
                    Advertizer: {{ info.advertiser }}
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
                <span v-if="info.state == 'PAID'">
                    <div class="form-group">
                        <label>Distance traveled [km]</label>
                        <b-input
                            type="number"
                            class="form-control"
                            v-model="mileage"
                        />
                    </div>
                    <div class="form-group">
                        <label>Extra info</label>
                        <b-form-textarea
                            type="text"
                            class="form-control"
                            v-model="extraInfo"
                            rows="3"
                            max-rows="6"
                        />
                    </div>
                    <b-button
                        class="float-right"
                        style="background:#b20000; color: white"
                        @click="report()"
                    >
                        Report and end booking
                    </b-button>
                </span>
                <b-button
                    class="float-left"
                    style="background:#b20000; width:150px; color: white"
                    @click="approve()"
                    v-if="this.mode=='requests' && info.state == 'PENDING'"
                >
                    Approve
                </b-button>
                <b-button
                    class="float-right"
                    style="background:white; width:150px; color: black"
                    @click="remove()"
                    v-if="canCancel"
                >
                    Cancel
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
                { key: "place", sortable: true },
                { key: "start_date", sortable: true },
                { key: "end_date", sortable: true },
                { key: "Created", sortable: true },
                { key: "state", sortable: true },
            ],
            bookings: [],
            info: null,
            canCancel: true,
            extraInfo: "",
            mileage: 0
        };
    },
    props: ["mode"],
    methods: {
        getBookings() {
            axios.get("/api/booking").then((response) => {
                this.bookings.splice(0, this.bookings.length);
                response.data.forEach((b) => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                    this.bookings.push(b);
                });
                this.$refs.table.refresh();
            });
        },
        getBookings2() {
            axios.get("/api/booking/request").then((response) => {
                this.bookings.splice(0, this.bookings.length);
                response.data.forEach((b) => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                    this.bookings.push(b);
                });
                this.$refs.table.refresh();
            });
        },
        showOptions(row) {
            console.log(row);
            if (
                row.state == "CANCELED" ||
                row.state == "PAID" ||
                row.state == "ENDED" ||
                row.state == "RESERVED"
            )
                this.canCancel = false;
            else 
                this.canCancel = true;
            let url = "/api/ad/" + row.ad;

            axios.get(url).then((response) => {
                this.info = response.data;
                this.info.id = row.id;
                this.info.state = row.state;
            });

            this.$refs["my-modal"].show();
        },
        remove() {
            if (this.mode == "personal") {
                axios
                    .delete("/api/booking/" + this.info.id)
                    .then(this.closeModalAndRefresh());
            } else {
                axios
                    .delete("/api/booking/reject/" + this.info.id)
                    .then(this.closeModalAndRefresh());
            }
        },
        approve() {
            axios
                .put("/api/booking/" + this.info.id)
                .then(this.closeModalAndRefresh());
        },
        report() {
            axios
                .post("/reports", {
                    extraInfo: this.extraInfo,
                    allowedMileage: this.mileage,
                    booking: this.info.id
                })
                .then(this.closeModalAndRefresh());
        },
        closeModalAndRefresh() {
            this.fill();
            this.$refs["my-modal"].hide();
        },
        startConversation(info){

            axios.post("/message/conversation",{
                "receiver":info.advertiser,
                "bookingId":info.id

            }).then(
                this.$router.push("/messages")
            );

        },
        fill() {
            if (this.mode == "personal") this.getBookings();
            else this.getBookings2();
        }
    },
    created() {
        this.fill();
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
