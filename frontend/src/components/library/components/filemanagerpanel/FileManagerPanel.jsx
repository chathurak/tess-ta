import * as React                from 'react'
import {withStyles}              from '@material-ui/core/styles'
import LinearProgress            from '@material-ui/core/LinearProgress'
import {InsertDriveFile}         from '@material-ui/icons'
import {styles}                  from './styles'
import {taskServices}            from '../../../../services/task.services'
import {documentServices}        from '../../../../services/document.services'
import DeleteForeverOutlinedIcon from '@material-ui/icons/DeleteForeverOutlined'

class FileManagerPanel extends React.Component {

    constructor(props, context) {
        super(props, context)

        this.state = {
            documents         : [],
            tasks             : [],
            isLoading         : true,
            selectedDocumentId: null
        }
    }

    componentDidMount = () => {
        documentServices.getDocuments()
            .then(documents => {
                this.setState({documents: documents, isLoading: false})
            })
            .catch(err => {
                console.log(err)
            })
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
            <div className={this.props.className}>
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
                            {this.state.documents !== undefined && this.state.documents !== [] && this.state.documents.map(
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
        )
    }

}

export default withStyles(styles, {withTheme: true})(FileManagerPanel)
