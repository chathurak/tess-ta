import * as React        from "react";
import Button            from "@material-ui/core/Button";
import Dialog            from "@material-ui/core/Dialog";
import DialogActions     from "@material-ui/core/DialogActions";
import DialogContent     from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle       from "@material-ui/core/DialogTitle";

export default class FormDialog extends React.Component  {
    constructor(props) {
        super(props);
    
        this.state = {
            action: this.props.action,
            title: this.props.title,
            message: this.props.message,
            open: false,
        };
    }

    show = () => {
        this.setState({
            open: true
        })
    }

    handleCancel = () => {
        this.setState({
            open: false
        })
    }

    handleOk = () => {
        this.setState({
            open: false
        })
        this.props.onOk(this.state.value);
    }

    render() {
        return (
            <div>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleCancel}
                    aria-labelledby="alert-dialog-slide-title"
                    aria-describedby="alert-dialog-slide-description"
                >
                    <DialogTitle id="alert-dialog-slide-title">{this.props.title}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-slide-description">{this.props.message}</DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleCancel} color="secondary">
                            Cancel
                        </Button>
                        <Button onClick={this.handleOk} color="secondary" variant="outlined">
                            {this.props.action}
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        )
 
    };
}
