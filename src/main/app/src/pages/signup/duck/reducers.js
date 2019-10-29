import {types} from './types'

export const signUpReducer = (state = {}, action) => {
    switch (action.type) {
        case types.SIGNUP_REQUEST:
            return {registering: true}
        case types.SIGNUP_SUCCESS:
            return {}
        case types.SIGNUP_FAILURE:
            return {}
        default:
            return state
    }
}
