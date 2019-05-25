import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getUserFiles = () => {
    return axios.request({
        method : 'get',
        url    : '/api/library/userfiles',
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        let userFileList = res.data.map(userFile => ({
            id       : userFile.id,
            userId   : userFile.userId,
            name     : userFile.name,
            createdAt: userFile.createdAt,
            updatedAt: userFile.updatedAt
        }))

        return new Promise((resolve, reject) => {
            resolve(userFileList)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const getTasks = (userFileId) => {
    return axios.request({
        method : 'get',
        url    : `/api/library/tasks?userFileId=${userFileId}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        let taskList = res.data.map(task => ({
            id        : task.id,
            userFileId: task.userFileId,
            tessdataId: task.tessdataId,
            createdAt : task.createdAt,
            updatedAt : task.updatedAt
        }))

        return new Promise((resolve, reject) => {
            resolve(taskList)
        })
    }).catch((error) => {
        console.log(error)
    })
}

export const fileServices = {
    getUserFiles,
    getTasks
}