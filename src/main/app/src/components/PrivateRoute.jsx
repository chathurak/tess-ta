import * as PropTypes     from 'prop-types'
import React, {Component} from 'react'
import {Redirect, Route}  from 'react-router-dom'
import {USER}             from '../constants/auth.constants'

class PrivateRoute extends Component {

    render() {
        let {component: Component, ...rest} = this.props

        return (
            <Route {...rest} render={props => (
                localStorage.getItem(USER)
                    ? <Component {...props} />
                    : <Redirect to={{pathname: '/signin', state: {from: props.location}}}/>
            )}/>
        )
    }

}

PrivateRoute.propTypes = {
    component: PropTypes.any
}

export default PrivateRoute