import {withStyles} from '@material-ui/core/styles'
import * as React   from 'react'
import {styles}     from './styles'
import CheckerPanel from './components/checkerpanel/CheckerPanel'
import OptionPanel  from './components/optionpanel/OptionPanel'


class GrammarChecker extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            selectedTask: null
        }
    }

    handleTaskChange = (task) => {
        this.setState({selectedTask: task})
    }

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <CheckerPanel className={classes.workspacePanel} selectedTask={this.state.selectedTask}/>
                <OptionPanel className={classes.optionPanel} handleTaskChange={this.handleTaskChange}/>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(GrammarChecker)
