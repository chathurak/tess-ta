import {withStyles}   from '@material-ui/core'
import PropTypes      from 'prop-types'
import * as React     from 'react'
import {GoogleLogin}  from 'react-google-login'
import {userServices} from '../../services'
import {styles}       from './styles'

class Settings extends React.Component {

    handleGoogleResponseSuccess = (e) => {
        userServices.updateAccessToken(e.accessToken, e.profileObj.imageUrl)
        console.log(e);
    }

    handleGoogleResponseFailure = (e) => {
        console.log(e)
    }

    render() {
        return (
            <div>
                <GoogleLogin
                    clientId="1001062260512-6i2uotvcggju09e133vvu71oaerujqdt.apps.googleusercontent.com"
                    buttonText="Login"
                    onSuccess={this.handleGoogleResponseSuccess}
                    onFailure={this.handleGoogleResponseFailure}
                    cookiePolicy={'single_host_origin'}
                    scope="https://www.googleapis.com/auth/drive.file"
                />
            </div>
        )
    }

}

Settings.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Settings)
