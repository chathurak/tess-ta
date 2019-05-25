import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

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

export const taskServices = {
    getTasks
}