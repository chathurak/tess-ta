import React             from 'react'
import Button            from '@material-ui/core/Button'
import Dialog            from '@material-ui/core/Dialog'
import DialogActions     from '@material-ui/core/DialogActions'
import DialogContent     from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import {styles}    from '../signin/styles'
import PropTypes   from 'prop-types'
import {withStyles}      from '@material-ui/core/styles'

class AlertDialog extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            open: false
        }
    }

    componentDidMount = () => {
        this.handleClickOpen()
    }

    handleClickOpen = () => {
        this.state.open = true
    }

    handleClose = () => {
        this.state.open = false
    }

    render() {
        let {classes, title, description} = this.props

        return (
            <Dialog
                open={this.state.open}
                onClose={this.handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">{description}</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary" autoFocus>
                        OK
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}

AlertDialog.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(AlertDialog)
