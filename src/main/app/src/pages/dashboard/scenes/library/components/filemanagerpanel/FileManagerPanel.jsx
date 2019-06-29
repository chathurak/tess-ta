import {withStyles}             from '@material-ui/core'
import PropTypes                from 'prop-types'
import * as React               from 'react'
import styles                   from './styles'
import MuiExpansionPanel        from '@material-ui/core/ExpansionPanel';
import MuiExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import MuiExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography               from '@material-ui/core/Typography';
import Grid                     from '@material-ui/core/Grid';
import Button                   from '@material-ui/core/Button';
import Table                    from '@material-ui/core/Table';
import TableBody                from '@material-ui/core/TableBody';
import TableCell                from '@material-ui/core/TableCell';
import TableHead                from '@material-ui/core/TableHead';
import TableRow                 from '@material-ui/core/TableRow';
import { documentServices }     from '../../../../../../services'
import { taskServices }         from '../../../../../../services'
import LinearProgress           from '@material-ui/core/LinearProgress';
import DialogInput              from '../../../../../../components/dialoginput/DialogInput';


const ExpansionPanel                = withStyles(styles.expansionPanel)(MuiExpansionPanel);
const ExpansionPanelSummary         = withStyles(styles.expansionPanelSummary)(props => <MuiExpansionPanelSummary {...props} />);
      ExpansionPanelSummary.muiName = 'ExpansionPanelSummary';
const ExpansionPanelDetails         = withStyles(styles.expansionPanelDetails)(MuiExpansionPanelDetails);


class FileManagerPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            expanded : "1",
            selectedDocumentId : null,
            files    : [],
            taskInfo : [],
            isLoading: true,
        };
    }

    handleChange = (panel) => (event, expanded) => {
        this.setState({
            expanded: expanded ? panel : false,
        });

        if (expanded) {
            this.setState({ selectedDocumentId: this.state.files[panel].id })

            taskServices.getTasks(this.state.files[panel].id)
                .then((tasks) => {
                    this.setState({
                        taskInfo : tasks,
                    });
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    };

    handleDelete = (event) => {
        documentServices.deleteDocument(this.state.selectedDocumentId);
    };

    handleRename = (newValue) => {
        documentServices.renameDocument(this.state.selectedDocumentId, newValue);
    }

    componentDidMount() {
        documentServices.getDocuments()
            .then((files) => {
                this.setState({
                    files    : files,
                    isLoading: false,
                });
            })
            .catch((error) => {
                console.log(error);
            });
    }

    render() {
        const { expanded } = this.state;
        const { classes }  = this.props;

        return (
            <div className = {this.props.className}>
                {this.state.isLoading && <LinearProgress />}
                {this.state.files !== [] &&
                    this.state.files.map((file, i) => {
                        return (
                            <div key = {i}>
                                <ExpansionPanel
                                    square
                                    expanded = {expanded === i}
                                    onChange = {this.handleChange(i)}
                                >
                                    <ExpansionPanelSummary>
                                        <Grid container spacing = {10}>
                                            <Grid item xs>
                                                <Typography
                                                    style = {{ fontWeight: 500 }}
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
                                                        textAlign: "right"
                                                    }}
                                                >
                                                    {file.createdAt}
                                                </Typography>
                                            </Grid>
                                        </Grid>
                                    </ExpansionPanelSummary>
                                    <ExpansionPanelDetails>
                                        <span  style = {{ width: "100%" }}>
                                            {/* Table of details */}
                                            {i === this.state.expanded && (
                                                <Table
                                                    className = {classes.table}
                                                    style     = {{ width: "100%" }}
                                                >
                                                    <TableHead>
                                                        <TableRow>
                                                            <TableCell>
                                                                Task Name
                                                            </TableCell>
                                                            <TableCell>
                                                                Tessdata Name
                                                            </TableCell>
                                                            <TableCell align = "right">
                                                                Created At
                                                            </TableCell>
                                                            <TableCell align = "right">
                                                                Modified At
                                                            </TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                        {this.state.taskInfo != null && this.state.taskInfo.map(
                                                            (task, i) => (
                                                                <TableRow
                                                                    key = {i}
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
                                                                    <TableCell align = "right">
                                                                        {
                                                                            task.createdAt
                                                                        }
                                                                    </TableCell>
                                                                    <TableCell align = "right">
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

                                                <Button
                                                    variant   = "outlined"
                                                    color     = "secondary"
                                                    className = {classes.button}
                                                    onClick   = {this.handleDelete}
                                                >
                                                    DELETE
                                                </Button>
                                            </div>
                                        </span>
                                    </ExpansionPanelDetails>
                                </ExpansionPanel>
                            </div>
                        );
                    })}
            </div>
        );
    }
}

FileManagerPanel.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
};

export default withStyles(styles, { withTheme: true })(FileManagerPanel);
