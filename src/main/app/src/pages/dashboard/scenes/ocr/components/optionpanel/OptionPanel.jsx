import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import GDriveUpload from './components/gdriveupload/GDriveUpload'
import styles       from './styles'

class OptionPanel extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={this.props.className}>
                <GDriveUpload className={classes.gDriveUpload}/>
            </div>
        )
    }

}

OptionPanel.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(OptionPanel)
