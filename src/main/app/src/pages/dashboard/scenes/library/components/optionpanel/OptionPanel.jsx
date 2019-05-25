import {withStyles}               from '@material-ui/core'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'
import 'filepond/dist/filepond.min.css'
import PropTypes                  from 'prop-types'
import * as React                 from 'react'
import {FilePond, registerPlugin} from 'react-filepond'
import {config as filepondConfig} from './filepond.config'
import {styles}                   from './styles'

// Register the plugins
registerPlugin(FilePondPluginImagePreview)

class OptionPanel extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            // Set initial files, type 'local' means this is a file
            // that has already been uploaded to the server (see docs)
            files: []
        }
    }

    handleInit() {
        // console.log('FilePond instance has initialised', this.pond)
    }

    render() {
        const {classes} = this.props

        return (
            <div      className     = {this.props.className}>
            <FilePond className     = {classes.filePond}
                      ref           = {ref => (this.pond = ref)}
                      files         = {this.state.files}
                      allowMultiple = {true}
                      maxFiles      = {3}
                      server        = {filepondConfig}
                      oninit        = {() => this.handleInit()}
                      onupdatefiles = {fileItems => {
                              // Set currently active file objects to this.state
                              this.setState({
                                  files: fileItems.map(fileItem => fileItem.file)
                              })
                        }}
                />
            </div>
        )
    }

}

OptionPanel.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(OptionPanel)
