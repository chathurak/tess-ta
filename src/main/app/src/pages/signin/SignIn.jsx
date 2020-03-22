import {withStyles}   from '@material-ui/core/styles'
import Button         from '@material-ui/core/Button'
import Container      from '@material-ui/core/Container'
import PropTypes      from 'prop-types'
import * as React     from 'react'
import {styles}       from './styles'
import {userServices} from '../../services/user.services'
import LockOpenIcon   from '@material-ui/icons/LockOpen'

class SignIn extends React.Component {

    constructor(props) {
        super(props)

        // reset login status
        userServices.signOut()
    }

    render() {
        const {classes}         = this.props

        return (
            <Container className={classes.container} component="main" maxWidth="xs">
                <Button fullWidth variant="contained" color="primary" className={classes.submit} startIcon={<LockOpenIcon/>}
                        onClick={event =>  window.location.href='/signin/oauth2/authorization/google'}>
                    Sign in
                </Button>
            </Container>
        )
    }

}

SignIn.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(SignIn)
