<template>
    <div class="container" style=" width:90%">
        <br />
        <b-card-group deck class="row">
            <div v-for="user in this.users" :key="user.id" class="col-12 col-md-3 col-lg-4">
                <div v-if="user.role === 'CLIENT'">
                    <b-card
                        img-src="../assets/user.gif"
                        img-alt="Image"
                        img-top
                        img-height="200px"
                        img-width="50px"
                        style="width:285px"
                    >
                        <b-card-title>{{user.email}}</b-card-title>

                        <b-card-sub-title class="mb-2">Name: {{user.name}}</b-card-sub-title>
                        <b-card-sub-title class="mb-3">Address: {{user.address}}</b-card-sub-title>

                        <div class="modal-footer">
                            <center>
                                <b-button
                                    type="button"
                                    class="btn btn-success"
                                    @click="goToPermissions(user)"
                                    style="width:100%"
                                >
                                    <b-icon icon="lock-fill" aria-hidden="true"></b-icon>Permissions
                                </b-button>
                            </center>
                        </div>
                    </b-card>
                </div>

                <div v-if="user.role === 'COMPANY'">
                    <b-card
                        img-src="../assets/company.png"
                        img-alt="Image"
                        img-top
                        img-height="200px"
                        img-width="50px"
                        style="width:285px"
                    >
                        <b-card-title>{{user.email}}</b-card-title>

                        <b-card-sub-title class="mb-2">Name: {{user.name}}</b-card-sub-title>
                        <b-card-sub-title class="mb-3">Address: {{user.address}}</b-card-sub-title>

                        <div class="modal-footer">
                            <center>
                                <b-button
                                    type="button"
                                    class="btn btn-success"
                                    @click="goToPermissions(user)"
                                    style="width:100%"
                                >
                                    <b-icon icon="lock-fill" aria-hidden="true"></b-icon>Permissions
                                </b-button>
                            </center>
                        </div>
                    </b-card>
                </div>

                <div v-if="user.role === 'ADMIN'">
                    <b-card
                        img-src="../assets/admin.png"
                        img-alt="Image"
                        img-top
                        img-height="200px"
                        img-width="50px"
                        style="width:285px"
                    >
                        <b-card-title>{{user.email}}</b-card-title>

                        <b-card-sub-title class="mb-2">Name: {{user.name}}</b-card-sub-title>
                        <b-card-sub-title class="mb-3">Address: {{user.address}}</b-card-sub-title>

                        <div class="modal-footer">
                            <center>
                                <b-button
                                    type="button"
                                    class="btn btn-success"
                                    @click="goToPermissions(user)"
                                    style="width:100%"
                                >
                                    <b-icon icon="lock-fill" aria-hidden="true"></b-icon>Permissions
                                </b-button>
                            </center>
                        </div>
                    </b-card>
                </div>

                <br />
            </div>
        </b-card-group>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            users: []
        };
    },
    methods: {
        goToPermissions(user) {
            this.$router.push({name:"PermissionPage", params:{email:user.email}});
        }
    },
    created() {
        axios.get("/admin/privilege").then(response => {
            this.users = response.data;
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