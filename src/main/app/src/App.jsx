import {withStyles}              from '@material-ui/core/styles'
import PropTypes                 from 'prop-types'
import React                     from 'react'
import {connect}                 from 'react-redux'
import {Route, Router, Switch}   from 'react-router-dom'
import styles                    from './App.styles'
import {PrivateRoute}            from './components'
import {actions as alertActions} from './components/alert/duck'
import {history}                 from './helpers'
import Dashboard                 from './pages/dashboard/Dashboard'
import SignIn                    from './pages/signin/SignIn'
import SignUp                    from './pages/signup/SignUp'

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
                            <PrivateRoute path="/" component={Dashboard}/>
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
