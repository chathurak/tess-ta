import {ACCESS_TOKEN} from '../../constants/auth.constants'

export const config = {
    url    : '/api/file',
    process: {
        url            : '/process',
        method         : 'POST',
        withCredentials: false,
        headers        : {
            'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        },
        timeout        : 7000,
        onload         : null,
        onerror        : null,
        ondata         : null
    },
    revert : '/revert',
    restore: '/restore/',
    load   : '/load/',
    fetch  : '/fetch/'
}
