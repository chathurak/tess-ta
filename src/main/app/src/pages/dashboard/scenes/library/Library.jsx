import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import TaskPanel    from './components/filemanagerpanel/FileManagerPanel'
import OptionPanel  from './components/optionpanel/OptionPanel'
import {styles}     from './styles'

class Library extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <TaskPanel className={classes.taskPanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Library)
