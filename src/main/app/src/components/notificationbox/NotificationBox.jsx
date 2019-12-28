import React           from 'react';
import {withStyles}    from '@material-ui/core/styles'
import {styles}        from './styles'
import Button          from '@material-ui/core/Button';
import Snackbar        from '@material-ui/core/Snackbar';
import IconButton      from '@material-ui/core/IconButton';
import CloseIcon       from '@material-ui/icons/Close';
import SnackbarContent from '@material-ui/core/SnackbarContent';


class NotificationBox extends React.Component  {
    constructor(props) {
        super(props);
    
        this.state = {
            open: false,
            message: this.props.message,
            type: 'success'
        };
    }

    showSuccess = (message) => {
        this.setState({
            message: message,
            type: 'success',
            open: true
        })
    }

    showError = (message) => {
        this.setState({
            message: message,
            type: 'error',
            open: true
        })
    }

    handleClose = () => {
        this.setState({
            open: false
        })
    }

    render() {
        const {classes} = this.props

        return (
            <div>
                <Button onClick={() => this.show("hello")}>Open simple snackbar</Button>
                <Snackbar
                    classes={{
                        root: classes.root
                    }}
                    anchorOrigin={{
                        vertical: "bottom",
                        horizontal: "left"
                    }} 
                    autoHideDuration={3000}
                    onClose={this.handleClose}
                    open={this.state.open}
                >
                    <SnackbarContent 
                        message={<span>{this.state.message}</span>}
                        className={classes[this.state.type]}
                        action={
                        <IconButton color="inherit" onClick={this.handleClose}>
                            <CloseIcon />
                        </IconButton>
                    }
                    >
                    </SnackbarContent>
                </Snackbar>
            </div>
        )
 
    };
}

export default withStyles(styles, {withTheme: true})(NotificationBox)

