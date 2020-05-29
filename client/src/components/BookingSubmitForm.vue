<template>
    <div>
        <!-- NEW BOOKING FORM -->
        <div class="modal fade" id="newbooking" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">New Booking</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <b-form>
                            <div>
                                <div class="form-group">
                                    <label>Place</label>
                                    <b-input type="text" class="form-control" />
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label>Start Date</label>
                                        <b-form-datepicker
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>End Date</label>
                                        <b-form-datepicker
                                            :date-format-options="{ year: 'numeric', month: '2-digit', day: '2-digit' }"
                                            locale="en"
                                        ></b-form-datepicker>
                                    </div>
                                </div>

                               
                            </div>
                        </b-form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" data-dismiss="modal">
                            <b-icon icon="check-circle" aria-hidden="true"></b-icon>Create Booking
                        </button>
                        <button
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
export default {
    data() {
        return {
        

        };
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
