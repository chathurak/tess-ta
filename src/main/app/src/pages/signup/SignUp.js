import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './SignUp.styles'

class SignUp extends React.Component {

  render() {
    return (
      <div>

      </div>
    )
  }

}

SignUp.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(SignUp)
