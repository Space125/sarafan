import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from "../api/messages";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    getters: {
        sortedMessages: state => state.messages.sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, message) {
            state.messages = [
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id)
            state.messages = [
                ...state.messages.slice(0, index),
                message,
                ...state.messages.slice(index + 1)
            ]
        },
        removeMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id)
            if (index > -1) {
                state.messages = [
                    ...state.messages.slice(0, index),
                    ...state.messages.slice(index + 1)
                ]
            }
        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            const result = await messagesApi.add(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateMessageMutation', data)
            } else {
                commit('addMessageMutation', data)
            }
        },
        async updateMessageAction({commit, state}, message) {
            const result = await messagesApi.update(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateMessageMutation', data)
            }
        },
        async removeMessageAction({commit, state}, message) {
            const result = await messagesApi.remove(message.id)
                if (result.ok) {
                    const index = state.messages.findIndex(item => item.id === message.id)
                    if (index > -1)
                        commit('removeMessageMutation', message)
                }

        }
    }
})