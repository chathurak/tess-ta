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
            id              : document.id,
            userId          : document.userId,
            name            : document.name,
            originalFileName: document.originalFileName,
            createdAt       : document.createdAt,
            updatedAt       : document.updatedAt
        }))

        return new Promise((resolve, reject) => {
            resolve(documentList)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const renameDocument = (documentId, newName) => {
    return axios.request({
        method : 'put',
        url    : `/api/document?documentId=${documentId}&newName=${newName}`,
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

const deleteDocument = (documentId) => {
    return axios.request({
        method : 'delete',
        url    : `/api/document?documentId=${documentId}`,
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

export const documentServices = {
    getDocuments,
    renameDocument,
    deleteDocument
}
