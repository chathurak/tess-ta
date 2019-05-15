import {combineReducers} from 'redux'
import {alertReducer}    from '../components/alert/duck'
import {signInReducer}   from '../pages/signin/duck'
import {signUpReducer}   from '../pages/signup/duck'

const rootReducer = combineReducers({
    signInReducer,
    signUpReducer,
    alertReducer
})

export default rootReducer