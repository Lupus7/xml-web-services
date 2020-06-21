<template>
    <div class="container" style="margin-top:20px;">
        <BookingList mode="requests" />
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
