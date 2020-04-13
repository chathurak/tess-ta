import {withStyles}     from '@material-ui/core/styles'
import * as React       from 'react'
import {styles}         from './styles'
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'
import 'filepond/dist/filepond.min.css'
import FileManagerPanel from './components/filemanagerpanel/FileManagerPanel'
import OptionPanel      from './components/optionpanel/OptionPanel'

class Library extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <FileManagerPanel className={classes.workspacePanel}/>
                <OptionPanel className={classes.optionPanel}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Library)
