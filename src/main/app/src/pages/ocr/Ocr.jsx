import {withStyles} from '@material-ui/core/styles'
import * as React   from 'react'
import OptionPanel  from './components/optionpanel/OptionPanel'
import TaskPanel    from './components/taskpanel/TaskPanel'
import {styles}     from './styles'

class Ocr extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <TaskPanel className={classes.workspacePanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Ocr)
