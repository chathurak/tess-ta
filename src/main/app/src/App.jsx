import {withStyles}                      from '@material-ui/core/styles'
import PropTypes                         from 'prop-types'
import React                             from 'react'
import {connect}                         from 'react-redux'
import {Redirect, Route, Router, Switch} from 'react-router-dom'
import styles                            from './App.styles'
import {actions as alertActions}         from './components/alert/duck'
import {history}                         from './helpers'
import SignIn                            from './pages/signin/SignIn'
import SignUp                            from './pages/signup/SignUp'
import {USER}                            from './constants/auth.constants'
import Header                            from './components/header/Header'
import Sidebar                           from './components/sidebar/Sidebar'
import Home                              from './pages/home/Home'
import Library                           from './pages/library/Library'
import Ocr                               from './pages/ocr/Ocr'
import Grammar                           from './pages/grammar/Grammar'
import Reports                           from './pages/reports/Reports'
import Settings                          from './pages/settings/Settings'
import CssBaseline                       from '@material-ui/core/CssBaseline'


class App extends React.Component {

    constructor(props) {
        super(props)

        const {dispatch} = this.props
        history.listen((location, action) => {
            // clear alertReducer on location change
            dispatch(alertActions.clear())
        })
    }

    render() {
        const {classes, alert} = this.props

        return (
            <div className={classes.root}>
                {alert.message && <div className={`alert ${alert.type}`}>{alert.message}</div>}
                <Router history={history}>
                    <div>
                        <Switch>
                            <Route path="/signin" component={SignIn}/>
                            <Route path="/signup" component={SignUp}/>
                            <Route path="/" render={props => (
                                localStorage.getItem(USER)
                                    ? <div className={classes.adminpanel}>
                                        <CssBaseline/>
                                        <Header/>
                                        <Sidebar/>
                                        <main className={classes.content}>
                                            <div className={classes.toolbar}/>
                                            <Route exact path='/home' component={Home}/>
                                            <Route exact path='/library' component={Library}/>
                                            <Route exact path='/ocr' component={Ocr}/>
                                            <Route exact path='/grammar' component={Grammar}/>
                                            <Route exact path='/reports' component={Reports}/>
                                            <Route exact path='/settings' component={Settings}/>
                                        </main>
                                    </div>
                                    : <Redirect to={{pathname: '/signin', state: {from: props.location}}}/>
                            )}/>
                        </Switch>
                    </div>
                </Router>
            </div>
        )
    }

}

App.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

function mapStateToProps(state) {
    const alert = state.alertReducer
    return {
        alert
    }
}

const styledComponent = withStyles(styles, {withTheme: true})(App)
export default connect(mapStateToProps)(styledComponent)
