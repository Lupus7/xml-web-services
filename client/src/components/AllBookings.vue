<template>
    <div class="container" style="margin-top:20px">
        <b-tabs style="background:white;" fill>
            <b-tab title="My Bookings" class="active">
                <BookingList mode="personal" />
            </b-tab>
            <b-tab title="Booking Requests">
                <BookingList mode="requests" />
            </b-tab>
        </b-tabs>
    </div>
</template>

<script>
import BookingList from "./BookingList";

export default {
    components: {
        BookingList,
    },
};
</script>

<style scoped></style>
