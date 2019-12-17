import * as React                 from 'react'
import {withStyles}               from '@material-ui/core/styles'
import {FilePond, registerPlugin} from 'react-filepond'
import {config as filepondConfig} from './filepond.config'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import {styles}                   from './styles'

// register the plugins
registerPlugin(FilePondPluginImagePreview)

class OptionPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            // Set initial files, type 'local' means this is a file
            // that has already been uploaded to the server (see docs)
            files: []
        }
    }

    handlePondInit = () => {
        // console.log('FilePond instance has initialised', this.pond)
    }

    render() {
        const {classes} = this.props

        return (
            <div className={this.props.className}>
                {
                    this.state.selectedDocumentId ?
                        <div></div> :
                        <FilePond
                            className={classes.filePond}
                            ref={ref => (this.pond = ref)}
                            files={this.state.files}
                            allowMultiple={true}
                            maxFiles={3}
                            server={filepondConfig}
                            oninit={() => this.handlePondInit()}
                            onupdatefiles={fileItems => {
                                // set currently active file objects to this.state
                                this.setState({
                                    files: fileItems.map(fileItem => fileItem.file)
                                })
                            }}
                        />
                }
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(OptionPanel)