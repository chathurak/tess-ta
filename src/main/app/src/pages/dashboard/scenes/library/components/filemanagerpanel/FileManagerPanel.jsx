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


const ExpansionPanel = withStyles(styles.expansionPanel)(MuiExpansionPanel);
  
const ExpansionPanelSummary = withStyles(styles.expansionPanelSummary)(props => <MuiExpansionPanelSummary {...props} />);
  
ExpansionPanelSummary.muiName = 'ExpansionPanelSummary';
  
const ExpansionPanelDetails = withStyles(styles.expansionPanelDetails)(MuiExpansionPanelDetails);


class FileManagerPanel extends React.Component {

    constructor(props) {
        super(props);
    
        this.state = {
            expanded: '1',
    
            // TODO: Load data from the server
            files: [],
    
            taskInfo: [
                { id: '201212120000', createdAt: '2012.12.12 00:00', input: 'Mawbima', tessdata: 'v1', accuracy: '95%' },
                { id: '201212120101', createdAt: '2012.12.12 00:00', input: 'Mawbima', tessdata: 'v2', accuracy: '97%' },
            ],

        };
      }



    handleChange = panel => (event, expanded) => {
        this.setState({
            expanded: expanded ? panel : false,
        });
    };

    handleDelete = event => {
        var selectedIndex = this.state.expanded;
        
        // TODO: Implement delete function
    }

    componentDidMount() {
        LibraryService.getUserFiles()
        .then(res => {
            console.log(res);
            this.setState({
                files: res,
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
                {this.state.files.map((file, i) => {         
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
                                                            <TableCell align="right">Input File</TableCell>
                                                            <TableCell align="right">Tessdata</TableCell>
                                                            <TableCell align="right">Accuracy</TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                    {this.state.taskInfo.map(task => (
                                                        <TableRow key={task.id}>
                                                            <TableCell>{task.id}</TableCell>
                                                            <TableCell align="right">{task.createdAt}</TableCell>
                                                            <TableCell align="right">{task.input}</TableCell>
                                                            <TableCell align="right">{task.tessdata}</TableCell>
                                                            <TableCell align="right">{task.accuracy}</TableCell>
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
