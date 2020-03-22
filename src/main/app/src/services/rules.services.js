import axios          from 'axios/index'
import {ACCESS_TOKEN} from '../constants/auth.constants'

const getRules = (ruleType) => {
    return axios.request({
        method : 'get',
        url    : `/api/rules/${ruleType.toLowerCase()}`,
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

const addRule = (rule, ruleType) => {
    return axios.request({
        method : 'post',
        url    : `/api/rules/${ruleType.toLowerCase()}?rule=${rule}`,
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

const updateRule = (oldRule, newRule, ruleType) => {
    return axios.request({
        method : 'put',
        url    : `/api/rules/${ruleType.toLowerCase()}?oldRule=${oldRule}&newRule=${newRule}`,
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

const deleteRule = (rule, ruleType) => {
    return axios.request({
        method : 'delete',
        url    : `/api/rules/${ruleType.toLowerCase()}?rule=${rule}`,
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


export const rulesServices = {
    getRules,
    addRule,
    updateRule,
    deleteRule
}