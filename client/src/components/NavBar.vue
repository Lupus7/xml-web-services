<template>
    <div>
        <b-navbar toggleable="lg" class="navbar navbar-dark bg-dark" style="height:70px">
            <div class="container">
                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav>
                        <b-link
                            class="nav-link active"
                            type="button"
                            v-on:click="homepage()"
                            href="#"
                        >
                            <b-icon icon="house-door-fill" aria-hidden="true"></b-icon> Homepage
                        </b-link>
                        <b-link
                            class="nav-link active"
                            type="button"
                            v-on:click="searchPage()"
                            href="#"
                        >
                            <b-icon icon="newspaper" aria-hidden="true"></b-icon> Adds
                        </b-link>
                    </b-navbar-nav>

                    <b-navbar-nav class="ml-auto">
                        <b-link
                            v-if="this.show"
                            right
                            class="nav-link active"
                            type="button"
                            v-b-modal.login-modal
                        >
                            <b-icon icon="person-check-fill" aria-hidden="true"></b-icon> Log in
                        </b-link>
                        <b-link
                            v-if="this.show"
                            class="nav-link active"
                            type="button"
                            v-on:click="register()"
                            href="#"
                        >
                            <b-icon icon="person-plus-fill" aria-hidden="true"></b-icon> Register
                        </b-link>
                        
                        <b-link
                            v-if="!this.show"
                            class="nav-link active"
                            type="button"
                            v-on:click="profile()"
                            href="#"
                        >
                            <b-icon icon="person-bounding-box" aria-hidden="true"></b-icon> My Profile
                        </b-link>

                        <b-link
                            v-if="!this.show"
                            class="nav-link active"
                            type="button"
                            v-on:click="logout()"
                            href="#"
                        >
                            <b-icon icon="person-dash-fill" aria-hidden="true"></b-icon> Log out
                        </b-link>
                    </b-navbar-nav>
                </b-collapse>
            </div>
        </b-navbar>
        
        <b-modal id="login-modal" body-bg-variant="light" header-bg-variant="light" footer-bg-variant="light" body-text-variant="dark" header-text-variant="dark"  >
            <template  v-slot:modal-title >
                <div style="margin-left:12.0rem" class="d-block text-center">
                    <h3>Log in</h3>
                </div>
            </template>

            <div class="form-group" >
                <center>
                    <label class>Email</label>

                    <b-input-group style="border-radius:20px;width:90%">
                        <b-input-group-prepend is-text>
                            <b-icon icon="envelope-fill"></b-icon>
                        </b-input-group-prepend>
                        <b-input
                            placeholder="Enter your email..."
                            v-model="email"
                            type="text"
                            class="form-control"
                            required
                        />
                    </b-input-group>
                </center>
            </div>
            <div class="form-group">
                <center>
                    <label>Password</label>

                    <b-input-group style="border-radius:20px;width:90%">
                        <b-input-group-prepend is-text>
                            <b-icon icon="lock-fill"></b-icon>
                        </b-input-group-prepend>
                        <b-input
                            placeholder="Enter your password..."
                            v-model="password"
                            type="password"
                            class="form-control"
                            required
                        />
                    </b-input-group>
                </center>
            </div>
            <template v-slot:modal-footer>
                <div class="w-100">
                    <b-button
                        style="margin-left:2rem;border-radius:20px;;width:150px;height:45px;"
                        class="float-left"
                        variant="outline-danger"
                        @click="register()"
                    >
                        <b-icon icon="person-plus-fill" aria-hidden="true"></b-icon> Register
                    </b-button>
                    <b-button
                        style="margin-right:2rem;border-radius:20px;width:150px;height:45px;"
                        class="float-right"
                        variant="danger"
                        @click="login()"
                    >
                        <b-icon icon="person-check-fill" aria-hidden="true"></b-icon> Log in
                    </b-button>
                </div>
            </template>
        </b-modal>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            email: "",
            password: "",
            show: true
        };
    },
    methods: {
        searchPage() {
            this.$router.push("/search");
        },
        homepage() {},
        login() {
            if (this.email.length > 0 && this.password.length > 0) {
                axios
                    .post("/user/login", {
                        username: this.email,
                        password: this.password
                    })
                    .then(response => {
                        localStorage.setItem("accessToken", response.data);
                        this.checkToken();
                        location.reload();
                    });
            }
        },
        register() {},
        logout() {
            localStorage.setItem("accessToken", "");
            this.checkToken();
            location.reload();
        },
        checkToken() {
            const token = localStorage.getItem("accessToken");
            if (token === null || token === "") this.show = true;
            else this.show = false;
            console.log(this.show);
        },
        profile(){
            this.$router.push("/myprofile");
        }
    },
    created() {
        this.checkToken();
    }
};
</script>

<style scoped>
</style>
