<template>
    <div class="container">
        <br />
        <div class="tab-content ml-1" id="myTabContent">
            <div
                class="tab-pane fade show active"
                id="basicInfo"
                role="tabpanel"
                aria-labelledby="basicInfo-tab"
            >
                <table class="table table-bordered table-sm">
                    <thead class="thead-dark">
                        <tr>
                            <th colspan="2">
                                <center>
                                    <h3>User: {{email}}</h3>
                                </center>
                            </th>
                        </tr>
                        <tr>
                            <th colspan="1">
                                <h4>Permissions</h4>
                            </th>
                            <th colspan="1">
                                <center>
                                    <button
                                    type="button"
                                    class="btn btn-success"
                                    data-toggle="modal"
                                    data-target="#newperm"
                                >
                                    <b-icon icon="plus-circle" aria-hidden="true"></b-icon> Add New Permission
                                    </button>
                                </center>
                            </th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="permission in this.userPermissions" :key="permission.id" style="background:	#F5F5F5" >
                            <td v-if="permission.def" scope="row" >
                                <b-icon   v-b-tooltip.hover title="Default Permission" icon="gear-fill" aria-hidden="true"></b-icon>
                                {{permission.name}}
                            </td>
                            <td v-if="!permission.def" scope="row">{{permission.name}}</td>

                            <td style="width:40%" scope="row">
                                <center>
                                    <b-button
                                        class="btn btn-danger"
                                        v-on:click="removePermission(permission)"
                                    >
                                        <b-icon icon="x-circle" aria-hidden="true" /> Remove Permission
                                    </b-button>
                                </center>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Add New PERMISSION -->
        <div class="modal fade" id="newperm" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">List of Permissions</h5>
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
                                <label>Select Permission</label>
                                <b-form-select
                                    v-model="selected"
                                    :options="privileges"
                                    :select-size="10"
                                ></b-form-select>
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button
                            type="button"
                            data-dismiss="modal"
                            class="btn btn-success"
                            @click="addNewPermission()"
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
            userPermissions: [],
            permissionList: [],
            privileges: [],
            selected: ""
        };
    },
    props: ["email"],
    methods: {
        addNewPermission() {
            let url = "/admin/privilege/" + this.email;
            let perms = null;
            for (let p of this.permissionList) {
                if (p.name === this.selected) {
                    perms = p;
                    break;
                }
            }

            if (
                perms === null ||
                this.selected === null ||
                this.selected === ""
            )
                return;

            axios
                .put(url, {
                    name: this.selected,
                    active: true,
                    def: perms.def
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Add Permission",
                            variant: "success",
                            solid: true
                        });
                        this.reset();
                        this.fill();
                        this.fillAvailable();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Add Permission",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        removePermission(permission) {
            let url = "/admin/privilege/" + this.email;
            console.log(permission);
            axios
                .put(url, {
                    name: permission.name,
                    active: false,
                    def: permission.def
                })
                .then(response => {
                    if (response.status === 200) {
                        this.$bvToast.toast(response.data, {
                            title: "Remove Permission",
                            variant: "success",
                            solid: true
                        });
                        this.reset();
                        this.fill();
                        this.fillAvailable();
                    } else {
                        this.$bvToast.toast(response.data, {
                            title: "Remove Permission",
                            variant: "warning",
                            solid: true
                        });
                    }
                });
        },
        reset() {
            this.selected = "";
        },
        fill() {
            let url = "/admin/privilege/" + this.email;
            axios.get(url).then(response => {
                this.userPermissions = response.data;
            });
        },

        fillAvailable() {
            this.privileges = [];
            let url = "/admin/privilege/available/" + this.email;
            axios.get(url).then(response => {
                this.permissionList = response.data;
                for (let p of this.permissionList) {
                    this.privileges.push(p.name);
                }
            });
        }
    },
    created() {
        this.fill();
        this.fillAvailable();
    }
};
</script>

<style scoped>
</style>