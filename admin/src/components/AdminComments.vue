<template>
    <div class="container" style=" width:90%">
        <br />
        <b-card v-if="this.comments.length === 0" class="text-center" style="background:#DCDCDC		">
            <div>
               <h4 style="color:#696969		"> No more comments! </h4>
            </div>
        </b-card>

        <b-card-group deck class="row">
            <div
                v-for="comment in this.comments"
                :key="comment.id"
                class="col-12 col-md-3 col-lg-4"
            >
                <b-card v-bind:img-src="comment.carImage" img-alt="Image" img-top>
                    <b-card-title>{{comment.car}}</b-card-title>

                    <b-card-sub-title class="mb-2">User: {{comment.user}}</b-card-sub-title>
                    <b-card-sub-title class="mb-3">Comment Date: {{comment.commentCreated}}</b-card-sub-title>

                    <b-card-text>{{comment.bodyComment}}</b-card-text>

                    <div class="modal-footer">
                        <div class="row">
                            <div class="col">
                                <b-button
                                    type="button"
                                    class="btn btn-dark"
                                    @click="approveComment(comment)"
                                    style="width:120%"
                                >
                                    <b-icon icon="check" aria-hidden="true"></b-icon>Approve
                                </b-button>
                            </div>
                            <div class="col">
                                <b-button
                                    type="button"
                                    class="btn btn-danger"
                                    @click="rejectComment(comment)"
                                    style="width:120%"
                                >
                                    <b-icon icon="x" aria-hidden="true"></b-icon>Reject
                                </b-button>
                            </div>
                        </div>
                    </div>
                </b-card>

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
            comments: []
        };
    },
    methods: {
        approveComment(comment) {
            let url = "/admin/comment/" + comment.commentId;

            axios.put(url).then(response => {
                if (response.status === 200) {
                    this.$bvToast.toast(response.data, {
                        title: "Comment Approve",
                        variant: "success",
                        solid: true
                    });
                    this.fill();
                } else {
                    this.$bvToast.toast(response.data, {
                        title: "Comment Approve",
                        variant: "warning",
                        solid: true
                    });
                }
            });
        },
        rejectComment(comment) {
            let url = "/admin/comment/" + comment.commentId;

            axios.delete(url).then(response => {
                if (response.status === 200) {
                    this.$bvToast.toast(response.data, {
                        title: "Comment Reject",
                        variant: "success",
                        solid: true
                    });
                    this.fill();
                } else {
                    this.$bvToast.toast(response.data, {
                        title: "Comment Reject",
                        variant: "warning",
                        solid: true
                    });
                }
            });
        },
        fill() {
            axios.get("/admin/comment").then(response => {
                this.comments = response.data;
            });
        }
    },
    created() {
        this.fill();
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