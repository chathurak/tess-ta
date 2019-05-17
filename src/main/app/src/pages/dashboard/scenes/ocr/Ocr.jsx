import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import TaskPicker   from '../../components/taskpicker/TaskPicker'
import {styles}     from './styles'

class Ocr extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <TaskPicker/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Ocr)
