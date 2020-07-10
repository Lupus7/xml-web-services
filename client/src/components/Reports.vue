<template>
    <div class="container" style="margin-top: 10px;">
        <b-card v-if="this.stats.length === 0" class="text-center" style="background:#DCDCDC">
            <div>
                <h4 style="color:#696969">You have no reports, so there are no statistics!</h4>
            </div>
        </b-card>

        <b-table
            striped
            bordered
            hover
            :items="stats"
            :fields="fields"
            v-else
            ref="table"
            style="background:white"
            @row-clicked="fillReports"
        ></b-table>

        <b-modal ref="my-modal" hide-footer title="Reports:" scrollable>
            <b-table
                v-if="reports != null && reports.length > 0"
                bordered
                :items="reports"
                :fields="fields_modal"
                stacked
                ref="table_reports"
                style="background:white"
            ></b-table>
            <span v-else> No reports for this car! </span>
        </b-modal>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            stats: [],
            fields: [
                { key: "carId", label: "ID", sortable: true },
                { key: "carName", label: "Car", sortable: true },
                { key: "totalMileage", label: "Total Mileage", sortable: true },
                { key: "rating", label: "Rating", sortable: true },
                { key: "commNum", label: "Number of comments", sortable: true }
            ],
            reports: [],
            fields_modal: [
                { key: "allowedMileage", label: "Mileage" },
                { key: "extraInfo", label: "Extra info" } 
            ]
        };
    },
    methods: {
        getAll() {
            axios.get("/community/reports/statistics").then(response => {
                this.stats = response.data;
            });
        },
        fillReports(row) {
            axios.get("/community/reports/cars/" + row.carId).then(response => {
                this.reports = response.data;
                this.$refs["my-modal"].show();
            });
        }
    },
    created() {
        this.getAll();
    }
};
</script>

<style scoped></style>
