import axios                from 'axios/index'
import {ACCESS_TOKEN, USER} from '../constants/auth.constants'

// const signIn = () => {
//     return axios.request({
//         method : 'get',
//         url    : 'http://localhost:4000/oauth2/authorize/google?redirect_uri=http://localhost:4000/oauth2/callback/google',
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     })
//         .then(res => {
//             console.log(res)
//         })
// }

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

const updateAccessToken = (accessToken, imageUrl) => {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject('No access token set.')
    }

    return axios.request({
        method : 'post',
        url    : '/api/auth/token',
        data   : JSON.stringify({accessToken, imageUrl}),
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    })
}

export const userServices = {
    // signIn,
    signOut,
    getCurrentUser,
    updateAccessToken,
}
