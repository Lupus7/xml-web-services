<template>
    <div class="container">
        <b-card
            v-if="this.bookings.length === 0"
            class="text-center"
            style="background:#DCDCDC"
        >
            <div>
                <h4 style="color:#696969">You have nothing in your cart!</h4>
            </div>
        </b-card>

        <span v-else>
            <b-button
                class="float-right"
                style="margin-bottom: 5px; margin-left: 5px"
                v-if="bundle.length > 0"
                @click="cancelBundleSelector()"
            >
                Cancel
            </b-button>
            <b-button
                variant="danger"
                class="float-right"
                style="margin-bottom: 5px;"
                v-if="bundle.length > 0"
                @click="bookBundle()"
            >
                Book
            </b-button>
            <b-table
                striped
                bordered
                hover
                :items="bookings"
                :fields="fields"
                @row-clicked="showOptions"
                ref="table"
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
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Start Date</label>
                    <b-form-datepicker
                        :date-format-options="{
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                        }"
                        v-model="info.chosen_start"
                        :min="info.startDate" 
                        :max="info.chosen_end"
                        locale="en"
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
                        v-model="info.chosen_end"
                        :min="info.chosen_start" 
                        :max="info.endDate"
                        locale="en"
                    ></b-form-datepicker>
                </div>
            </div>
            <span>
                <b-button
                    class="float-right"
                    style="background:white; width:100px; color: black"
                    @click="remove()"
                >
                    Remove
                </b-button>
                <span v-if="bundle.length == 0">
                    <b-button
                        class="float-left"
                        style="background:#b20000; width:100px"
                        @click="bookCar()"
                    >
                        Book Car
                    </b-button>
                    <b-button
                        class="float-right"
                        v-if="!bundle.includes(info)"
                        style="background:#b20000;"
                        @click="addToBundle()"
                    >
                        Add to bundle
                    </b-button>
                    <b-button
                        class="float-right"
                        v-else
                        style="background:#b20000;"
                        @click="removeFromBundle()"
                    >
                        Remove from bundle
                    </b-button>
                </span>
                <span v-else-if="bundle[0].advertiser == info.advertiser">
                    <b-button
                        class="float-left"
                        v-if="info.in_bundle == 'NO'"
                        style="background:#b20000;"
                        @click="addToBundle()"
                    >
                        Add to bundle
                    </b-button>
                    <b-button
                        class="float-left"
                        v-else
                        style="background:#b20000;"
                        @click="removeFromBundle()"
                    >
                        Remove from bundle
                    </b-button>
                </span>
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
                { key: "advertiser", sortable: true },
                { key: "start_date", sortable: true },
                { key: "end_date", sortable: true },
            ],
            bookings: [],
            info: null,
            bundle: [],
        };
    },
    methods: {
        getCartItems() {
            axios.get("/rent/api/cart").then((response) => {
                this.bookings = response.data;
                this.bookings.forEach((b) => {
                    b.car = b.brand + " " + b.model;
                    b.start_date = b.startDate.split("T")[0];
                    b.end_date = b.endDate.split("T")[0];
                    b.in_bundle = "NO";
                    b.chosen_start = b.startDate.split("T")[0];
                    b.chosen_end = b.endDate.split("T")[0];
                });
            });
        },
        showOptions(row) {
            this.info = row;
            this.$refs["my-modal"].show();
        },
        bookCar() {
            axios
                .post("/rent/api/booking", {
                    loaner: this.info.loaner,
                    books: [
                        {
                            adId: this.info.adId,
                            startDate: this.info.chosen_start + "T00:00:00",
                            endDate: this.info.chosen_end + "T00:00:00",
                            place: this.info.place,
                        },
                    ],
                })
                .then(() => {
                    this.$refs["my-modal"].hide();
                    this.getCartItems();
                });
        },
        remove() {
            axios.delete("/rent/api/cart/" + this.info.adId).then(() => {
                this.$refs["my-modal"].hide();
                this.$refs.table.refresh();
                this.getCartItems();
            });
        },
        addToBundle() {
            for (let i = 0; i < this.bundle.length; i++) {
                if (this.bundle[i].adId == this.info.adId) {
                    return;
                }
            }
            this.fields.push({ key: "chosen_start", sortable: true });
            this.fields.push({ key: "chosen_end", sortable: true });
            this.fields.push({ key: "in_bundle", sortable: true });
            this.info.in_bundle = "YES";
            this.bundle.push(this.info);
            this.$refs.table.refresh();
            this.$refs["my-modal"].hide();
        },
        removeFromBundle() {
            for (let i = 0; i < this.bookings.length; i++) {
                if (this.bookings[i].adId == this.info.adId) {
                    this.bookings.in_bundle = "NO";
                    break;
                }
            }
            for (let i = 0; i < this.bundle.length; i++) {
                if (this.bundle[i].adId == this.info.adId) {
                    this.bundle.splice(i, 1);
                    break;
                }
            }
            if (this.bundle.length == 0 && this.fields[this.fields.length - 1].key == "in_bundle") {
                this.fields.splice(this.fields.length - 3, 3);
            }
            this.$refs.table.refresh();
            this.$refs["my-modal"].hide();
        },
        cancelBundleSelector() {
            this.bundle = [];
            if (this.fields[this.fields.length - 1].key == "in_bundle")
                this.fields.splice(this.fields.length - 3, 3);
        },
        bookBundle() {
            let bookBundle = [];
            this.bundle.forEach((b) =>
                bookBundle.push({
                    adId: b.adId,
                    startDate: b.chosen_start + "T00:00:00",
                    endDate: b.chosen_end + "T00:00:00",
                    place: b.place,
                })
            );
            axios
                .post("/rent/api/booking", {
                    loaner: this.info.loaner,
                    books: bookBundle,
                })
                .then(() => {
                    this.$refs["my-modal"].hide();
                    this.cancelBundleSelector();
                    this.getCartItems();
                });
        },
    },
    created() {
        this.getCartItems();
        this.info = this.bookings[0];
        if (this.fields[this.fields.length - 1].key == "in_bundle")
            this.fields.splice(this.fields.length - 3, 3);
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
