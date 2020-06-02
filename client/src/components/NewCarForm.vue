<template>
    <div>
        <!-- NEW CAR FORM -->
        <div class="modal fade" id="newcar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Car</h5>
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
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Car Brand</label>
                                        <b-form-select
                                            v-model="brandF"
                                            :options="brands"
                                            @change="fillModels()"
                                            class="form-control"
                                        />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Car Model</label>
                                        <b-form-select
                                            v-model="modelF"
                                            :options="models"
                                            :disabled="modelBool"
                                            class="form-control"
                                        />
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Fuel Type</label>
                                        <b-form-select
                                            v-model="fuelF"
                                            :options="fuels"
                                            class="form-control"
                                        />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Transmission Type</label>
                                        <b-form-select
                                            v-model="transmissionF"
                                            :options="transmissions"
                                            class="form-control"
                                        />
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Car Class</label>
                                        <b-form-select
                                            v-model="classF"
                                            :options="carClasses"
                                            class="form-control"
                                        />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Traveled Km</label>
                                        <b-input
                                            v-model="total_mileageF"
                                            type="number"
                                            min="0"
                                            class="form-control"
                                        />
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Predicted Km</label>
                                        <b-input
                                            v-model="planned_mileageF"
                                            type="number"
                                            min="0"
                                            class="form-control"
                                        />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Children Seats</label>
                                        <b-input
                                            v-model="seats_numberF"
                                            type="number"
                                            min="0"
                                            class="form-control"
                                        />
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Description</label>
                                        <b-form-textarea
                                            rows="7"
                                            v-model="descriptionF"
                                            type="text"
                                            class="form-control"
                                        />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Images</label>
                                        <b-form-file
                                            @change="base64"
                                            id="file"
                                            no-drop
                                            multiple
                                            required
                                            :file-name-formatter="formatNames"
                                            placeholder="Select maximum of 6 images..."
                                        />

                                        <b-card-group deck>
                                            <table v-for="image in this.images" :key="image.id">
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            <b-img
                                                                :src="image"
                                                                style="margin-left:0.9rem;width:115px;height:70px"
                                                            />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </b-card-group>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-0.5">
                                        <b-form-checkbox
                                            v-model="collision_damageF"
                                            style="width:20px; height:25px"
                                            type="checkbox"
                                        />
                                    </div>
                                    <div
                                        class="form-group col-md-11"
                                    >Collision Damage Waiver Protection</div>
                                </div>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            @click="addNewCar()"
                            type="button"
                            class="btn btn-success"
                            data-dismiss="modal"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Add Car
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
import axios from "axios";
export default {
    data() {
        return {
            adds: [],
            descriptionF: "",
            brandF: "",
            modelF: "",
            fuelF: "",
            transmissionF: "",
            classF: "",
            total_mileageF: 0,
            planned_mileageF: 0,
            collision_damageF: false,
            seats_numberF: 0,
            brands: [],
            carClasses: [],
            transmissions: [],
            fuels: [],
            models: [],
            modelBool: true,
            brandsResponse: [],
            images: [],
            files: []
        };
    },
    methods: {
        getCarSpec() {
            axios.get("/cars/api/brands").then(response => {
                // stavi brandove u brands, a mozda samo stringove ubaci
                this.brandsResponse = response.data;
                this.brands = [];
                this.brands.push("");

                for (let b of this.brandsResponse) this.brands.push(b.Name);
            });

            axios.get("/cars/api/classes").then(response => {
                this.carClasses = [];
                this.carClasses.push("");
                for (let cl of response.data) this.carClasses.push(cl);
            });

            axios.get("/cars/api/fuels").then(response => {
                this.fuels = [];
                this.fuels.push("");
                for (let fl of response.data) this.fuels.push(fl);
            });

            axios.get("/cars/api/transmissions").then(response => {
                this.transmissions = [];
                this.transmissions.push("");
                for (let tr of response.data) this.transmissions.push(tr);
            });
        },

        fillModels() {
            if (this.brandF === "") {
                this.modelBool = true;
                this.modelF = "";
                return;
            }

            let brandChosen = null;
            for (let brand of this.brandsResponse) {
                if (this.brandF === brand.Name) {
                    brandChosen = brand;
                    break;
                }
            }
            this.models = [];
            this.models.push("");
            for (let m of brandChosen.Models) this.models.push(m);
            this.modelBool = false;
        },
        formatNames(files) {
            if (files.length > 6) return "Select maximum of 6 images...";
            else return `${files.length} images selected`;
        },
        base64(event) {
            this.images = [];
            var input = event.target;

            if (input.files.length > 6) {
                this.$bvToast.toast("Maxumum number of images is 6", {
                    title: "Images",
                    variant: "warning",
                    solid: true
                });
                return;
            }

            if (input.files) {
                for (let file of input.files) {
                    var reader = new FileReader();
                    reader.onload = e => {
                        let imageData = e.target.result;
                        this.images.push(imageData);
                    };
                    // Start the reader job - read file as a data url (base64 format)
                    reader.readAsDataURL(file);
                }
            }
        },
        resetForm() {
            this.images = [];
            this.descriptionF = "";
            this.brandF = "";
            this.modelF = "";
            this.fuelF = "";
            this.transmissionF = "";
            this.classF = "";
            this.total_mileageF = 0;
            this.planned_mileageF = 0;
            this.collision_damageF = false;
            this.seats_numberF = 0;
        },
        addNewCar() {
            event.preventDefault();
         
            axios
                .post("/cars-ads/cars", {
                    brand: this.brandF,
                    model: this.modelF,
                    fuel: this.fuelF,
                    transmission: this.transmissionF,
                    carClass: this.classF,
                    totalMileage: this.total_mileageF,
                    allowedMilleage: this.planned_mileageF,
                    colDamProtection: this.collision_damageF,
                    childrenSeats: this.seats_numberF,
                    description: this.descriptionF,
                    images: this.images,
                })
                .then(response => {
                    this.resetForm();
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "New Car",
                            variant: "success",
                            solid: true
                        });
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "New Car",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        }
    },
    created() {
        this.getCarSpec();
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
