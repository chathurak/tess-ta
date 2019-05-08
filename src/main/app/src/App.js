import {withStyles}    from '@material-ui/core/styles'
import PropTypes       from 'prop-types'
import React           from 'react'
import {connect}       from 'react-redux'
import {Route, Router} from 'react-router-dom'
import {alertActions}  from './actions/alert.actions'
import styles          from './App.styles'
import {PrivateRoute}  from './components'
import {history}       from './helpers'
import {Dashboard}     from './pages/dashboard'
import {SignIn}        from './pages/signin'
import {SignUp}        from './pages/signup'

class App extends React.Component {

    constructor(props) {
        super(props)

        const {dispatch} = this.props
        history.listen((location, action) => {
            // clear alert on location change
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
                        <PrivateRoute exact path="/" component={Dashboard}/>
                        <Route path="/login" component={SignIn}/>
                        <Route path="/register" component={SignUp}/>
                    </div>
                </Router>
            </div>
        )
    }

}

App.propTypes = {
    classes: PropTypes.object.isRequired,
}

function mapStateToProps(state) {
    const {alert} = state
    return {
        alert
    }
}

const styledComponent = withStyles(styles, {withTheme: true})(App)
export default connect(mapStateToProps)(styledComponent)
