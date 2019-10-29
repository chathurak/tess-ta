import {USER}  from '../../../constants/auth.constants'
import {types} from './types'

let user           = JSON.parse(localStorage.getItem(USER))
const initialState = user ? {loggedIn: true, user} : {}

export const signInReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.SIGNIN_REQUEST:
            return {
                loggingIn: true,
                user     : action.user
            }
        case types.SIGNIN_SUCCESS:
            return {
                loggedIn: true,
                user    : action.user
            }
        case types.SIGNIN_FAILURE:
            return {}
        case types.SIGNOUT:
            return {}
        default:
            return state
    }
}