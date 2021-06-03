import Vue from 'vue'
import VueRouter from 'vue-router'
import Auth from 'pages/Auth.vue'
import MessagesList from 'pages/MessageList.vue'

Vue.use(VueRouter)


const routes = [
    {path: '/auth', component: Auth},
    {path: '/', component: MessagesList},
]
export default new VueRouter({
        mode: 'history',
        routes
    }
)