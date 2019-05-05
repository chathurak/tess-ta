import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {ActionPanel} from './components/actionpanel'
import {OptionPanel} from './components/optionpanel'
import {TaskPanel}   from './components/taskpanel'
import styles        from './Ocr.styles'

class Ocr extends React.Component {

  render() {
    const {classes} = this.props

    return (
      <div className={classes.ocrRoot}>
        <div className={classes.contentPane}>
          <ActionPanel className={classes.actionPanel}/>
          <TaskPanel className={classes.taskPanel}/>
        </div>
        <OptionPanel className={classes.optionsPane}/>
      </div>
    )
  }

}

export default withStyles(styles, {withTheme: true})(Ocr)
