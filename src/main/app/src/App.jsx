import {withStyles}                      from '@material-ui/core/styles'
import PropTypes                         from 'prop-types'
import React, {lazy, Suspense}           from 'react'
import {Redirect, Route, Router, Switch} from 'react-router-dom'
import styles                            from './App.styles'
import {history}                         from './helpers/history'
import {USER}                            from './constants/auth.constants'
import Header                            from './components/header/Header'
import Sidebar                           from './components/sidebar/Sidebar'
import CssBaseline                       from '@material-ui/core/CssBaseline'

const SignIn = lazy(() => import(/* webpackChunkName: "SignIn" */ './pages/signin/SignIn'))
const SignUp = lazy(() => import(/* webpackChunkName: "SignUp" */ './pages/signup/SignUp'))

const Home     = lazy(() => import(/* webpackChunkName: "Home" */ './pages/home/Home'))
const Library  = lazy(() => import(/* webpackChunkName: "Library" */ './pages/library/Library'))
const Ocr      = lazy(() => import(/* webpackChunkName: "Ocr" */ './pages/ocr/Ocr'))
const Grammar  = lazy(() => import(/* webpackChunkName: "Grammar" */ './pages/grammar/Grammar'))
const Reports  = lazy(() => import(/* webpackChunkName: "Reports" */ './pages/reports/Reports'))
const Settings = lazy(() => import(/* webpackChunkName: "Settings" */ './pages/settings/Settings'))

class App extends React.Component {

    constructor(props) {
        super(props)

        history.listen((location, action) => {
            // clear alertReducer on location change
            // TODO : Clear alerts
        })
    }

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <Router history={history}>
                    <div>
                        <Suspense fallback={<div>Loading...</div>}>
                            <Switch>
                                <Route path="/signin" component={SignIn}/>
                                <Route path="/signup" component={SignUp}/>
                                <Route path="/"
                                       render={props => (localStorage.getItem(USER) ?
                                               <div className={classes.adminpanel}>
                                                   <CssBaseline/>
                                                   <Header/>
                                                   <Sidebar/>
                                                   <main className={classes.content}>
                                                       <div className={classes.toolbar}/>
                                                       <Route exact path='/home' component={Home} />
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
                        </Suspense>
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

export default withStyles(styles, {withTheme: true})(App)
