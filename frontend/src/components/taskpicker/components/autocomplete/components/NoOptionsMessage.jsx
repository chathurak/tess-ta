import Typography         from '@material-ui/core/Typography'
import React, {Component} from 'react'

class NoOptionsMessage extends Component {

    render() {
        return (
            <Typography
                color="textSecondary"
                className={this.props.selectProps.classes.noOptionsMessage}
                {...this.props.innerProps}
            >
                {this.props.children}
            </Typography>
        )
    }

}

export default NoOptionsMessage
