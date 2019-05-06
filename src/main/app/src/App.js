import {withStyles}    from '@material-ui/core/styles'
import PropTypes       from 'prop-types'
import React           from 'react'
import {Route, Router} from 'react-router-dom'
import styles          from './App.styles'
import {PrivateRoute}  from './components'
import {history}       from './helpers'
import {Dashboard}     from './pages/dashboard'
import {SignIn}        from './pages/signin'
import {SignUp}        from './pages/signup'

class App extends React.Component {

  render() {
    // return (
    //   <div>
    //     {this.props.children}
    //   </div>
    // )

    const {alert} = this.props

    return (
      <div className="jumbotron">
        <div className="container">
          <div className="col-sm-8 col-sm-offset-2">
            {alert.message && <div className={`alert ${alert.type}`}>{alert.message}</div>}
            <Router history={history}>
              <div>
                <PrivateRoute exact path="/" component={Dashboard}/>
                <Route path="/login" component={SignIn}/>
                <Route path="/register" component={SignUp}/>
              </div>
            </Router>
          </div>
        </div>
      </div>
    )
  }

}

App.propTypes = {
  classes : PropTypes.object.isRequired,
  children: PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(App)
