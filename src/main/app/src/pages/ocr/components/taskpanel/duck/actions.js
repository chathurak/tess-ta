import {actions as alertActions} from '../../../../../components/alert/duck/actions'
import {taskServices}            from '../../../../../services'
import {types}                   from './types'

const scheduleTask = (documentId) => {
    const request = () => {
        return {type: types.SCHEDULE_TASK_REQUEST}
    }

    const success = () => {
        return {type: types.SCHEDULE_TASK_SUCCESS}
    }

    const failure = (error) => {
        return {type: types.SCHEDULE_TASK_FAILURE, error}
    }

    return dispatch => {
        dispatch(request())

        taskServices.scheduleTask(documentId)
            .then(
                _ => dispatch(success()),
                error => {
                    dispatch(failure(error.toString()))
                    dispatch(alertActions.error(error.toString()))
                }
            )
    }
}

export const actions = {
    scheduleTask
}