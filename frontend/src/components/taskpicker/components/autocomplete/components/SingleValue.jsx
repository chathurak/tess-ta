import Typography         from '@material-ui/core/Typography'
import React, {Component} from 'react'

class SingleValue extends Component {

    render() {
        return (
            <Typography className={this.props.selectProps.classes.singleValue} {...this.props.innerProps}>
                {this.props.children}
            </Typography>
        )
    }

}

export default SingleValue
