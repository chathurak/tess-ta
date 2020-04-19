import Paper              from '@material-ui/core/Paper'
import React, {Component} from 'react'

class Menu extends Component {

    render() {
        return (
            <Paper square className={this.props.selectProps.classes.paper} {...this.props.innerProps}>
                {this.props.children}
            </Paper>
        )
    }

}

export default Menu
