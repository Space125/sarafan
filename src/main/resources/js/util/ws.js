import {Stomp} from '@stomp/stompjs'
import SockJS from 'sockjs-client'

let stompClient = null;
const handlers = []

export function connect() {
    stompClient = Stomp.over(() => {
        return new SockJS('/gs-guide-websocket')
    })
    stompClient.debug = () => { }
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendMessage(message) {
    stompClient.send("/app/changeMessage", {}, JSON.stringify(message))
}