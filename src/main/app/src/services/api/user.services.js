import axios                from 'axios'
import {ACCESS_TOKEN, USER} from '../../constants'

const signUp = (user) => {
    return axios.request({
        method : 'post',
        url    : '/api/auth/signup',
        data   : JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

const signIn = (email, password) => {
    let loginRequest = {
        usernameOrEmail: email,
        password       : password
    }

    return axios.request({
        method : 'post',
        url    : '/api/auth/signin',
        data   : JSON.stringify(loginRequest),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            localStorage.setItem(ACCESS_TOKEN, res.data.accessToken)
            return getCurrentUser()
        })
}

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

const getAll = () => {
    // const requestOptions = {
    //     method : 'GET',
    //     headers: authHeader()
    // }
    //
    // return fetch(`/user`, requestOptions).then(handleResponse)
}

const getById = (id) => {
    // return axios.request({
    //     method : 'get',
    //     url    : '/api/user/',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     }
    // })
    // .then(handleResponse)
}

const update = () => {
    // const requestOptions = {
    //     method : 'PUT',
    //     headers: {...authHeader(), 'Content-Type': 'application/json'},
    //     body   : JSON.stringify(user)
    // }
    //
    // return fetch(`/users/${user.id}`, requestOptions).then(handleResponse)
}

const _delete = () => {
    // const requestOptions = {
    //     method : 'DELETE',
    //     headers: authHeader()
    // }
    //
    // return fetch(`/users/${id}`, requestOptions).then(handleResponse)
}

const handleResponse = response => {
    const data = response.data
    if (!response.ok) {
        // Auto logout if 401 response returned from api
        if (response.status === 401) {
            signOut()
            window.location.reload(true)
        }

        const error = (data && data.message) || response.statusText
        return Promise.reject(error)
    }

    return response
}

export const userServices = {
    signIn,
    signOut,
    signUp,
    getAll,
    getById,
    update,
    delete: _delete
}
