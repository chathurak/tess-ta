import axios                from 'axios/index'
import {ACCESS_TOKEN, USER} from '../constants/auth.constants'

export const LibraryService = {
    getUserFiles,
    getTasks
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
}

function getTasks(userFileName) {
    return axios.request({
        method : 'get',
        url    : '/api/library/tasks?userfilename=' + userFileName,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    })
}