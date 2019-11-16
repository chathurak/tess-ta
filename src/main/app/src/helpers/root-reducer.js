import {combineReducers}     from 'redux'
import {alertReducer}        from '../components/alert/duck'
import {taskPickerReducer}   from '../components/taskpicker/duck'
import {scheduleTaskReducer} from '../pages/ocr/components/taskpanel/duck'
import {signInReducer}       from '../pages/signin/duck'
import {signUpReducer}       from '../pages/signup/duck'

const rootReducer = combineReducers({
    alertReducer,

    signInReducer,
    signUpReducer,

    taskPickerReducer,

    scheduleTaskReducer
})

export default rootReducer