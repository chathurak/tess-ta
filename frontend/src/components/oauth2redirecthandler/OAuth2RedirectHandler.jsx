import React, {Component} from 'react'
import {Redirect}         from 'react-router-dom'
import {ACCESS_TOKEN}     from '../../constants/auth.constants'
import {history} from "../../helpers/history";

class OAuth2RedirectHandler extends Component {

    constructor(props) {
        super(props)
    }

    getUrlParameter(name) {
        name      = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]')
        let regex = new RegExp('[\\?&]' + name + '=([^&#]*)')

        let results = regex.exec(this.props.location.search)
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '))
    };

    render() {
        const token = this.getUrlParameter('token')
        const error = this.getUrlParameter('error')

        if (token) {
            localStorage.setItem(ACCESS_TOKEN, token)
            return <Redirect to={{
                pathname: '/',
                state   : {
                    from: this.props.location
                }
            }}/>
        } else {
            return <Redirect to={{
                pathname: '/signin',
                state   : {
                    from : this.props.location,
                    error: error
                }
            }}/>
        }
    }
}

export default OAuth2RedirectHandler
