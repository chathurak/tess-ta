import {withStyles}    from '@material-ui/core'
import Button          from '@material-ui/core/Button'
import PropTypes       from 'prop-types'
import * as React      from 'react'
import styles          from './styles'
import CloudUploadIcon from '@material-ui/icons/CloudUpload'


class GDriveUpload extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={this.props.className}>
                <Button variant="contained" color="default" className={classes.button}>
                    Upload
                    <CloudUploadIcon className={classes.rightIcon}/>
                </Button>
            </div>
        )
    }

}

GDriveUpload.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(GDriveUpload)
