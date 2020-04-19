import axios                from 'axios/index'
import {ACCESS_TOKEN, USER} from '../constants/auth.constants'

const signOut = () => {

}

const getCurrentUser = () => {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject('No access token set.')
    }

    return axios.request({
        method : 'get',
        url    : '/api/user/me',
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    })
        .then(res => {
            let user = res.data
            localStorage.setItem(USER, JSON.stringify(user))
            return user
        })
        .catch(error => {
            console.log(error)
            return Promise.reject(error)
        })
}

export const userServices = {
    signOut,
    getCurrentUser
}
