import {actions as alertActions} from '../../../components/alert/duck'
import {history}                 from '../../../helpers'
import {userServices}            from '../../../services'
import {types}                   from './types'

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
        return {type: types.SIGNUP_REQUEST, user}
    }

    function success(user) {
        return {type: types.SIGNUP_SUCCESS, user}
    }

    function failure(error) {
        return {type: types.SIGNUP_FAILURE, error}
    }
}

export const actions = {
    signUp
}