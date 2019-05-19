import {withStyles}  from '@material-ui/core'
import PropTypes     from 'prop-types'
import * as React    from 'react'
import {GoogleLogin} from 'react-google-login'
import {styles}      from './styles'

class Home extends React.Component {

    handleGoogleResponse = (e) => {
        console.log(e)
    }

    render() {
        return (
            <div>
                <GoogleLogin
                    clientId="1001062260512-qaflr02ho464t9s16v6n3a3hj42egq88.apps.googleusercontent.com"
                    buttonText="Login"
                    onSuccess={this.handleGoogleResponse}
                    onFailure={this.handleGoogleResponse}
                    cookiePolicy={'single_host_origin'}
                />
            </div>
        )
    }

}

Home.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Home)
