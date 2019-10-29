import {types} from './types'

const initialState = {}

export const taskPickerReducer = (state = initialState, action) => {
    switch (action.type) {
        case types.SELECT_DOCUMENT:
            return {
                selectedDocument: action._document
            }
        case types.SELECT_TASK:
            return {
                selectedTask: action.task
            }
        default:
            return state
    }
}