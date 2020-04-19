import Chip               from '@material-ui/core/Chip'
import CancelIcon         from '@material-ui/icons/Cancel'
import classNames         from 'classnames'
import React, {Component} from 'react'

class MultiValue extends Component {

    render() {
        return (
            <Chip
                tabIndex={-1}
                label={this.props.children}
                className={classNames(this.props.selectProps.classes.chip, {
                    [this.props.selectProps.classes.chipFocused]: this.props.isFocused
                })}
                onDelete={this.props.removeProps.onClick}
                deleteIcon={<CancelIcon {...this.props.removeProps} />}
            />
        )
    }

}

export default MultiValue
