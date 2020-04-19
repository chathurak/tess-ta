import Typography         from '@material-ui/core/Typography'
import React, {Component} from 'react'

class Placeholder extends Component {

    render() {
        return (
            <Typography
                color="textSecondary"
                className={this.props.selectProps.classes.placeholder}
                {...this.props.innerProps}
            >
                {this.props.children}
            </Typography>
        )
    }

}

export default Placeholder
