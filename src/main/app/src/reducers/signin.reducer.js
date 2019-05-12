import {USER, userConstants} from '../constants'

let user           = JSON.parse(localStorage.getItem(USER))
const initialState = user ? {loggedIn: true, user} : {}

export function signin(state = initialState, action) {
    switch (action.type) {
        case userConstants.SIGNIN_REQUEST:
            return {
                loggingIn: true,
                user     : action.user
            }
        case userConstants.SIGNIN_SUCCESS:
            return {
                loggedIn: true,
                user    : action.user
            }
        case userConstants.SIGNIN_FAILURE:
            return {}
        case userConstants.SIGNOUT:
            return {}
        default:
            return state
    }
}
