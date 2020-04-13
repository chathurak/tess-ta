import React             from 'react'
import {Redirect, Route} from 'react-router-dom'

class PrivateRoute extends React.Component {
    render() {
        let {component: Component, authenticated, ...rest} = this.props

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

export default PrivateRoute