<template>
    <div class="container" style=" width:90%">
        <br />

        <table class="table table-hover" style="background:#f1f1f1; border: 3px solid #2c2c2c">
            <thead>
                <tr>
                    <th colspan="2" style="color:#4c4c4c">
                        <h4>My Pricelists</h4>
                    </th>
                    <th style="width:30%" colspan="1">
                        <center>
                            <b-button
                                class="btn btn-success"
                                href="#"
                                style="width:55%"
                                data-toggle="modal"
                                data-target="#newpricelist"
                            >
                                <b-icon icon="plus-circle" aria-hidden="true" />New Pricelist
                            </b-button>
                        </center>
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr
                    v-for="pricelist in this.pricelists"
                    :key="pricelist.id"
                    style="background:	#F6F6F6"
                >
                    <td scope="row">{{pricelist.name}}</td>
                    <td style="width:30%" scope="row">
                        <center>
                            <b-button class="btn btn-warning" v-on:click="viewPricelist(pricelist)">
                                <b-icon icon="arrow-up-circle" aria-hidden="true" />View Pricelist
                            </b-button>
                        </center>
                    </td>
                    <td style="width:30%" scope="row">
                        <center>
                            <b-button
                                class="btn btn-danger"
                                v-on:click="removePricelist(pricelist)"
                            >
                                <b-icon icon="x-circle" aria-hidden="true" />Remove Pricelist
                            </b-button>
                        </center>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- NEW PRICELIST -->
        <div class="modal fade" id="newpricelist" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Pricelist</h5>
                        <button
                            type="button"
                            class="close"
                            @click="resetForm()"
                            data-dismiss="modal"
                            aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Name</label>
                                    <b-input v-model="nameF" type="text" class="form-control" />
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            @click="addPricelist()"
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Create Pricelist
                        </button>
                        <button
                            @click="resetForm()"
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            pricelists: [],
            nameF: "",
        };
    },
    methods: {
        getPricelists() {
            axios.get("/pricelist").then(response => {
                this.pricelists = response.data;
            });
        },

        addPricelist() {
            event.preventDefault();
            axios
                .post("/pricelist", {
                  name:this.nameF
                })
                .then(response => {
                    this.resetForm();
                    if (response.status === 200) {
                        this.$bvToast.toast("New Pricelist added!", {
                            title: "New Pricelist",
                            variant: "success",
                            solid: true
                        });
                        this.getPricelists();
                    }else {
                        this.$bvToast.toast("Error", {
                            title: "New Pricelist",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        
        },

        viewPricelist(price) {
            event.preventDefault();
            this.$router.push({
                name: "Pricelist",
                params: { pricelist: price }
            });
        },

        removePricelist(pricelist) {
            event.preventDefault();
            axios
                .delete("/pricelist/" + pricelist.id)
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Pricelist",
                            variant: "success",
                            solid: true
                        });
                        this.getPricelists();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Delete Pricelist",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },

        resetForm() {
            this.nameF = "";
        }
    },
    created() {
        this.getPricelists();
    }
};
</script>

<style>
</style>