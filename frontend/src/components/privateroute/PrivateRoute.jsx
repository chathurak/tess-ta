import React             from 'react'
import {Redirect, Route} from 'react-router-dom'
import * as PropTypes    from 'prop-types'


class PrivateRoute extends React.Component {
    render() {
        const {component: Component, authenticated, ...rest} = this.props

        return (
            <Route
                {...rest}
                render={props =>
                    authenticated ? (
                        <Component {...rest} {...props} />
                    ) : (
                        <Redirect
                            to={{
                                pathname: '/signin',
                                state   : {from: props.location}
                            }}
                        />
                    )
                }
            />
        )
    }
}

PrivateRoute.propTypes = {
    component    : PropTypes.any,
    authenticated: PropTypes.any
}

export default PrivateRoute
