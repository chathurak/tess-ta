import {withStyles}     from '@material-ui/core/styles'
import PropTypes        from 'prop-types'
import * as React       from 'react'
import {GoogleLogin}    from 'react-google-login'
import {userServices}   from '../../services/user.services'
import {styles}         from './styles'
import {G_ACCESS_TOKEN} from '../../constants/auth.constants'

class Settings extends React.Component {

    handleGoogleResponseSuccess = (e) => {
        // userServices.updateAccessToken(e.accessToken, e.profileObj.imageUrl)
        localStorage.setItem(G_ACCESS_TOKEN, e.accessToken)
        console.log(e)
    }

    handleGoogleResponseFailure = (e) => {
        console.log(e)
    }

    render() {
        return (
            <div>
                <GoogleLogin
                    clientId="649193414368-9g4ksk2r1fi9goqto8k41k4loj594s39.apps.googleusercontent.com"
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
    theme  : PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(Settings)
