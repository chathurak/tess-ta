import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './OptionPanel.styles'

class OptionPanel extends React.Component {

  render() {
    return (
      <div className={this.props.className}>
      </div>
    )
  }

}

OptionPanel.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(OptionPanel)
