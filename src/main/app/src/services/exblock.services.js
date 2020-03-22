import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getLetters = () => {
    return axios.request({
        method : 'get',
        url    : `/api/exblock`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        let result = res.data;
        result.sort();

        return new Promise((resolve, reject) => {
            resolve(result)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const addLetter = (letter) => {
    return axios.request({
        method : 'post',
        url    : `/api/exblock?letter=${letter}`,
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

const updateLetter = (oldLetter, newLetter) => {
    return axios.request({
        method : 'put',
        url    : `/api/exblock?oldLetter=${oldLetter}&newLetter=${newLetter}`,
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

const deleteLetter = (letter) => {
    return axios.request({
        method : 'delete',
        url    : `/api/exblock?letter=${letter}`,
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


export const exblockServices = {
    getLetters,
    addLetter,
    updateLetter,
    deleteLetter
}