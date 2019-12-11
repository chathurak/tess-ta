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

class SignUp extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            user     : {
                firstName: '',
                lastName : '',
                username : '',
                email    : '',
                password : ''
            },
            submitted: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleChange = event => {
        const {name, value} = event.target
        const {user}        = this.state
        this.setState({
            user: {
                ...user,
                [name]: value
            }
        })
    }

    handleSubmit = event => {
        event.preventDefault()

        this.setState({
            submitted: true
        })
        const {user} = this.state
        if (user.firstName && user.lastName && user.username && user.email && user.password) {
            userServices.signUp(user)
                .then(
                    user => {
                        // TODO : Do success activity
                        history.push('/signin')
                        console.log('Registration successful')
                    },
                    error => {
                        // TODO : handle registration error
                        console.log('There were some errors in registration!')
                    }
                )
        }
    }

    render() {
        const {classes} = this.props
        const {user}    = this.state

        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Paper className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">Sign Up</Typography>
                    <form className={classes.form} onSubmit={this.handleSubmit}>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="firstName">First name</InputLabel>
                            <Input id="firstName" name="firstName" value={user.firstName} onChange={this.handleChange}
                                   autoComplete="fname" autoFocus/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="lastName">Last name</InputLabel>
                            <Input id="lastName" name="lastName" value={user.lastName} onChange={this.handleChange}
                                   autoComplete="lname"/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="username">Username</InputLabel>
                            <Input id="username" name="username" value={user.username} onChange={this.handleChange}
                                   autoComplete="username"/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="email">Email Address</InputLabel>
                            <Input id="email" name="email" value={user.email} onChange={this.handleChange}
                                   autoComplete="email"/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="password">Password</InputLabel>
                            <Input id="password" name="password" type="password" value={user.password}
                                   onChange={this.handleChange} autoComplete="current-password"/>
                        </FormControl>
                        <FormControlLabel control={<Checkbox value="remember" color="primary"/>} label="Remember me"/>
                        <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
                            Sign up
                        </Button>
                    </form>
                    <p>
                        Already have an account?&nbsp;
                        <Link to="/signin">Sign In</Link>
                    </p>
                </Paper>
            </Container>
        )
    }

}

SignUp.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(SignUp)