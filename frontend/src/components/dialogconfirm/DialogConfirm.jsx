import * as React        from "react";
import Button            from "@material-ui/core/Button";
import Dialog            from "@material-ui/core/Dialog";
import DialogActions     from "@material-ui/core/DialogActions";
import DialogContent     from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle       from "@material-ui/core/DialogTitle";
import Slide             from '@material-ui/core/Slide';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="down" ref={ref} {...props} />;
});

export default function FormDialog(props) {
    const [open, setOpen] = React.useState(false);
    const [value] = React.useState(props.value);

    function handleClickOpen() {
        setOpen(true);
    }

    function handleNo() {
        setOpen(false);
    }

    function handleYes() {
        setOpen(false);
        props.onOk(value);
    }

    return (
        <div>
            <Button
                variant = "outlined"
                color   = "secondary"
                onClick = {handleClickOpen}
                style={props.style}
            >
                {props.label}
            </Button>
            <Dialog
                open={open}
                TransitionComponent={Transition}
                keepMounted
                onClose={handleNo}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle id="alert-dialog-slide-title">{props.title}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">{props.message}</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleNo} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleYes} color="primary">
                        OK
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
