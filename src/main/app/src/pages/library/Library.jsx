import {withStyles}                     from '@material-ui/core'
import * as React                       from 'react'
import {styles}                         from './styles'
import LinearProgress                   from '@material-ui/core/LinearProgress'
import {documentServices, taskServices} from '../../services'
import {FilePond, registerPlugin}       from 'react-filepond'
import {config as filepondConfig}       from './filepond.config'
import FilePondPluginImagePreview       from 'filepond-plugin-image-preview'
import DeleteForeverOutlinedIcon        from '@material-ui/icons/DeleteForeverOutlined'

import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css'
import 'filepond/dist/filepond.min.css'
import {InsertDriveFile}                from '@material-ui/icons'

// register the plugins
registerPlugin(FilePondPluginImagePreview)

class Library extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            documents         : [],
            tasks             : [],
            isLoading         : true,
            selectedDocumentId: null,

            // Set initial files, type 'local' means this is a file
            // that has already been uploaded to the server (see docs)
            files: []
        }

        this.componentDidMount = this.componentDidMount.bind(this)
    }

    componentDidMount() {
        documentServices.getDocuments()
            .then(documents => {
                this.setState({documents: documents, isLoading: false})
            })
            .catch(err => {
                console.log(err)
            })
    }

    handlePondInit() {
        // console.log('FilePond instance has initialised', this.pond)
    }

    handleDocumentSelect = documentId => {
        this.setState({
            selectedDocumentId: documentId
        })

        taskServices.getTasks(documentId)
            .then((tasks) => {
                this.setState({tasks: tasks})
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleDocumentDelete = documentId => {
        documentServices.deleteDocument(documentId)
            .then(() => {
                this.componentDidMount()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleRename = newValue => {
        documentServices.renameDocument(this.state.selectedDocumentId, newValue)
            .then(() => {
                this.componentDidMount()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
                <div className={classes.workspacePanel}>
                    {this.state.isLoading && <LinearProgress/>}
                    {
                        this.state.selectedDocumentId ?
                            <div className={classes.documentPane}>
                                {this.state.tasks !== [] && this.state.tasks.map(
                                    (task, i) => (
                                        <div key={i} className={classes.document}>
                                            <p>{task.name}</p>
                                        </div>
                                    )
                                )}
                            </div> :
                            <div className={classes.documentPane}>
                                <div className={classes.documentPaneTitle}>
                                    <p className={classes.documentPaneTitleName}>Name</p>
                                </div>
                                {this.state.documents !== [] && this.state.documents.map(
                                    (document, i) => (
                                        <div key={i} className={classes.document}
                                             onClick={() => this.handleDocumentSelect(document.id)}>
                                            <InsertDriveFile className={classes.documentIcon}/>
                                            <p className={classes.documentName}>{document.name}</p>
                                            <DeleteForeverOutlinedIcon className={classes.documentDeleteIcon}
                                                                       onClick={() => this.handleDocumentDelete(document.id)}/>
                                        </div>
                                    )
                                )}
                            </div>
                    }
                </div>
                <div className={classes.optionPanel}>
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
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Library)
