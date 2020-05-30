<template>
    <div style="width:90%" class="container">
        <br />
        <table class="table table-bordered table-sm" v-if="this.models.length>0">
            <thead class="thead-dark">
                <tr>
                    <th colspan="2">
                        <h3>Models</h3>
                    </th>
                    <th style="width:30%" colspan="1">
                        <center>
                            <b-button
                                class="btn btn-success"
                                style="width:45%"
                                href="#"
                                data-toggle="modal"
                                 data-target="#newmodel"
                            >
                                <b-icon icon="plus-circle" aria-hidden="true" /> New Model
                            </b-button>
                        </center>
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="Model in this.models" :key="Model.id" style="background:	#F5F5F5" >
                    <td scope="row">{{Model.name}}</td>
                    <td style="width:30%" scope="row">
                        <center>
                            <b-button
                                class="btn btn-warning"
                                v-on:click="editModel(Model)"
                                data-toggle="modal"
                                data-target="#editmodel"
                            >
                                <b-icon icon="wrench" aria-hidden="true" /> Edit Model
                            </b-button>
                        </center>
                    </td>
                    <td style="width:30%" scope="row">
                        <center>
                            <b-button class="btn btn-danger" v-on:click="removeModel(Model)">
                                <b-icon icon="x-circle" aria-hidden="true" /> Remove Model
                            </b-button>
                        </center>
                    </td>
                </tr>
            </tbody>
        </table>


        <!-- Add New Model -->
        <div class="modal fade" id="newmodel" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Model</h5>
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                            @click="reset()"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <b-form>
                            <div class="form-group">
                                <label>Name of Model</label>
                                <b-input
                                    placeholder="Enter Model Name"
                                    v-model="modelF"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Select Brand</label>
                                <b-form-select
                                    v-model="brandF"
                                    :options="brands"
                                    class="form-control"
                                />
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                            @click="addNewModel()"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Confirm
                        </button>
                        <button
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                            @click="reset()"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Model -->
        <div class="modal fade" id="editmodel" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Model</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <b-form>
                            <div class="form-group">
                                <label>Name of Model</label>
                                <b-input
                                    placeholder="Enter Model Name"
                                    v-model="modelEdit"
                                    type="text"
                                    class="form-control"
                                />
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                            @click="editModelFinal()"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Confirm
                        </button>
                        <button
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                            @click="reset()"
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
import axios from "axios";
export default {
    data() {
        return {
            fields: [{ key: "Name", label: "Name", sortable: false }],
            models: [],
            modelF: "",
            modelEdit: "",
            modelId: "",
            brands: [],
            brandF: ""
        };
    },
    methods: {
        removeModel(model) {
            let url = "/codebook/model/" + model.id;

            axios.delete(url).then(response => {
                if (response.status === 200) {
                    this.$bvToast.toast(response.data, {
                        title: "Removing Model",
                        variant: "success",
                        solid: true
                    });
                    this.fill();
                } else {
                    this.$bvToast.toast(response.data, {
                        title: "Removing Model",
                        variant: "warning",
                        solid: true
                    });
                }
            });
        },

        addNewModel() {
            axios
                .post("/codebook/model/", {
                    name: this.modelF,
                    brand: this.brandF
                })
                .then(response => {
                    if (response.status === 200) {
                        this.reset();
                        this.$bvToast.toast(response.data, {
                            title: "New Model",
                            variant: "success",
                            solid: true
                        });
                        this.fill();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "New Model",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        editModel(model) {
            this.modelEdit = model.name;
            this.modelId = model.id;
        },
        editModelFinal() {
            let url = "/codebook/model/" + this.modelId;

            axios
                .put(url, {
                    id: this.modelId,
                    name: this.modelEdit
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Model Edit",
                            variant: "success",
                            solid: true
                        });
                        this.fill();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Model Edit",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        reset() {
            this.modelF = "";
            this.brandF = "";
        },
        fill() {
            axios.get("/codebook/model").then(response => {
                this.models = response.data;
            });
        }
    },
    created() {
        this.fill();

        axios.get("/codebook/brand").then(response => {
            this.brands = [];
            for (let b of response.data) {
                this.brands.push(b.name);
            }
        });
    }
};
</script>

<style scoped>
.modal-footer {
    border-top: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}

.modal-header {
    border-bottom: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}
</style>