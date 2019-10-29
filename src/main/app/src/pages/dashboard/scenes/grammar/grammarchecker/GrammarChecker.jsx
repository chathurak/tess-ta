import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {styles}      from './styles'
import CheckerPanel  from './components/checkerpanel/CheckerPanel'
import OptionPanel   from './components/optionpanel/OptionPanel'


class GrammarChecker extends React.Component {
    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <CheckerPanel className={classes.workspacePanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(GrammarChecker)
