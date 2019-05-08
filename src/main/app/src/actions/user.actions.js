import {userConstants} from '../constants'
import {history}       from '../helpers'
import {userServices}  from '../services'
import {alertActions}  from './'

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
                    history.push('/login')
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

const getAll = () => {
    return dispatch => {
        dispatch(request())

        userServices.getAll()
            .then(
                users => dispatch(success(users)),
                error => dispatch(failure(error.toString()))
            )
    }

    function request() {
        return {type: userConstants.GETALL_REQUEST}
    }

    function success(users) {
        return {type: userConstants.GETALL_SUCCESS, users}
    }

    function failure(error) {
        return {type: userConstants.GETALL_FAILURE, error}
    }
}

const _delete = (id) => {
    return dispatch => {
        dispatch(request(id))

        userServices.delete(id)
            .then(
                user => dispatch(success(id)),
                error => dispatch(failure(id, error.toString()))
            )
    }

    function request(id) {
        return {type: userConstants.DELETE_REQUEST, id}
    }

    function success(id) {
        return {type: userConstants.DELETE_SUCCESS, id}
    }

    function failure(id, error) {
        return {type: userConstants.DELETE_FAILURE, id, error}
    }
}

export const userActions = {
    signIn,
    signOut,
    signUp,
    getAll,
    delete: _delete
}