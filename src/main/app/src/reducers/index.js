import {combineReducers} from 'redux'
import {alert}           from './alert.reducer'

import {signin} from './signin.reducer'
import {signup} from './signup.reducer'
import {users}  from './users.reducer'

const rootReducer = combineReducers({
    signup,
    signin,
    users,
    alert
})

export default rootReducer
