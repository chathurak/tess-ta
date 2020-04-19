import * as React        from 'react'
import Button            from '@material-ui/core/Button'
import TextField         from '@material-ui/core/TextField'
import Dialog            from '@material-ui/core/Dialog'
import DialogActions     from '@material-ui/core/DialogActions'
import DialogContent     from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle       from '@material-ui/core/DialogTitle'

export default function FormDialog(props) {
    const [open, setOpen]   = React.useState(false)
    const [value, setValue] = React.useState(props.value)

    function handleClickOpen() {
        setOpen(true)
    }

    function handleCancel() {
        setOpen(false)
    }

    function handleOk() {
        setOpen(false)
        props.onOk(value)
    }

    function handleChange(event) {
        setValue(event.target.value)
    }

    return (
        <div>
            <Button
                variant="outlined"
                color="secondary"
                onClick={handleClickOpen}
            >
                {props.label}
            </Button>
            <Dialog
                open={open}
                onClose={handleCancel}
                aria-labelledby="form-dialog-title"
            >
                <DialogTitle id="form-dialog-title">{props.title}</DialogTitle>
                <DialogContent>
                    <DialogContentText>{props.message}</DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        fullWidth
                        value={value}
                        onChange={handleChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCancel} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleOk} color="primary">
                        OK
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}
