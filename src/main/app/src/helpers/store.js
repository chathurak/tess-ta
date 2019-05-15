import {applyMiddleware, createStore} from 'redux'
import {createLogger}                 from 'redux-logger'
import thunkMiddleware                from 'redux-thunk'
import rootReducer                    from './root-reducer'

const loggerMiddleware = createLogger()

export const store = createStore(
    rootReducer,
    applyMiddleware(
        thunkMiddleware,
        loggerMiddleware
    )
)
