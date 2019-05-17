import TextField          from '@material-ui/core/TextField'
import React, {Component} from 'react'

function inputComponent({inputRef, ...props}) {
    return <div ref={inputRef} {...props} />
}

class Control extends Component {

    render() {
        return (
            <TextField
                fullWidth
                InputProps={{
                    inputComponent,
                    inputProps: {
                        className: this.props.selectProps.classes.input,
                        inputRef : this.props.innerRef,
                        children : this.props.children,
                        ...this.props.innerProps,
                    },
                }}
                {...this.props.selectProps.textFieldProps}
            />
        )
    }

}

export default Control