import React, {Component} from 'react'

class ValueContainer extends Component {

    render() {
        return <div className={this.props.selectProps.classes.valueContainer}>
            {this.props.children}
        </div>
    }

}

export default ValueContainer