import * as React        from "react";
import Button            from "@material-ui/core/Button";
import TextField         from "@material-ui/core/TextField";
import Dialog            from "@material-ui/core/Dialog";
import DialogActions     from "@material-ui/core/DialogActions";
import DialogContent     from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle       from "@material-ui/core/DialogTitle";
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

export default class FormDialog extends React.Component  {
    constructor(props) {
        super(props);
    
        this.state = {
            open: false,
            items: this.props.items,
            value: this.props.value
        };
    }

    handleClickOpen = () => {
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

    handleChange = (event) => {
        this.setState({
            value: event.target.value
        })
    }

    render() {
        return (
            <div>
                <Dialog
                    open            = {this.state.open}
                    onClose         = {this.handleCancel}
                    aria-labelledby = "form-dialog-title"
                >
                    <DialogTitle id="form-dialog-title">{this.props.title}</DialogTitle>
                    <DialogContent>
                        <DialogContentText>{this.props.message}</DialogContentText>

                        <List component="nav" aria-label="main mailbox folders">
                            {this.props.items.map((item, index) => (
                                <ListItem button onClick={this.handleOk} key={index}>
                                    <ListItemText primary={item} />
                                </ListItem>
                            ))}
                        </List>
                    </DialogContent>

                    <DialogActions>
                        <Button onClick={this.handleCancel} color="primary">
                            Cancel
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        )
 
    };
}
