<template>

    <div class="container" style=" width:90%">
        <br>
        <b-card-group deck class="row" >
            <div v-for="comment in this.comments" :key="comment.id"  class="col-12 col-md-3 col-lg-4" >

                <b-card  img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>

                    <b-card-title>Auto</b-card-title>

                    <b-card-sub-title class="mb-2">User: {{comment}}</b-card-sub-title>
                    <b-card-sub-title class="mb-3">Comment Date: {{comment}}</b-card-sub-title>


                    <b-card-text>
                        This is a wider card with supporting text below as a natural lead-in to additional content.
                        This content is a little bit longer.
                    </b-card-text>
               
                    <div class="modal-footer">
                        <div class="row">
                            <div class="col">
                                <b-button type="button" class="btn btn-success"  @click="approveComment()" style="width:120%" > <b-icon icon="check" aria-hidden="true"> </b-icon> Approve</b-button>
                            </div>
                            <div class="col">
                                <b-button type="button" class="btn btn-danger"  @click="rejectComment()" style="width:120%" > <b-icon icon="x" aria-hidden="true"> </b-icon> Reject</b-button>
                            </div>
                        </div>
                    </div>
                </b-card>

                <br>
            </div>

        </b-card-group>
        
        
    </div>
    
</template>

<script>
export default {
    data(){
        return{
            comments:[1,2,3,4,5,6,7,8,9,10],
        }
    },
    methods:{
        approveComment(){

        },
        rejectComment(){

        }

    },
    created(){

    }
}
</script>

<style scoped>

.modal-footer{
  border-top: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}

.modal-header{
  border-bottom: 1px solid #5f5f5f;
  width: 100%;
  font-size: 20px;
  font-size: 3vh
}

</style>