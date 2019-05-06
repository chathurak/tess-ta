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
import styles           from './SignIn.styles'

class SignIn extends React.Component {

  render() {
    const {classes} = this.props

    return (
      <main className={classes.main}>
        <CssBaseline/>
        <Paper className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon/>
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <form className={classes.form}>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="email">Email Address</InputLabel>
              <Input id="email" name="email" autoComplete="email" autoFocus/>
            </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input name="password" type="password" id="password" autoComplete="current-password"/>
            </FormControl>
            <FormControlLabel
              control={<Checkbox value="remember" color="primary"/>}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Sign in
            </Button>
          </form>
        </Paper>
      </main>
    )
  }

}

SignIn.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(SignIn)
