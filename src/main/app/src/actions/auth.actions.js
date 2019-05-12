import {userConstants} from '../constants'
import {history}       from '../helpers'
import {userServices}  from '../services'
import {alertActions}  from './alert.actions'

const signIn = (username, password) => {
    return dispatch => {
        dispatch(request({username}))

        userServices.signIn(username, password)
            .then(
                user => {
                    dispatch(success(user))
                    history.push('/')
                },
                error => {
                    dispatch(failure(error.toString()))
                    dispatch(alertActions.error(error.toString()))
                }
            )
    }

    function request(user) {
        return {type: userConstants.SIGNIN_REQUEST, user}
    }

    function success(user) {
        return {type: userConstants.SIGNIN_SUCCESS, user}
    }

    function failure(error) {
        return {type: userConstants.SIGNIN_FAILURE, error}
    }
}

const signOut = () => {
    userServices.signOut()
    return {type: userConstants.SIGNOUT}
}

const signUp = (user) => {
    return dispatch => {
        dispatch(request(user))

        userServices.signUp(user)
            .then(
                user => {
                    dispatch(success())
                    history.push('/signin')
                    dispatch(alertActions.success('Registration successful'))
                },
                error => {
                    dispatch(failure(error.toString()))
                    dispatch(alertActions.error(error.toString()))
                }
            )
    }

    function request(user) {
        return {type: userConstants.SIGNUP_REQUEST, user}
    }

    function success(user) {
        return {type: userConstants.SIGNUP_SUCCESS, user}
    }

    function failure(error) {
        return {type: userConstants.SIGNUP_FAILURE, error}
    }
}

export const authActions = {
    signIn,
    signOut,
    signUp
}