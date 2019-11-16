import MenuItem           from '@material-ui/core/MenuItem'
import React, {Component} from 'react'

class Option extends Component {

    render() {
        return (
            <MenuItem
                buttonRef={this.props.innerRef}
                selected={this.props.isFocused}
                component="div"
                style={{
                    fontWeight: this.props.isSelected ? 500 : 400,
                }}
                {...this.props.innerProps}
            >
                {this.props.children}
            </MenuItem>
        )
    }

}

export default Option