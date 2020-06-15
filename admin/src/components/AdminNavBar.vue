<template>
    <div>
        <!-- Navbar -->
        <b-navbar type="light" variant="light">
            <div class="container">
                <b-navbar-nav style="margin-left:0.9rem">
                    <b-nav-item href="#" @click="showClients()">
                        <b-icon icon="people-fill" variant="dark" aria-hidden="true" />Clients
                    </b-nav-item>

                    <b-nav-item-dropdown text="Cars">
                        <b-dropdown-item @click="showBrands()">Brands</b-dropdown-item>
                        <b-dropdown-item @click="showModels()">Models</b-dropdown-item>
                        <b-dropdown-divider></b-dropdown-divider>
                        <b-dropdown-item @click="showFuels()">Fuels</b-dropdown-item>
                        <b-dropdown-item @click="showTransmissions()">Transmissions</b-dropdown-item>
                        <b-dropdown-item @click="showClasses()">Classes</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown text="Registration">
                        <b-dropdown-item data-toggle="modal" data-target="#registerCompany">
                            <b-icon icon="building" variant="dark" aria-hidden="true" />Company
                        </b-dropdown-item>
                        <b-dropdown-item data-toggle="modal" data-target="#registerAgent">
                            <b-icon icon="person-fill" variant="dark" aria-hidden="true" />Agent
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item @click="showComments()">
                        <b-icon icon="chat-dots-fill" aria-hidden="true" variant="dark" />Comments
                    </b-nav-item>

                    <b-nav-item @click="showPermissions()">
                        <b-icon icon="tools" aria-hidden="true" variant="dark" />Permissions
                    </b-nav-item>
                </b-navbar-nav>
            </div>
        </b-navbar>

        <!-- Company Registration -->
        <div class="modal fade" id="registerCompany" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            Company Registration
                            <b-icon icon="building" variant="dark" aria-hidden="true" />
                        </h5>
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                            @click="resetValues()"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div class="form-group">
                                <label>Company Name</label>
                                <b-input
                                    placeholder="Enter Company Name"
                                    v-model="companyNameF"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <b-input
                                    placeholder="Enter Email"
                                    v-model="emailF"
                                    type="email"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Password</label>
                                <b-input
                                    placeholder="Enter Password"
                                    v-model="password"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Address</label>
                                <b-input
                                    placeholder="Enter Address"
                                    v-model="addressF"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Business Number</label>
                                <b-input
                                    placeholder="Enter Bussines Number"
                                    v-model="businessNumberF"
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
                            @click="registerCompany()"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Confirm
                        </button>
                        <button
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                            @click="resetValues()"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Agent Registration -->
        <div class="modal fade" id="registerAgent" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            Agent Registration
                            <b-icon icon="building" variant="dark" aria-hidden="true" />
                        </h5>
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                            @click="resetValues()"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div class="form-group">
                                <label>First Name</label>
                                <b-input
                                    placeholder="Enter Company Name"
                                    v-model="firstName"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Last Name</label>
                                <b-input
                                    placeholder="Enter Company Name"
                                    v-model="lastName"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <b-input
                                    placeholder="Enter Email"
                                    v-model="emailF"
                                    type="email"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Password</label>
                                <b-input
                                    placeholder="Enter Password"
                                    v-model="password"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Address</label>
                                <b-input
                                    placeholder="Enter Address"
                                    v-model="addressF"
                                    type="text"
                                    class="form-control"
                                />
                            </div>

                            <div class="form-group">
                                <label>Business Number</label>
                                <b-input
                                    placeholder="Enter Bussines Number"
                                    v-model="businessNumberF"
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
                            @click="registerAgent()"
                        >
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Confirm
                        </button>
                        <button
                            type="button"
                            class="btn btn-danger"
                            data-dismiss="modal"
                            @click="resetValues()"
                        >
                            <b-icon icon="x-circle"></b-icon>Close
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Komponente-->
        <AdminBrand v-if="brandBool" />
        <AdminModel v-if="modelBool" />
        <AdminFuel v-if="fuelBool" />
        <AdminTransmission v-if="transBool" />
        <AdminClass v-if="classBool" />
        <AdminComments v-if="commentsBool" />
        <AdminPermissions v-if="persmissionsBool" />
        <AdminClients v-if="clientsBool" />
    </div>
</template>

<script>
import AdminBrand from "./AdminBrand";
import AdminModel from "./AdminModel";
import AdminFuel from "./AdminFuel";
import AdminTransmission from "./AdminTransmission";
import AdminClass from "./AdminClass";
import AdminComments from "./AdminComments";
import AdminPermissions from "./AdminPermissions";
import AdminClients from "./AdminClients";
import axios from "axios"

export default {
    data() {
        return {
            brandBool: false,
            modelBool: false,
            fuelBool: false,
            transBool: false,
            classBool: false,
            commentsBool: false,
            persmissionsBool: false,
            clientsBool: false,
            companyNameF: "",
            emailF: "",
            addressF: "",
            businessNumberF: "",
            firstName: "",
            lastName: "",
            password:""
        };
    },
    components: {
        AdminBrand,
        AdminModel,
        AdminFuel,
        AdminTransmission,
        AdminClass,
        AdminComments,
        AdminPermissions,
        AdminClients
    },
    methods: {
        showBrands() {
            this.brandBool = true;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showModels() {
            this.brandBool = false;
            this.modelBool = true;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showFuels() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = true;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showTransmissions() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = true;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showClasses() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = true;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showComments() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = true;
            this.persmissionsBool = false;
            this.clientsBool = false;
        },
        showPermissions() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = true;
            this.clientsBool = false;
        },
        showClients() {
            this.brandBool = false;
            this.modelBool = false;
            this.fuelBool = false;
            this.transBool = false;
            this.classBool = false;
            this.commentsBool = false;
            this.persmissionsBool = false;
            this.clientsBool = true;
        },
        registerCompany() {
            event.preventDefault();
            axios
                .post("/user/client-control/company", {
                    email: this.emailF,
                    password: this.password,
                    name: this.companyNameF,
                    address: this.addressF,
                    businessNumber: this.businessNumberF
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "New Company",
                            variant: "success",
                            solid: true
                        });
                        this.resetValues();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "New Company",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        registerAgent() {
            event.preventDefault();
            axios
                .post("/user/client-control/agent", {
                    email: this.emailF,
                    password: this.password,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    address: this.addressF,
                    businessNumber: this.businessNumberF
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "New Agent",
                            variant: "success",
                            solid: true
                        });
                        this.resetValues();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "New Agent",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        resetValues() {
            this.companyNameF = "";
            this.emailF = "";
            this.addressF = "";
            this.businessNumberF = "";
            this.firstName = "";
            this.lastName = "";
            this.password = "";
        }
    },
    created() {}
};
</script>

<style scoped>
.modal-header {
    border-bottom: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}
.modal-footer {
    border-top: 1px solid #5f5f5f;
    width: 100%;
    font-size: 20px;
    font-size: 3vh;
}
</style>