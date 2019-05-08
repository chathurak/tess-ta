import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './ActionPanel.styles'

class ActionPanel extends React.Component {

    render() {
        return (
            <div className={this.props.className}>
            </div>
        )
    }

}

ActionPanel.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(ActionPanel)
