import {withStyles} from '@material-ui/core/styles'
import Button       from '@material-ui/core/Button'
import Container    from '@material-ui/core/Container'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import {styles}     from './styles'
import LockOpenIcon from '@material-ui/icons/LockOpen'
import {Redirect}   from 'react-router-dom'

class SignIn extends React.Component {

    constructor(props) {
        super(props)
    }

    componentDidMount() {
        // If the OAuth2 login encounters an error, the user is redirected to the /signin page with an error
        // Here we display the error and then remove the error query parameter from the location.
        if(this.props.location.state && this.props.location.state.error) {
            setTimeout(() => {
                console.log(this.props.location.state.error)
                this.props.history.replace({
                    pathname: this.props.location.pathname,
                    state: {}
                });
            }, 100);
        }
    }

    render() {
        const {classes}         = this.props

        if(this.props.authenticated) {
            return <Redirect
                to={{
                    pathname: "/",
                    state: { from: this.props.location }
                }}/>;
        }

        return (
            <Container className={classes.container} component="main" maxWidth="xs">
                <Button fullWidth variant="contained" color="primary" className={classes.submit} startIcon={<LockOpenIcon/>}
                        onClick={event =>  window.location.href='/login/oauth2/authorize/google'}>
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
