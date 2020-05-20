<template>
    <div>
        <b-navbar
            toggleable="lg"
            class="navbar navbar-expand-lg  navbar-dark bg-dark"
            style="height:70px"
        >
            <div class="container">
                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav>
                        <b-link
                            class="nav-link active"
                            type="button"
                            v-on:click="homepage()"
                            href="#"
                        >
                            <b-icon
                                icon="house-door-fill"
                                aria-hidden="true"
                            ></b-icon>
                            Homepage
                        </b-link>
                        <b-link
                            class="nav-link active"
                            type="button"
                            v-on:click="adminPage()"
                            href="#"
                        >
                            <b-icon
                                icon="briefcase-fill"
                                aria-hidden="true"
                            ></b-icon>
                            Admin Panel</b-link
                        >
                    </b-navbar-nav>

                    <b-navbar-nav class="ml-auto">
                        <b-link
                            right
                            class="nav-link active"
                            type="button"
                            v-b-modal.login-modal
                            href="#"
                        >
                            <b-icon
                                icon="person-check-fill"
                                aria-hidden="true"
                            ></b-icon>
                            Log in</b-link
                        >
                        <b-link
                            class="nav-link active"
                            type="button"
                            v-on:click="logout()"
                            href="#"
                            ><b-icon
                                icon="person-dash-fill"
                                aria-hidden="true"
                            ></b-icon>
                            Log out</b-link
                        >
                    </b-navbar-nav>
                </b-collapse>
            </div>
        </b-navbar>
        <b-modal id="login-modal" title="Log in">
            <input
                placeholder="Enter your email..."
                v-model="email"
                type="text"
                class="form-control"
                required
            /><input
                placeholder="Enter your password..."
                v-model="password"
                type="password"
                class="form-control"
                style="margin-top: 10px;"
                required
            />
            <template v-slot:modal-footer>
                <div class="w-100">
                    <b-button
                        variant="danger"
                        class="float-right"
                        @click="login()"
                    >
                        <b-icon
                            icon="person-check-fill"
                            aria-hidden="true"
                        ></b-icon>
                        Log in
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
        };
    },
    methods: {
        homepage() {
            this.$router.push("/");
        },
        login() {
            if (this.email.length > 0 && this.password.length > 0) {
                axios
                    .post("/authentication/login", {
                        username: this.email,
                        password: this.password,
                    })
                    .then((response) => {
                        localStorage.setItem("accessToken", response.data);
                        location.reload();
                    });
            }
        },
        logout() {
            localStorage.setItem("accessToken", "");
            location.reload();
        },
        adminPage() {
            this.$router.push("/admin");
        },
    },
    created() {},
};
</script>
