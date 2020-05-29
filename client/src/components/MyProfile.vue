<template>
    <div>
        <b-navbar type="light" variant="light">
            <div class="container">
                <b-navbar-nav style="margin-left:0.9rem">
                    <b-nav-item-dropdown text="My Ads">
                        <b-dropdown-item data-toggle="modal" data-target="#newad">
                            <b-icon icon="plus-circle-fill" aria-hidden="true" variant="dark"></b-icon> New Ad
                        </b-dropdown-item>
                        <b-dropdown-item @click="showClientAds()">
                            <b-icon icon="newspaper" aria-hidden="true" variant="dark"></b-icon> All Ads
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown text="My Cars">
                        <b-dropdown-item data-toggle="modal" data-target="#newcar">
                            <b-icon icon="plus-circle-fill" aria-hidden="true" variant="dark"></b-icon> New Car
                        </b-dropdown-item>
                        <b-dropdown-item @click="showClientCars()">
                            <b-icon icon="tools" aria-hidden="true" variant="dark"></b-icon> All Cars
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </div>
        </b-navbar>
        <NewCarForm/>
        <NewAdForm/>
        <ClientCars v-if="this.boolcars"/>
        <ClientAds v-if="this.boolads"/>

     
    </div>
</template>

<script>
import NewCarForm from './NewCarForm'
import NewAdForm from './NewAdForm'
import ClientCars from './ClientCars'
import ClientAds from './ClientAds'


export default {
    data() {
        return {
            boolcars:false,
            boolads:false,
        };
    },
    components:{
        NewCarForm,
        NewAdForm,
        ClientCars,
        ClientAds,
    },
    methods: {
        showClientCars(){
            this.boolcars = true;
            this.boolads = false;
        },
        showClientAds(){
            this.boolads = true;
            this.boolcars = false;
        }
    },
    created() {
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
