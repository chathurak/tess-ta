import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './Dashboard.styles'

class Dashboard extends React.Component {

  render() {
    const {classes} = this.props

    return (
      <div>
      </div>
    )
  }

}

Dashboard.propTypes = {
  classes: PropTypes.object.isRequired,
  theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Dashboard)
