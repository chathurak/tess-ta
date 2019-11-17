import {withStyles}                     from '@material-ui/core'
import PropTypes                        from 'prop-types'
import * as React                       from 'react'
import MuiExpansionPanel                from '@material-ui/core/ExpansionPanel'
import MuiExpansionPanelSummary         from '@material-ui/core/ExpansionPanelSummary'
import MuiExpansionPanelDetails         from '@material-ui/core/ExpansionPanelDetails'
import Typography                       from '@material-ui/core/Typography'
import Grid                             from '@material-ui/core/Grid'
import Table                            from '@material-ui/core/Table'
import TableBody                        from '@material-ui/core/TableBody'
import TableCell                        from '@material-ui/core/TableCell'
import TableHead                        from '@material-ui/core/TableHead'
import TableRow                         from '@material-ui/core/TableRow'
import LinearProgress                   from '@material-ui/core/LinearProgress'
import {documentServices, taskServices} from '../../services'
import DialogInput                      from '../../components/dialoginput/DialogInput'
import DialogConfirm                    from '../../components/dialogconfirm/DialogConfirm'
import {styles}                         from './styles'


const ExpansionPanel          = withStyles(styles.expansionPanel)(MuiExpansionPanel)
const ExpansionPanelSummary   = withStyles(styles.expansionPanelSummary)(props =>
    <MuiExpansionPanelSummary {...props} />)
ExpansionPanelSummary.muiName = 'ExpansionPanelSummary'
const ExpansionPanelDetails   = withStyles(styles.expansionPanelDetails)(MuiExpansionPanelDetails)


class FileManagerPanel extends React.Component {
    handleRename = (newValue) => {
        documentServices.renameDocument(this.state.selectedDocumentId, newValue)
            .then(() => {
                this.componentDidMount()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    render() {
        const {expanded} = this.state
        const {classes}  = this.props

        return (
            <div className={this.props.className}>
                {/*{this.state.isLoading && <LinearProgress/>}*/}
                {this.state.files !== [] && this.state.files.map((file, i) => {
                    return (
                        <div key={i}>
                            <ExpansionPanel
                                square
                                expanded={expanded === i}
                                onChange={this.handleChange(i)}
                            >
                                <ExpansionPanelSummary>
                                    <Grid container spacing={10}>
                                        <Grid item xs>
                                            <Typography
                                                style={{fontWeight: 500}}
                                            >
                                                {file.name}
                                            </Typography>
                                        </Grid>
                                        <Grid item xs>
                                            <Typography>
                                                {file.originalFileName != null && file.originalFileName.split('.').pop().toUpperCase()}
                                            </Typography>
                                        </Grid>
                                        <Grid item xs>
                                            <Typography
                                                style={{
                                                    textAlign: 'right'
                                                }}
                                            >
                                                {file.createdAt}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </ExpansionPanelSummary>
                                <ExpansionPanelDetails>
                                        <span style={{width: '100%'}}>
                                            {/* Table of details */}
                                            {i === this.state.expanded && (
                                                <Table
                                                    className={classes.table}
                                                    style={{width: '100%'}}
                                                >
                                                    <TableHead>
                                                        <TableRow>
                                                            <TableCell>
                                                                Task Name
                                                            </TableCell>
                                                            <TableCell>
                                                                Tessdata Name
                                                            </TableCell>
                                                            <TableCell align="right">
                                                                Created At
                                                            </TableCell>
                                                            <TableCell align="right">
                                                                Modified At
                                                            </TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                        {this.state.taskInfo != null && this.state.taskInfo.map(
                                                            (task, i) => (
                                                                <TableRow
                                                                    key={i}
                                                                >
                                                                    <TableCell>
                                                                        {
                                                                            task.name
                                                                        }
                                                                    </TableCell>
                                                                    <TableCell>
                                                                        {
                                                                            task.tessdataName
                                                                        }
                                                                    </TableCell>
                                                                    <TableCell align="right">
                                                                        {
                                                                            task.createdAt
                                                                        }
                                                                    </TableCell>
                                                                    <TableCell align="right">
                                                                        {
                                                                            task.updatedAt
                                                                        }
                                                                    </TableCell>
                                                                </TableRow>
                                                            )
                                                        )}
                                                    </TableBody>
                                                </Table>
                                            )}

                                            <br/>
                                            <div style={styles.buttonBox}>
                                                <DialogInput
                                                    label="Rename"
                                                    title="Rename document"
                                                    message="Enter a new document name"
                                                    value={file.name}
                                                    onOk={this.handleRename}
                                                />
                                                <DialogConfirm
                                                    style={styles.button}
                                                    label="Delete"
                                                    title="Delete document and its ALL tasks?"
                                                    message="Are you sure you want to delete the document and its created tasks?"
                                                    onOk={this.handleDelete}
                                                />

                                            </div>
                                        </span>
                                </ExpansionPanelDetails>
                            </ExpansionPanel>
                        </div>
                    )
                })}
            </div>
        )
    }
}

FileManagerPanel.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(FileManagerPanel)
