import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getTasksSummary = () => {
    // TODO: Load from the server
    // GET: '/api/reports'
    return new Promise((resolve, reject) => {
        resolve([
            {name: 'Task 01', uv: 400, pv: 2400, amt: 2400},
            {name: 'Task 02', uv: 300, pv: 2400, amt: 2400},
            {name: 'Task 03', uv: 50, pv: 2400, amt: 2400},
            {name: 'Task 03', uv: 600, pv: 2400, amt: 2400}
        ])
    })
}

const saveToDrive = (confusionMatrix, confusionSummary, diff) => {
    return axios.request({
        method : 'post',
        url    : `/api/reports?confusionMatrix=${confusionMatrix}&confusionSummary=${confusionSummary}&diff=${diff}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        return new Promise((resolve, reject) => {
            resolve(res)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const download = (confusionMatrix, confusionSummary, diff) => {
    return axios.request({
        method : 'get',
        url    : `/api/reports?confusionMatrix=${confusionMatrix}&confusionSummary=${confusionSummary}&diff=${diff}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        return new Promise((resolve, reject) => {
            resolve(res)
        })
    }).catch((error) => {
        console.log(error)
    })
}

export const reportServices = {
    saveToDrive,
    download,
    getTasksSummary
}
