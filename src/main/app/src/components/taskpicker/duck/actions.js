import {types} from './types'

const selectDocument = (_document) => {
    return {type: types.SELECT_DOCUMENT, _document}
}

const selectTask = (task) => {
    return {type: types.SELECT_TASK, task}
}

export const actions = {
    selectDocument,
    selectTask,
}