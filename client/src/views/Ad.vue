<template>
    <div class="container">
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
                <div class="ad-title">
                    <h3 class="car-price float-left">
                        {{ info.brand }} {{ info.model }}
                    </h3>
                    <h3 class="car-price float-right">
                        <!-- {{ info.price }}  -->0 €
                    </h3>
                </div>
                <h4 class="ad-info">
                    <b-icon
                        icon="building"
                        aria-hidden="true"
                        variant="light"
                    ></b-icon>
                    Advertizer: anonymus
                    <!-- TODO: Advertizer: {{ info.advertiser }} -->
                </h4>
                <h4 class="ad-info">
                    <b-icon
                        icon="geo-alt"
                        aria-hidden="true"
                        variant="light"
                    ></b-icon>
                    Place: {{ info.place }}
                </h4>
                <hr />
                <h4 class="ad-info">Class: {{ info.carClass }}</h4>
                <h4 class="ad-info">Fuel: {{ info.fuel }}</h4>
                <h4 class="ad-info">Transmission: {{ info.transmission }}</h4>
                <h4 class="ad-info">
                    Children seats: {{ info.childrenSeats }}
                </h4>
                <hr />
                <h4>
                    <b-icon
                        icon="calendar"
                        aria-hidden="true"
                        variant="light"
                    />
                    Start Date: {{ info.startDate }}
                </h4>
                <h4>
                    <b-icon
                        icon="calendar-fill"
                        aria-hidden="true"
                        variant="light"
                    />
                    End Date: {{ info.endDate }}
                </h4>
                <hr />
                <h6 v-if="show">Log in to rent this car...</h6>
                <span v-else>
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
                        <b-icon icon="plus" aria-hidden="true" variant="dark" />
                        Add to cart
                    </b-button>
                </span>
            </div>
        </span>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            info: {},
            show: true,
        };
    },
    props: ["id"],
    methods: {
        fill() {
            let url = "/cars-ads/api/ad/" + this.id;
            axios.get(url).then((response) => {
                this.info = response.data;
            });
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
        addToCart() {},
    },
    created() {
        this.fill();
        const token = localStorage.getItem("accessToken");
        if (token === null || token === "") this.show = true;
        else this.show = false;
    },
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
