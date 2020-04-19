import DateFnsUtils                        from '@date-io/date-fns'
import {ThemeProvider as MuiThemeProvider} from '@material-ui/core/styles'
import {MuiPickersUtilsProvider}           from '@material-ui/pickers'
import React                               from 'react'
import ReactDOM                            from 'react-dom'
import App                                 from './App'
import './index.sass'
import * as serviceWorker                  from './serviceWorker'
import {theme}                             from './styles/theme'
import {BrowserRouter}                     from 'react-router-dom'

ReactDOM.render(
    <MuiThemeProvider theme={theme}>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
        </MuiPickersUtilsProvider>
    </MuiThemeProvider>,
    document.getElementById('root'))

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about httpService workers: https://bit.ly/CRA-PWA
serviceWorker.unregister()
