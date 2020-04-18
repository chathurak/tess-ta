import React from 'react'
import {Redirect, withRouter} from 'react-router-dom'
import {ACCESS_TOKEN} from '../../constants/auth.constants'
import {withStyles} from '@material-ui/core/styles'
import {styles} from '../../pages/signin/styles'

class OAuth2RedirectHandler extends React.Component {

    constructor(props) {
        super(props)
    }

    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]')
        let regex = new RegExp('[\\?&]' + name + '=([^&#]*)')

        let results = regex.exec(this.props.location.search)
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '))
    };

    componentDidMount() {
        const {history} = this.props

        const token = this.getUrlParameter('token')
        const error = this.getUrlParameter('error')

        if (token) {
            localStorage.setItem(ACCESS_TOKEN, token)
            this.props.setAuthenticated(true)
            history.push('/home', {from: this.props.location})
        } else {
            this.props.setAuthenticated(false)
            history.push('/signin', {from: this.props.location, error: error})
        }
    }

    render() {
        return null
    }
}

export default withRouter(OAuth2RedirectHandler)
