import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import ReportsPanel from './components/reportspanel/ReportsPanel'
import OptionPanel  from './components/optionpanel/OptionPanel'
import {styles}     from './styles'

class Reports extends React.Component {
    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <ReportsPanel className={classes.workspacePanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Reports)
