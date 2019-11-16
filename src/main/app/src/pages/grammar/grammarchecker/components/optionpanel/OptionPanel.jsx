import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import TaskPicker   from '../../../../../components/taskpicker/TaskPicker'
import {styles}     from './styles'

class OptionPanel extends React.Component {

    render() {
        return (
            <div className={this.props.className}>
                <TaskPicker/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(OptionPanel)