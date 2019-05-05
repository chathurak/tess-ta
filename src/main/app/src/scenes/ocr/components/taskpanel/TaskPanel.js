import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from '../../../../components/sidebar/Sidebar.styles'

class TaskPanel extends React.Component {

  render() {
    return (
      <div className={this.props.className}>
      </div>
    )
  }

}

TaskPanel.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(TaskPanel)
