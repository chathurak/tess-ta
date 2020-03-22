import axios                from 'axios/index'
import {ACCESS_TOKEN, USER} from '../constants/auth.constants'

// const signUp = (user) => {
//     return axios.request({
//         method : 'post',
//         url    : '/api/auth/signup',
//         data   : JSON.stringify(user),
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     })
// }

// const signIn = (email, password) => {
//     let loginRequest = {
//         usernameOrEmail: email,
//         password
//     }
//
//     return axios.request({
//         method : 'post',
//         url    : '/api/auth/signin',
//         data   : JSON.stringify(loginRequest),
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     })
//         .then(res => {
//             localStorage.setItem(ACCESS_TOKEN, res.data.accessToken)
//             return getCurrentUser()
//         })
// }

const signOut = () => {
    localStorage.removeItem(ACCESS_TOKEN)
    localStorage.removeItem(USER)
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
    signOut,
    updateAccessToken,
}
