<template>
    <div class="container" style="width:90%">

        <br>

        <table class="table table-hover" style="background:#efefef; border: 3px solid #4c4c4c">
            <thead>
                <tr>
                <th colspan="2" style="color:#4c4c4c">
                    <h4>Messages</h4>
                </th>
                </tr>
            </thead>
        </table>


        

        <div class="row" style="width:auto">

            <div class="col-3">

                <b-list-group style="max-width: 330px;" >
                     <b-list-group-item class="d-flex align-items-center" style="color:#4c4c4c">
                       <h4> People </h4>
                    </b-list-group-item>
                    
                    <b-list-group-item  v-for="user in this.people" :key="user.id"  class="d-flex align-items-center" href="#" @click="showMessageBoard(user)">
                        <b-avatar class="mr-3"></b-avatar>
                        <span class="mr-auto"> {{user}} </span>
                    </b-list-group-item>
               
                </b-list-group>

            </div>


            <div class="col-9">
                <div class="container" v-if="showboard">

                    <b-card 
                        style="max-width:100%;"
                        class="mb-1"
                    >

                    <div class="modal-header" style="border:none;margin-bottom: -20px;">
                        <h4 class="modal-title" style="color:#4c4c4c">{{receiver}}</h4>
                        <button type="button" class="close" @click="closeBoard()" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>                 
                    </div>
                     <hr>

                    
                    <b-card style="overflow-y:scroll;position: relative; height:800px; border:none " >

                         <!-- OVDE PREPISkA -->
                    
                        <!--  style="max-width:100%; border-color: border: 2px solid #dedede; background-color:#E0E0E0;" -->

                        
                        <span v-for="message in this.conversation" :key="message.id">

                            <b-card border-variant="dark" v-if="receiver === message.receiver"
                                style="max-width:100%; height:30%; border-color: border: 2px solid #dedede; background-color: #f1f1f1; margin-left:2rem; border-top-left-radius:50px; border-top-right-radius:50px;border-bottom-left-radius:50px"
                                class="mb-3"
                            >
                            
                                <b-card-header class="text-right" >
                                    
                                    <b-avatar class="mr-3"></b-avatar>
                                    <span class="mr-auto">{{message.sender}}</span>


                                </b-card-header>

                                <b-card-body>
                                    <b-card-text>
                                        {{message.body}}
                                    </b-card-text>
                                    <div class="modal-footer" style="border-top: none;">
                                        <span class="time-left" style="color: #aaa;" >  {{changeDate(message.date)}} </span>
                                    </div>
                                </b-card-body>

    
                            </b-card>

                            
                            <b-card border-variant="dark" v-else
                                style="max-width:100%; height:30%; border-color: border: 2px solid #dedede; background-color:#E0E0E0;margin-right:2rem; border-top-left-radius:50px; border-top-right-radius:50px;border-bottom-right-radius:50px"
                                class="mb-3"
                            >
                            
                                <b-card-header class="text-left" >
                                    
                                    <b-avatar class="mr-3" ></b-avatar>
                                    <span class="mr-auto" >{{message.sender}}</span>

                                </b-card-header>

                                <b-card-body>
                                    <b-card-text>
                                        {{message.body}}
                                    </b-card-text>
                                    <div class="modal-footer" style="border-top: none;">
                                        <span class="time-left" style="color: #aaa;"> {{changeDate(message.date)}} </span>
                                    </div>
                                </b-card-body>

    
                            </b-card>

                    </span>
                    
                    </b-card>


                    <div class="modal-footer" style="border-top: 1px transparent #686868;">

                        <b-form-textarea v-model="body" rows="5" no-resize >

                        </b-form-textarea>
                        <b-button @click="sendMessage()"> Send </b-button>
                        
                    </div>

                </b-card>

            

                </div>

            </div>
        
        </div>

    
    </div>


</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
            showboard:false,
            conversation:[],
            people:[],
            receiver:"",
            body:""
        }
    },

   
    methods:{

        showMessageBoard(user){
            this.getConversation(user);
            this.receiver = user;
            this.showboard = true;
        },
        closeBoard(){
            this.showboard = false;
            this.receiver = "";
        },
        getPeople(){

             axios
                .get("/community/message/people")
                .then(response => {
                    this.people = response.data;
                });

        },
        getConversation(user){

             axios
                .get("/community/message/"+user)
                .then(response => {
                    this.conversation = response.data;
                });

        },
        sendMessage(){
            axios
                .post("/community/message",{
                    'receiver':this.receiver,
                    'body':this.body,
                })
                .then(response => {
                    this.conversation.push(response.data);
                    this.body = "";
                });
        },
        changeDate(date){
            let s = date.toString().split("T");
            let s1 = s[1].split(".");
            let s2 = s[0].split("-");
            return "Sent: "+s1[0]+" "+s2[2]+"."+s2[1]+"."+s2[0]+".";

        }

    },

    created(){
        this.getPeople();
    },
}
</script>

<style scoped>



</style>