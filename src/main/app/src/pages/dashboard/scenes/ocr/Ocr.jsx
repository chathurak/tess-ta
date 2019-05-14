import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {OptionPanel} from './components/optionpanel'
import {TaskPanel}   from './components/taskpanel'
import styles        from './Ocr.styles'

class Ocr extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.ocrRoot}>
                <TaskPanel className={classes.taskPanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Ocr)
