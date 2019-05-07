import {withStyles}     from '@material-ui/core'
import Avatar           from '@material-ui/core/Avatar'
import Button           from '@material-ui/core/Button'
import Checkbox         from '@material-ui/core/Checkbox'
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
import {connect}        from 'react-redux'
import {Link}           from 'react-router-dom'
import {userActions}    from '../../actions/user.actions'
import styles           from './SignIn.styles'

class SignIn extends React.Component {

  constructor(props) {
    super(props)

    // reset login status
    this.props.dispatch(userActions.logout())

    this.state = {
      email    : '',
      password : '',
      submitted: false
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleChange(e) {
    const {name, value} = e.target
    this.setState({[name]: value})
  }

  handleSubmit(e) {
    e.preventDefault()

    this.setState({submitted: true})
    const {email, password} = this.state
    const {dispatch}        = this.props
    if (email && password) {
      dispatch(userActions.login(email, password))
    }
  }

  render() {
    // TODO : Use loggingIn and submitted
    const {classes, loggingIn}         = this.props
    const {email, password, submitted} = this.state

    return (
      <main className={classes.root}>
        <CssBaseline/>
        <Paper className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h5">Sign In</Typography>
          <form className={classes.form} onSubmit={this.handleSubmit}>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="email">Email Address</InputLabel>
              <Input id="email" name="email" value={email} onChange={this.handleChange} autoComplete="email" autoFocus/>
            </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input id="password" name="password" type="password" value={password} onChange={this.handleChange}
                     autoComplete="current-password"/>
            </FormControl>
            <FormControlLabel control={<Checkbox value="remember" color="primary"/>} label="Remember me"/>
            <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
              Sign in
            </Button>
          </form>
          <p>
            Don&apos;t have an account?&nbsp;
            <Link to="/register">Sign Up</Link>
          </p>
        </Paper>
      </main>
    )
  }

}

SignIn.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

function mapStateToProps(state) {
  const {loggingIn} = state.authentication
  return {
    loggingIn
  }
}

const styledComponent = withStyles(styles, {withTheme: true})(SignIn)
export default connect(mapStateToProps)(styledComponent)
