import {withStyles}   from '@material-ui/core/styles'
import * as React     from 'react'
import TaskPicker from '../../../../../components/taskpicker/TaskPicker'
import {styles}       from './styles'

class OptionPanel extends React.Component {

    render() {
        return (
            <div className={this.props.className}>
                <TaskPicker handleTaskChange={this.props.handleTaskChange}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(OptionPanel)