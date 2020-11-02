import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getWords = (type) => {
    console.log(type)
    return axios.request({
        method : 'get',
        url    : `/api/dictionary?type=${type}`,
        headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        }
    }).then((res) => {
        // console.log(res.data) // TODO: temp
        let result = res.data
        result.sort()

        return new Promise((resolve, reject) => {
            resolve(result)
        })
    }).catch((error) => {
        console.log(error)
    })
}

const addWord = (word,type) => {
    return axios.request({
        method : 'post',
        url    : `/api/dictionary?word=${word}&type=${type}`,
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

const updateWord = (oldWord, newWord) => {
    return axios.request({
        method : 'put',
        url    : `/api/dictionary?oldWord=${oldWord}&newWord=${newWord}`,
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

const deleteWord = (word) => {
    return axios.request({
        method : 'delete',
        url    : `/api/dictionary?word=${word}`,
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


export const dictionaryServices = {
    getWords,
    addWord,
    updateWord,
    deleteWord
}
