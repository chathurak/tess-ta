import {userServices}  from '../services'
import {userConstants} from './user.constants'

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
    getAll,
    delete: _delete
}