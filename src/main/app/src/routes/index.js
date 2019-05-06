import {Switch} from '@material-ui/core'
import React    from 'react'
import {Route}  from 'react-router-dom'
import App      from '../App'
import {SignIn} from '../pages/signin'
import {SignUp} from '../pages/signup'


const routes = (
  <Switch>
    <Route exact path="/" component={App}/>
    <Route exact path="/signup" component={SignUp}/>
    <Route exact path="/signin" component={SignIn}/>
  </Switch>
)

export default routes
