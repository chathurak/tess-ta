import {withStyles}     from '@material-ui/core/styles'
import Avatar           from '@material-ui/core/Avatar'
import Button           from '@material-ui/core/Button'
import Checkbox         from '@material-ui/core/Checkbox'
import Container        from '@material-ui/core/Container'
import CssBaseline      from '@material-ui/core/CssBaseline'
import FormControl      from '@material-ui/core/FormControl'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import Input            from '@material-ui/core/Input'
import InputLabel       from '@material-ui/core/InputLabel'
import Paper            from '@material-ui/core/Paper'
import Typography       from '@material-ui/core/Typography'
import LockOutlinedIcon from '@material-ui/icons/LockOutlined'
import PropTypes        from 'prop-types'
import * as React       from 'react'
import {Link}           from 'react-router-dom'
import {styles}         from './styles'
import {userServices}   from '../../services/user.services'
import {history}        from '../../helpers/history'

class SignIn extends React.Component {

    constructor(props) {
        super(props)

        // reset login status
        userServices.signOut()

        this.state = {
            email   : '',
            password: '',
        }
    }

    handleChange = event => {
        const {name, value} = event.target
        this.setState({[name]: value})
    }

    handleSubmit = event => {
        event.preventDefault()

        this.setState({submitted: true})
        const {email, password} = this.state

        if (email && password) {
            userServices.signIn(email, password)
                .then(
                    user => {
                        // TODO : Do login stuff like saving user details
                        history.push('/')
                    },
                    error => {
                        // TODO : Give a proper error message
                        console.log('Some error occurred. Cant login!!!')
                    }
                )
        }
    }

    render() {
        const {classes}         = this.props
        const {email, password} = this.state

        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Paper className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">Sign In</Typography>
                    <form className={classes.form} onSubmit={this.handleSubmit}>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="email">Email Address</InputLabel>
                            <Input id="email" name="email" value={email} onChange={this.handleChange}
                                   autoComplete="email" autoFocus/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="password">Password</InputLabel>
                            <Input id="password" name="password" type="password" value={password}
                                   onChange={this.handleChange} autoComplete="current-password"/>
                        </FormControl>
                        <FormControlLabel control={<Checkbox value="remember" color="primary"/>} label="Remember me"/>
                        <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
                            Sign in
                        </Button>
                    </form>
                    <p>
                        Don&apos;t have an account?&nbsp;
                        <Link to="/signup">Sign Up</Link>
                    </p>
                </Paper>
            </Container>
        )
    }

}

SignIn.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(SignIn)
