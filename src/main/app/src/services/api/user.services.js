import axios from 'axios'

const signUp = (user) => {
    return axios({
        method : 'post',
        url    : '/api/users/signup',
        data   : JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

const signIn = (email, password) => {
    return axios({
        method : 'post',
        url    : '/api/users/signin',
        data   : JSON.stringify({email, password}),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(handleResponse)
        .then(user => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user))
            return user
        })
}

const signOut = () => {
    // Remove user from local storage
    localStorage.removeItem('user')
}

const getAll = () => {

}

const getById = () => {

}

const update = () => {

}

const _delete = () => {

}

const handleResponse = response => {
    return response.text().then(text => {
        const data = text && JSON.parse(text)
        if (!response.ok) {
            // Auto logout if 401 response returned from api
            if (response.status === 401) {
                signOut()
                window.location.reload(true)
            }

            const error = (data && data.message) || response.statusText
            return Promise.reject(error)
        }

        return data
    })
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

// function getAll() {
//     const requestOptions = {
//         method : 'GET',
//         headers: authHeader()
//     }
//
//     return fetch(`/user`, requestOptions).then(handleResponse)
// }

// function getById(id) {
//     const requestOptions = {
//         method : 'GET',
//         headers: authHeader()
//     }
//
//     return fetch(`/user/${id}`, requestOptions).then(handleResponse)
// }


// function update(user) {
//     const requestOptions = {
//         method : 'PUT',
//         headers: {...authHeader(), 'Content-Type': 'application/json'},
//         body   : JSON.stringify(user)
//     }
//
//     return fetch(`/users/${user.id}`, requestOptions).then(handleResponse)
// }

// function _delete(id) {
//     const requestOptions = {
//         method : 'DELETE',
//         headers: authHeader()
//     }
//
//     return fetch(`/users/${id}`, requestOptions).then(handleResponse)
// }
