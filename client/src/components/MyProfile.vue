<template>
    <div>
        <b-navbar type="light" variant="light">
            <div class="container">
                <b-navbar-nav style="margin-left:0.9rem">
                    <b-nav-item-dropdown text="My Ads">
                        <b-dropdown-item data-toggle="modal" data-target="#newad">
                            <b-icon icon="plus-circle-fill" aria-hidden="true" variant="dark"></b-icon> New Ad
                        </b-dropdown-item>
                        <b-dropdown-item>
                            <b-icon icon="newspaper" aria-hidden="true" variant="dark"></b-icon> All Ads
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown text="My Cars">
                        <b-dropdown-item data-toggle="modal" data-target="#newcar">
                            <b-icon icon="plus-circle-fill" aria-hidden="true" variant="dark"></b-icon> New Car
                        </b-dropdown-item>
                        <b-dropdown-item>
                            <b-icon icon="tools" aria-hidden="true" variant="dark"></b-icon> All Cars
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </div>
        </b-navbar>
        <NewCarForm/>
        <NewAdForm/>

     
    </div>
</template>

<script>
import NewCarForm from './NewCarForm'
import NewAdForm from './NewAdForm'

export default {
    data() {
        return {
         
        };
    },
    components:{
        NewCarForm,
        NewAdForm
    },
    methods: {
      
    },
    created() {
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
