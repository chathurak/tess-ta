import {withStyles}               from '@material-ui/core'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'
import 'filepond/dist/filepond.min.css'
import PropTypes                  from 'prop-types'
import * as React                 from 'react'
import {FilePond, registerPlugin} from 'react-filepond'
import {ACCESS_TOKEN}             from '../../../../../../constants/auth.constants'
import styles                     from './styles'

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
        console.log('FilePond instance has initialised', this.pond)
    }

    render() {
        const {classes} = this.props

        return (
            <div className={this.props.className}>
                {/* Pass FilePond properties as attributes */}
                <FilePond className={classes.filePond}
                          ref={ref => (this.pond = ref)}
                          files={this.state.files}
                          allowMultiple={true}
                          maxFiles={3}
                          server={
                              {
                                  url    : '/api/file',
                                  process: {
                                      url            : '/process',
                                      method         : 'POST',
                                      withCredentials: false,
                                      headers        : {
                                          'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
                                      },
                                      timeout        : 7000,
                                      onload         : null,
                                      onerror        : null,
                                      ondata         : null
                                  },
                                  revert : '/revert',
                                  restore: '/restore/',
                                  load   : '/load/',
                                  fetch  : '/fetch/'
                              }
                          }
                          oninit={() => this.handleInit()}
                          onupdatefiles={fileItems => {
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
