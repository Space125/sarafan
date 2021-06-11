import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from 'api/messages'
import commentApi from 'api/comment'


Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages,
        profile,
        ...frontendData
    },
    getters: {
        sortedMessages: state => (state.messages || []).sort((a, b) => -(a.id - b.id))
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
        },
        addCommentMutation(state, comment) {
            const index = state.messages.findIndex(item => item.id === comment.message.id)
            const message = state.messages[index]

            if (!(message.comments || []).find(item => item.id === comment.id)) {
                state.messages = [
                    ...state.messages.slice(0, index),
                    {
                        ...message,
                        comments: message.comments === null ?
                            [comment] : [...message.comments,
                                comment
                            ]
                    },
                    ...state.messages.slice(index + 1)
                ]
            }
        },
        addMessagePageMutation(state, messages) {
            const targetMessages = state.messages
                .concat(messages)
                .reduce((res, val) => {
                    console.log('val = ' + val)
                    res[val.id] = val
                    return res
                }, {})
            state.messages = Object.values(targetMessages)
        },
        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.currentPage = currentPage
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

        },
        async addCommentAction({commit, state}, comment) {
            const response = await commentApi.add(comment)
            const data = await response.json()
            commit('addCommentMutation', data)
        },
        async loadPageAction({commit, state}) {
            const response = await messagesApi.page(state.currentPage + 1)
            const data = await response.json()
            commit('addMessagePageMutation', data.messages)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
        }
    }
})