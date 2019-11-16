import {types} from './types'

export const scheduleTaskReducer = (state = {}, action) => {
    switch (action.type) {
        case types.SCHEDULE_TASK_REQUEST:
            return {}
        case types.SCHEDULE_TASK_SUCCESS:
            return {}
        case types.SCHEDULE_TASK_FAILURE:
            return {}
        default:
            return state
    }
}
