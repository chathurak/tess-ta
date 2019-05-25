import {combineReducers}   from 'redux'
import {alertReducer}      from '../components/alert/duck'
import {taskPickerReducer} from '../pages/dashboard/components/taskpicker/duck'
import {signInReducer}     from '../pages/signin/duck'
import {signUpReducer}     from '../pages/signup/duck'

const rootReducer = combineReducers({
    alertReducer,

    signInReducer,
    signUpReducer,

    taskPickerReducer
})

export default rootReducer