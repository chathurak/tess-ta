import {withStyles}                        from '@material-ui/core/styles'
import PropTypes                           from 'prop-types'
import React, {lazy, Suspense}             from 'react'
import {Route, Switch, withRouter} from 'react-router-dom'
import styles                              from './App.styles'
import CssBaseline                         from '@material-ui/core/CssBaseline'
import PrivateRoute                        from './components/privateroute/PrivateRoute'
import {ACCESS_TOKEN}                      from './constants/auth.constants'

const SignIn                = lazy(() => import(/* webpackChunkName: "SignIn" */ './components/signin/SignIn'))
const OAuth2RedirectHandler = lazy(() => import(/* webpackChunkName: "OAuth2RedirectHandler" */ './components/oauth2redirecthandler/OAuth2RedirectHandler'))
const ManagementConsole     = lazy(() => import(/* webpackChunkName: "SignIn" */ './components/managementconsole/ManagementConsole'))


class App extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            authenticated: false,
            currentUser  : null
        }

        if (localStorage.getItem(ACCESS_TOKEN)) {
            this.state = {
                authenticated: true,
                currentUser  : null
            }
        }
    }

    componentDidMount() {
        this.loadCurrentlyLoggedInUser()
    }

    loadCurrentlyLoggedInUser = () => {
        // this.setState({
        //     loading: true
        // })

        // userServices.getCurrentUser()
        //     .then(response => {
        //         console.log(response)
        //         this.setState({
        //             currentUser: response,
        //             authenticated: true
        //         })
        //     }).catch(error => {
        //     this.setState({
        //         loading: false
        //     })
        // })
    }

    setAuthenticated = (value) => {
        this.setState({authenticated: value})
    }

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <CssBaseline/>
                <Suspense fallback={<div>Loading...</div>}>
                    <Switch>
                        <Route path="/signin"
                               render={(props) => <SignIn authenticated={this.state.authenticated} {...props} />}/>
                        <Route path="/oauth2/redirect"
                               render={(props) => <OAuth2RedirectHandler authenticated={this.state.authenticated}
                                                                         setAuthenticated={this.setAuthenticated} {...props} />}/>
                        <PrivateRoute path="/" component={ManagementConsole}
                                      authenticated={this.state.authenticated}
                                      currentUser={this.state.currentUser}/>
                    </Switch>
                </Suspense>
            </div>
        )
    }

}

App.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
}

export default withRouter((withStyles(styles, {withTheme: true})(App)))
