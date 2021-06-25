import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import App from 'pages/App.vue'
import store from 'store/store'
import router from 'router/router'
import {connect} from 'util/ws'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from "@sentry/vue"
import { Integrations } from "@sentry/tracing"

Sentry.init({
    Vue,
    dsn: "https://87c1803232414dd5a06c038f1e54598a@o850203.ingest.sentry.io/5817093",
    integrations: [new Integrations.BrowserTracing()],

    // Set tracesSampleRate to 1.0 to capture 100%
    // of transactions for performance monitoring.
    // We recommend adjusting this value in production
    tracesSampleRate: 1.0,
})

Sentry.configureScope(scope =>
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    })
)

if(profile){
    connect()
}

Vue.use(Vuetify)

new Vue({
    store,
    router,
    render: a => a(App),
    vuetify: new Vuetify()
}).$mount("#app")