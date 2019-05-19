import axios                from 'axios/index'
import {ACCESS_TOKEN, USER} from '../constants/auth.constants'

export const LibraryService = {
    getUserFiles
}


function getUserFiles() {

    return axios.request({
        method : 'get',
        url    : '/api/library/userfiles',
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

