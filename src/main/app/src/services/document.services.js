import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getDocuments = () => {
    return axios.request({
        method : 'get',
        url    : '/api/document',
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        let documentList = res.data.map(document => ({
            id       : document.id,
            userId   : document.userId,
            name     : document.name,
            createdAt: document.createdAt,
            updatedAt: document.updatedAt
        }))

        return new Promise((resolve, reject) => {
            resolve(documentList)
        })
    }).catch((error) => {
        console.log(error)
    })
}

export const documentServices = {
    getDocuments
}