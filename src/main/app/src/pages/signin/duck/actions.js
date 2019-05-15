import {actions as alertActions} from '../../../components/alert/duck'
import {history}                 from '../../../helpers'
import {userServices}            from '../../../services'
import {types}                   from './types'

const signIn = (username, password) => {
    const request = (user) => {
        return {type: types.SIGNIN_REQUEST, user}
    }

    const success = (user) => {
        return {type: types.SIGNIN_SUCCESS, user}
    }

    const failure = (error) => {
        return {type: types.SIGNIN_FAILURE, error}
    }

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
}

const signOut = () => {
    userServices.signOut()
    return {type: types.SIGNOUT}
}

export const actions = {
    signIn,
    signOut
}