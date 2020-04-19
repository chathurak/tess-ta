import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const process = (taskId) => {
    return axios.request({
        method : 'get',
        url    : `/api/grammar/process?taskId=${taskId}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        console.log(res.data) // TODO: temp
        let result = {
            input : res.data.input,
            output: res.data.output
        }

        return new Promise((resolve, reject) => {
            resolve(result)
        })
    }).catch((error) => {
        console.log(error)
    })
}


export const grammarServices = {
    process
}
