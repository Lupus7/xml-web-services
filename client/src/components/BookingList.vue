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

                 <b-button
                    class="float-left"
                    style="width:160px; color: white"
                    variant="dark"
                    @click="startConversation(info)"
                    v-if="this.mode=='personal' && info.state == 'RESERVED'"
                >
                    Start Conversation
                </b-button>

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
        };
    },
    props: ["mode"],
    methods: {
        getBookings() {
            axios.get("/rent/api/booking").then((response) => {
                this.bookings = response.data;
                this.bookings.forEach((b) => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                });
            });
        },
        getBookings2() {
            axios.get("/rent/api/booking/request").then((response) => {
                this.bookings = response.data;
                this.bookings.forEach((b) => {
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.Created = b.created.split("T")[0];
                });
            });
        },
        showOptions(row) {
            console.log(row);
            if (
                row.state == "CANCELED" ||
                row.state == "PAID" ||
                row.state == "ENDED"
            )
                this.canCancel = false;
            let url = "/cars-ads/api/ad/" + row.ad;

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
                    .delete("/rent/api/booking/" + this.info.id)
                    .then(this.$refs["my-modal"].hide());
            } else {
                axios
                    .delete("/rent/api/booking/reject/" + this.info.id)
                    .then(this.$refs["my-modal"].hide());
            }
        },
        approve() {
            axios
                .put("/rent/api/booking/" + this.info.id)
                .then(this.$refs["my-modal"].hide());
        },
        startConversation(info){

            axios.post("/community/message/conversation",{
                "receiver":info.advertiser,
                "bookingId":info.id

            }).then(
                this.$router.push("/messages")
            );

         
        }
    },
    created() {
        if (this.mode == "personal") this.getBookings();
        else this.getBookings2();
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
