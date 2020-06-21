<template>
    <div class="container" style="margin-top: 10px;">
        <b-card
            v-if="this.reports.length === 0"
            class="text-center"
            style="background:#DCDCDC"
        >
            <div>
                <h4 style="color:#696969">You have no reports, so there are no statistics!</h4>
            </div>
        </b-card>

        <b-table
            striped
            bordered
            hover
            :items="reports"
            :fields="fields"
            v-else
            ref="table"
            style="background:white"
        ></b-table>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            reports: [],
            fields: [
                { key: "carId", label: "ID", sortable: true },
                { key: "carName", label: "Car", sortable: true },
                { key: "totalMileage", label: "Total Mileage", sortable: true },
                { key: "rating", label: "Rating", sortable: true },
                { key: "commNum", label: "Number of comments", sortable: true },
            ],
        }
    },
    methods: {
        getAll() {
            axios
                .get("/reports/statistics")
                .then(response => {
                    this.reports = response.data;
                });
        }
    },
    created() {
        this.getAll();
    }
}
</script>

<style scoped></style>
