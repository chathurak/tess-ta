import {withStyles} from '@material-ui/core/styles'
import * as React   from 'react'
import OptionPanel  from './components/optionpanel/OptionPanel'
import TaskPanel    from './components/taskpanel/TaskPanel'
import {styles}     from './styles'

class Ocr extends React.Component {

    constructor(props, context) {
        super(props, context)

        this.state = {
            selectedDocument: null
        }
    }

    handleDocumentChange = (document) => {
        this.setState({selectedDocument: document})
    }

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <TaskPanel className={classes.workspacePanel} selectedDocument={this.state.selectedDocument}/>
                <OptionPanel className={classes.optionPanel} handleDocumentChange={this.handleDocumentChange}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Ocr)
