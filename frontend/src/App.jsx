import { withStyles } from '@material-ui/core/styles'
import PropTypes from 'prop-types'
import React, { lazy, Suspense } from 'react'
import { Redirect, Route, Router, Switch } from 'react-router-dom'
import styles from './App.styles'
import { history } from './helpers/history'
import Header from './components/header/Header'
import Sidebar from './components/sidebar/Sidebar'
import CssBaseline from '@material-ui/core/CssBaseline'
import { userServices } from './services/user.services'

const SignIn = lazy(() => import(/* webpackChunkName: "SignIn" */ './pages/signin/SignIn'))
const OAuth2RedirectHandler = lazy(() => import(/* webpackChunkName: "OAuth2RedirectHandler" */ './components/oauth2redirecthandler/OAuth2RedirectHandler'))
const Home = lazy(() => import(/* webpackChunkName: "Home" */ './pages/home/Home'))
const Library = lazy(() => import(/* webpackChunkName: "Library" */ './pages/library/Library'))
const Ocr = lazy(() => import(/* webpackChunkName: "Ocr" */ './pages/ocr/Ocr'))
const Spellcheck = lazy(() => import(/* webpackChunkName: "Spellcheck" */ './pages/spellcheck/Spellcheck'))
const Reports = lazy(() => import(/* webpackChunkName: "Reports" */ './pages/reports/Reports'))
const Settings = lazy(() => import(/* webpackChunkName: "Settings" */ './pages/settings/Settings'))
const NotFound = lazy(() => import(/* webpackChunkName: "NotFound" */ './components/notfound/NotFound'))

class App extends React.Component {

    constructor(props) {
        super(props)

        history.listen((location, action) => {
            // clear alertReducer on location change
            // TODO : Clear alerts
        })

        this.state = {
            authenticated: false,
            currentUser: null
        }
    }

    loadCurrentlyLoggedInUser = () => {
        this.setState({
            loading: true
        });

        userServices.getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    authenticated: true
                });
            }).catch(error => {
                this.setState({
                    loading: false
                });
            });
    }

    componentDidMount() {
        this.loadCurrentlyLoggedInUser();
    }

    render() {
        const { classes } = this.props

        if (this.state.authenticated) {
            return (
                <div className={classes.root}>
                    <CssBaseline />
                    <Router history={history}>
                        <Suspense fallback={<div>Loading...</div>}>
                            <div className={classes.adminpanel}>
                                <Header />
                                <Sidebar />
                                <main className={classes.content}>
                                    <div className={classes.toolbar} />
                                    <Suspense fallback={<div>Loading...</div>}>
                                        <Switch>
                                            <Route exact path='/home' component={Home} />
                                            <Route exact path='/library' component={Library} />
                                            <Route exact path='/ocr' component={Ocr} />
                                            <Route exact path='/spellcheck' component={Spellcheck} />
                                            <Route exact path='/reports' component={Reports} />
                                            <Route exact path='/settings' component={Settings} />
                                        </Switch>
                                    </Suspense>
                                </main>
                            </div>
                        </Suspense>
                    </Router>
                </div>
            )
        } else {
            return (
                <div className={classes.root}>
                    <CssBaseline />
                    <Router history={history}>
                        <Suspense fallback={<div>Loading...</div>}>
                            <Route path="/signin" component={SignIn} render={
                                (props) => <SignIn authenticated={this.state.authenticated} {...props} />
                            } />
                            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} />
                        </Suspense>
                    </Router>
                </div>
            )
        }
    }

}

App.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
}

export default withStyles(styles, { withTheme: true })(App)
