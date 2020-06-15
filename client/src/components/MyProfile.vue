<template>
    <div>
        <b-navbar type="light" variant="light">
            <div class="container">
                <b-navbar-nav style="margin-left:0.9rem">
                    <b-nav-item-dropdown text="My Ads">
                     
                        <b-dropdown-item @click="showClientAds()">
                            <b-icon
                                icon="newspaper"
                                aria-hidden="true"
                                variant="dark"
                            ></b-icon>
                            All Ads
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown text="My Cars">
                        <b-dropdown-item
                            data-toggle="modal"
                            data-target="#newcar"
                        >
                            <b-icon
                                icon="plus-circle-fill"
                                aria-hidden="true"
                                variant="dark"
                            ></b-icon>
                            New Car
                        </b-dropdown-item>
                        <b-dropdown-item @click="showClientCars()">
                            <b-icon
                                icon="minecart"
                                aria-hidden="true"
                                variant="dark"
                            ></b-icon>
                            All Cars
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown text="My Bookings">
                        <b-dropdown-item
                            data-toggle="modal"
                            data-target="#newbooking"
                        >
                            <b-icon
                                icon="calendar-plus-fill"
                                aria-hidden="true"
                                variant="dark"
                            ></b-icon>
                            Create Booking
                        </b-dropdown-item>
                        <b-dropdown-item @click="showClientBookings()">
                            <b-icon
                                icon="calendar3-fill"
                                aria-hidden="true"
                                variant="dark"
                            ></b-icon>
                            All Bookings
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item @click="showClientCart()" right>
                        <b-icon
                            icon="cart-fill"
                            aria-hidden="true"
                            variant="dark"
                        ></b-icon>
                        My Cart
                    </b-nav-item>
                </b-navbar-nav>
            </div>
        </b-navbar>
        <NewCarForm />
        <NewAdForm />
        <BookingSubmitForm />
        <BookingList v-if="this.boolbook" />
        <ClientCars v-if="this.boolcars" />
        <ClientAds v-if="this.boolads" />
        <Cart v-if="this.boolcart" />
    </div>
</template>

<script>
import NewCarForm from "./NewCarForm";
import NewAdForm from "./NewAdForm";
import ClientCars from "./ClientCars";
import ClientAds from "./ClientAds";
import BookingSubmitForm from "./BookingSubmitForm";
import BookingList from "./BookingList";
import Cart from "./Cart";

export default {
    data() {
        return {
            boolcars: false,
            boolads: false,
            boolbook: false,
            boolcart: false,
        };
    },
    components: {
        NewCarForm,
        NewAdForm,
        ClientCars,
        ClientAds,
        BookingSubmitForm,
        BookingList,
        Cart,
    },
    methods: {
        showClientCars() {
            this.boolcars = true;
            this.boolads = false;
            this.boolbook = false;
            this.boolcart = false;
        },
        showClientAds() {
            this.boolads = true;
            this.boolcars = false;
            this.boolbook = false;
            this.boolcart = false;
        },
        showClientBookings() {
            this.boolads = false;
            this.boolcars = false;
            this.boolbook = true;
            this.boolcart = false;
        },
        showClientCart() {
            this.boolads = false;
            this.boolcars = false;
            this.boolbook = false;
            this.boolcart = true;
        },
    },
    created() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
