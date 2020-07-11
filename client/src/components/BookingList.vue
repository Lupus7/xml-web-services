<template>
    <div>
        <b-card v-if="this.bookings.length === 0 && this.bundles.length === 0" class="text-center" style="background:#DCDCDC">
            <div>
                <h4 style="color:#696969">You have no bookings!</h4>
            </div>
        </b-card>
        <span v-else-if="this.bookings.length > 0">
            <span v-if="this.bundles.length > 0">
                <hr />
                <h4 style="color:#696969;text-align: center;">Single bookings:</h4>
                <hr />
            </span>
            <b-table
                striped
                bordered
                hover
                :items="bookings"
                :fields="fields"
                @row-clicked="showOptions"
            ></b-table>
        </span>
        <span v-if="this.bundles.length > 0">
            <hr />
            <h4 style="color:#696969;text-align: center;">Bundles:</h4>
            <hr />
        </span>
        <span v-for="(bundle, index) in bundles" :key="index">
            <div style="padding-left: 20px; padding-right:20px">
                <h5 class="float-left" style="color:#696969;text-align: center;">ID #{{bundle.id}}</h5>
                <span class="float-right" style="margin-top:-5px; margin-bottom:10px">
                    <b-button
                        class="float-left"
                        style="width:160px; color: white"
                        variant="dark"
                        @click="startConversation(bundle.bookings[0])"
                        v-if="mode == 'personal' && bundle.bookings[0].state == 'PAID'"
                    >Start Conversation</b-button>

                    <b-button
                        class="float-left"
                        style="background:#b20000; width:150px; color: white"
                        @click="approveBundle(bundle)"
                        v-if="mode == 'requests' && bundle.bookings[0].state == 'PENDING'"
                    >Approve</b-button>
                    <b-button
                        class="float-right"
                        style="background:white; width:150px; color: black"
                        @click="removeBundle(bundle)"
                        v-if="bundle.bookings[0].state == 'PENDING'"
                    >Cancel</b-button>
                </span>
            </div>
            <b-table
                striped
                bordered
                hover
                :items="bundle.bookings"
                :fields="fields"
                @row-clicked="showOptions"
            ></b-table>
        </span>
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

                        <b-carousel-slide v-else caption="No images" img-blank img-alt="No images">
                            <p>Contact car owner or admin for more info.</p>
                        </b-carousel-slide>
                    </b-carousel>
                </div>
                <div class="col-6">
                    Price:
                    <!-- {{ info.price }}  -->
                    0 €
                    <br />
                    <b-icon icon="building" aria-hidden="true" variant="dark"></b-icon>
                    Advertizer: {{ info.advertiser }}
                    <br />
                    <b-icon icon="geo-alt" aria-hidden="true" variant="dark"></b-icon>
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
                    <b-icon icon="calendar-fill" aria-hidden="true" variant="dark" />
                    End Date: {{ info.endDate.split("T")[0] }}
                    <hr />
                </div>
            </span>
            <span>
                <span v-if="(canReport || info.state == 'PAID') && mode == 'requests'">
                    <div class="form-group">
                        <label>Distance traveled [km]</label>
                        <b-input type="number" class="form-control" v-model="mileage" />
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
                    >Report and end booking</b-button>
                </span>
                <b-button
                    class="float-left"
                    style="width:160px; color: white"
                    variant="danger"
                    @click="leaveRate(info)"
                    v-if="this.mode == 'personal' && info.state == 'ENDED'"
                >Leave Rate</b-button>

                <b-button
                    class="float-left"
                    style="width:160px; color: white"
                    variant="dark"
                    @click="startConversation(info)"
                    v-if="this.mode == 'personal' && info.state == 'PAID' && !inBundle"
                >Start Conversation</b-button>

                <b-button
                    class="float-left"
                    style="background:#b20000; width:150px; color: white"
                    @click="approve()"
                    v-if="this.mode == 'requests' && info.state == 'PENDING' && !inBundle"
                >Approve</b-button>
                <b-button
                    class="float-right"
                    style="background:white; width:150px; color: black"
                    @click="remove()"
                    v-if="canCancel && !inBundle"
                >Cancel</b-button>
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
                { key: "state", sortable: true }
            ],
            bookings: [],
            bundles: [],
            info: null,
            canCancel: true,
            canReport: false,
            inBundle: false
        };
    },
    props: ["mode"],
    methods: {
        getBookings() {
            axios.get("/rent/api/booking").then(response => {
                this.bookings = response.data;
                this.bookings.forEach(b => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                    b.inBundle = false;
                });
            });
        },
        getBookings2() {
            axios.get("/rent/api/booking/request").then(response => {
                this.bookings = response.data;
                this.bookings.forEach(b => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                    b.inBundle = false;
                });
            });
        },
        getBundles() {
            axios.get("/rent/api/bundle").then(response => {
                this.bundles = response.data;
                this.bundles.forEach(bun => {
                    bun.bookings.forEach(b => {
                        b.start_date = b.startDate.split("T")[0];
                        b.end_date = b.endDate.split("T")[0];
                        b.Created = b.created.split("T")[0];
                        b.inBundle = true;
                    });
                });
            });
        },
        getBundles2() {
            axios.get("/rent/api/bundle/request").then(response => {
                this.bundles = response.data;
                this.bundles.forEach(bun => {
                    bun.bookings.forEach(b => {
                        b.start_date = b.startDate.split("T")[0];
                        b.end_date = b.endDate.split("T")[0];
                        b.Created = b.created.split("T")[0];
                        b.inBundle = true;
                    });
                });
            });
        },
        showOptions(row) {
            this.inBundle = row.inBundle;
            this.getReports(row);
            if (
                row.state == "CANCELED" ||
                row.state == "PAID" ||
                row.state == "ENDED"
            )
                this.canCancel = false;
            let url = "/cars-ads/api/ad/" + row.ad;

            axios.get(url).then(response => {
                this.info = response.data;
                this.info.id = row.id;
                this.info.state = row.state;
            });

            this.$refs["my-modal"].show();
        },
        remove() {
            if (this.mode == "personal") {
                axios
                    .delete("/rent/api/booking/" + this.info.id)
                    .then(this.$refs["my-modal"].hide());
            } else {
                axios
                    .delete("/rent/api/booking/reject/" + this.info.id)
                    .then(this.$refs["my-modal"].hide());
            }
        },
        removeBundle(bundle) {
            if (this.mode == "personal") {
                axios
                    .delete("/rent/api/bundle/" + bundle.id)
            } else {
                axios
                    .delete("/rent/api/bundle/reject/" + bundle.id)
            }
        },
        approve() {
            axios
                .put("/rent/api/booking/" + this.info.id)
                .then(this.$refs["my-modal"].hide());
        },
        approveBundle(bundle) {
            axios
                .put("/rent/api/bundle/" + bundle.id)
        },
        report() {
            axios
                .post("/community/reports", {
                    extraInfo: this.extraInfo,
                    allowedMileage: this.mileage,
                    booking: this.info.id
                })
                .then(this.closeModalAndRefresh());
        },
        getReports(row) {
            axios.get("/community/reports").then(response => {
                if (
                    response.data.filter(
                        report => report.booking == this.info.id
                    ).length > 0
                ) {
                    this.canReport = false;
                } else if (row.state == "ENDED") {
                    this.canReport = true;
                } else {
                    this.canReport = false;
                }
            });
        },
        startConversation(info) {
            axios
                .post("/community/message/conversation", {
                    receiver: info.advertiser,
                    bookingId: info.id
                })
                .then(this.$router.push("/messages"));
        },
        leaveRate(info) {
            this.$router.push({ name: "LeaveRate", params: { booking: info } });
        }
    },
    created() {
        if (this.mode == "personal") {
            this.getBookings();
            this.getBundles();
        } else {
            this.getBookings2();
            this.getBundles2();
        }
    }
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
