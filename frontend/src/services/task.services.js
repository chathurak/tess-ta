import axios                          from 'axios/index'
import {ACCESS_TOKEN, G_ACCESS_TOKEN} from '../constants/auth.constants'

const getTasks = async (documentId) => {
    console.log(documentId)
    let res = axios.request({
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
    return res;
}

const scheduleTask = (documentId) => {
    let accessToken = localStorage.getItem(G_ACCESS_TOKEN)
    return axios.request({
        method : 'post',
        url    : '/api/task/schedule',
        data   : JSON.stringify({accessToken, documentId}),
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    })
}

export const taskServices = {
    getTasks,
    scheduleTask
}
