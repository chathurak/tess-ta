import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './styles'
import MuiExpansionPanel from '@material-ui/core/ExpansionPanel';
import MuiExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import MuiExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Input from '@material-ui/core/Input';
import { LibraryService } from '../../../../../../services/library.service'
import LinearProgress from '@material-ui/core/LinearProgress';




const ExpansionPanel = withStyles(styles.expansionPanel)(MuiExpansionPanel);
  
const ExpansionPanelSummary = withStyles(styles.expansionPanelSummary)(props => <MuiExpansionPanelSummary {...props} />);
  
ExpansionPanelSummary.muiName = 'ExpansionPanelSummary';
  
const ExpansionPanelDetails = withStyles(styles.expansionPanelDetails)(MuiExpansionPanelDetails);


class FileManagerPanel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            expanded: '1',
            files: [],
            taskInfo: [],
            isLoading: true,
        };
      }

    

    handleChange = panel => (event, expanded) => {
        this.setState({
            expanded: expanded ? panel : false,
        });

        if (expanded) {
            this.setState({ isLoading: true })
            LibraryService.getTasks(this.state.files[panel].name)
            .then(res => {
                console.log(res.data);
    
                this.setState({
                    taskInfo: res.data,
                    isLoading: false
                })
            })
            .catch(error => {
                console.log(error)
            })
        }
    };

    handleDelete = event => {
        var selectedIndex = this.state.expanded;
        
        // TODO: Implement delete function
    }

    componentDidMount() {
        LibraryService.getUserFiles()
        .then(res => {
            console.log(res.data);

            this.setState({
                files: res.data,
                isLoading: false
            })
        })
        .catch(error => {
            console.log(error)
        })
    }

    render() {
        const { expanded } = this.state;
        const { classes } = this.props;

        return (
            <div className={this.props.className}>
                {this.state.isLoading && <LinearProgress />}
                {this.state.files !== [] && this.state.files.map((file, i) => {         
                    return (
                        <div key={i}>
                            <ExpansionPanel
                            square
                            expanded={expanded === i}
                            onChange={this.handleChange(i)}
                            >
                                <ExpansionPanelSummary>
                                    <Grid container spacing={24}>
                                        <Grid item xs>
                                            <Typography style={{fontWeight: 500}}>{file.name}</Typography>
                                        </Grid>
                                        <Grid item xs>
                                            <Typography>{file.isText ? "TEXT" : "TIFF"}</Typography>
                                        </Grid>
                                        <Grid item xs >
                                            <Typography style={{textAlign: 'right'}}>{file.createdAt}</Typography>
                                        </Grid>
                                    </Grid>
                                    
                                </ExpansionPanelSummary>
                                <ExpansionPanelDetails>
                                    <span style={{width: '100%'}}>
                                        <Input value={this.state.name}/>
                                        <Button variant="contained" color="primary" className={classes.button}>
                                            RENAME
                                        </Button>
                                        <Button variant="contained" color="secondary" className={classes.button} onClick={this.handleDelete}>
                                            DELETE
                                        </Button>

                                        {/* Table of details */}
                                            { i === this.state.expanded &&
                                                <Table className={classes.table} style={{width: '100%'}}>
                                                    <TableHead>
                                                        <TableRow>
                                                            <TableCell>ID</TableCell>
                                                            <TableCell align="right">Created On</TableCell>
                                                            <TableCell align="right">Tessdata</TableCell>
                                                            <TableCell align="right">Accuracy</TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                    {this.state.taskInfo.map((task, i) => (
                                                        <TableRow key={i}>
                                                            <TableCell>{task.key}</TableCell>
                                                            <TableCell align="right">{task.createdAt}</TableCell>
                                                            <TableCell align="right">{task.tessdataName}</TableCell>
                                                            <TableCell align="right">{task.accuracy}%</TableCell>
                                                        </TableRow>
                                                    ))}
                                                    </TableBody>
                                                </Table>
                                            }

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
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(FileManagerPanel)
