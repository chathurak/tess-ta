import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getTasks = (documentId) => {
    return axios.request({
        method : 'get',
        url    : `/api/task?documentId=${documentId}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        let taskList = res.data.map(task => ({
            id          : task.id,
            name        : task.name,
            userFileId  : task.userFileId,
            tessdataId  : task.tessdataId,
            tessdataName: task.tessdataName,
            createdAt   : task.createdAt,
            updatedAt   : task.updatedAt
        }))

        return new Promise((resolve, reject) => {
            resolve(taskList)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const scheduleTask = (documentId) => {
    return axios.request({
        method : 'post',
        url    : '/api/task/schedule',
        data   : JSON.stringify({documentId}),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    })
}

export const taskServices = {
    getTasks,
    scheduleTask
}