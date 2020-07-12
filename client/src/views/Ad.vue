<template>
    <div class="container">
        <span class="row">
            <div class="col-6">
                <b-carousel
                    :interval="0"
                    controls
                    indicators
                    background="#ababab"
                    style="text-shadow: 1px 1px 2px #333;"
                >
                    <span v-if="info.images && info.images.length > 0">
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
                <div class="ad-title">
                    <h3 class="car-price float-left">{{ info.brand }} {{ info.model }}</h3>
                    <h3 class="car-price float-right">
                        <span v-if="info.price == 0"> ARANGED PRICE </span>
                        <span v-else> Price: {{ info.price }} </span> 
                        
                    </h3>
                </div>
                <h4 class="ad-info">
                    <b-icon icon="building" aria-hidden="true" variant="light"></b-icon>
                    Advertiser: {{ info.advertiser }}
                </h4>
                <h4 class="ad-info">
                    <b-icon icon="geo-alt" aria-hidden="true" variant="light"></b-icon>
                    Place: {{ info.place }}
                </h4>
                <hr />
                <h4 class="ad-info">Class: {{ info.carClass }}</h4>
                <h4 class="ad-info">Fuel: {{ info.fuel }}</h4>
                <h4 class="ad-info">Transmission: {{ info.transmission }}</h4>
                <h4 class="ad-info">Children seats: {{ info.childrenSeats }}</h4>
                <hr />
                <h4 v-if="info && info.startDate">
                    <b-icon icon="calendar" aria-hidden="true" variant="light" />
                    Start Date: {{ info.startDate.split("T")[0] }}
                </h4>
                <h4 v-if="info && info.endDate">
                    <b-icon icon="calendar-fill" aria-hidden="true" variant="light" />
                    End Date: {{ info.endDate.split("T")[0] }}
                </h4>
                <hr />
                <h6 v-if="show">Log in to rent this car...</h6>
                <h6 v-else-if="!active">This ad is not active...</h6>
                <span v-else>
                    <b-button
                        class="float-left"
                        style="background:#b20000; width:150px"
                        @click="openDiag()"
                    >Book Car</b-button>
                    <b-button
                        v-if="!inCart"
                        class="float-right"
                        style="background:white; width:150px; color: black"
                        @click="addToCart()"
                    >
                        <b-icon icon="plus" aria-hidden="true" variant="dark" />Add to cart
                    </b-button>
                    <b-button
                        v-else
                        class="float-right"
                        style="background:white; width:150px; color: black"
                        disabled
                    >
                        <b-icon icon="check" aria-hidden="true" variant="dark" />In cart
                    </b-button>
                </span>
            </div>
        </span>
        <b-modal
            ref="my-modal"
            hide-footer
            v-bind:title="info.brand + ' ' + info.model"
            v-if="info != null"
        >
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Start Date</label>
                    <b-form-datepicker
                        :date-format-options="{
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                        }"
                        v-model="chosenStart"
                        :min="chosenStart"
                        :max="chosenEnd"
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
                        v-model="chosenEnd"
                        :min="chosenStart"
                        :max="chosenEnd"
                        locale="en"
                    ></b-form-datepicker>
                </div>
            </div>
            <span>
                <button type="button" class="btn btn-danger float-right" data-dismiss="modal">
                    <b-icon icon="x-circle"></b-icon>Close
                </button>
                <button
                    type="button"
                    class="btn btn-success float-right"
                    data-dismiss="modal"
                    style="margin-right: 5px"
                    @click="bookCar()"
                >
                    <b-icon icon="check-circle" aria-hidden="true"></b-icon>Create Booking
                </button>
            </span>
        </b-modal>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            info: {},
            show: true,
            cart: [],
            inCart: false,
            chosenStart: {},
            chosenEnd: {},
            active: true
        };
    },
    props: ["id"],
    methods: {
        fill() {
            let url = "/cars-ads/api/ad/" + this.id;

            axios.get(url).then(response => {
                this.info = response.data;
                this.chosenStart = this.info.startDate;
                this.chosenEnd = this.info.endDate;
                let x = this.info
                axios.get("/cars-ads/test/pricelist/" + this.info.pricelist + "/car/" + this.info.carId).then(response => {
                    x.price = response.data
                });
            });

            axios.get("/cars-ads/api/ad/active/" + this.id).then(response => {
                this.active = response.data;
            });
        },
        bookCar() {
            if (!this.chosenStart.includes("T"))
                this.chosenStart += "T00:00:00";

            if (!this.chosenEnd.includes("T")) this.chosenEnd += "T00:00:00";

            axios
                .post("/rent/api/booking", {
                    // TODO: add date selector for start and end date
                    loaner: this.info.loaner,
                    books: [
                        {
                            adId: this.info.adId,
                            startDate: this.chosenStart,
                            endDate: this.chosenEnd,
                            place: this.info.place
                        }
                    ]
                })
                .then(response => {
                    if (response.status === 200)
                        this.$bvToast.toast(response.data, {
                            title: "Creating Booking",
                            variant: "success",
                            solid: true
                        });
                    let url = "/cars-ads/api/ad/" + this.id;

                    axios.get(url).then(response => {
                        this.info = response.data;
                        this.chosenStart = this.info.startDate;
                        this.chosenEnd = this.info.endDate;
                    });
                });
            this.$refs["my-modal"].hide();
        },
        openDiag() {
            this.$refs["my-modal"].show();
        },
        addToCart() {
            axios.put("/rent/api/cart/" + this.info.adId).then(response => {
                if (response.status < 300) this.inCart = true;
            });
        },
        getCartItems() {
            axios.get("/rent/api/cart").then(response => {
                this.cart = response.data;
                this.isInCart();
            });
        },
        isInCart() {
            this.cart.forEach(c => {
                if (c.carId == this.info.carId) this.inCart = true;
            });
        }
    },
    created() {
        this.getCartItems();
        this.fill();
        const token = localStorage.getItem("accessToken");
        if (token === null || token === "") this.show = true;
        else this.show = false;
    }
};
</script>

<style scoped>
.container {
    background-color: #343a40;
    margin-top: 20px;
    padding: 20px;
    color: white;
}
.ad-title {
    background-color: #b20000;
    padding-right: 5px;
    padding-left: 5px;
    padding-top: 10px;
    border-top-style: solid;
    border-bottom-style: solid;
    border-width: 2px;
    border-color: black;
    height: 60px;
    margin-bottom: 20px;
}
.car-ptice {
    text-align: right;
}
.ad-info {
    margin-bottom: 10px;
}
hr {
    border-top-color: white;
}
</style>
