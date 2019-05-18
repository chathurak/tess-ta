import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import styles       from './styles'
import MuiExpansionPanel from '@material-ui/core/ExpansionPanel';
import MuiExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import MuiExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';

const ExpansionPanel = withStyles(styles.expansionPanel)(MuiExpansionPanel);
  
const ExpansionPanelSummary = withStyles(styles.expansionPanelSummary)(props => <MuiExpansionPanelSummary {...props} />);
  
ExpansionPanelSummary.muiName = 'ExpansionPanelSummary';
  
const ExpansionPanelDetails = withStyles(styles.expansionPanelDetails)(MuiExpansionPanelDetails);
  

class FileManagerPanel extends React.Component {

    state = {
        expanded: '1',

        // TODO: Load data from the server
        files: [
            {name: 'File 01', type: 'TEXT', createdOn: '2012.12.12 00:00'},
            {name: 'File 02', type: 'TIFF', createdOn: '2013.11.12 12:00'},
        ]
    };

    handleChange = panel => (event, expanded) => {
        this.setState({
            expanded: expanded ? panel : false,
        });
    };

    render() {
        const { expanded } = this.state;

        return (
            <div className={this.props.className}>
                {this.state.files.map((file, i) => {         
                    return (
                        <ExpansionPanel
                        square
                        expanded={expanded === i}
                        onChange={this.handleChange(i)}
                        >
                            <ExpansionPanelSummary>
                                <Typography>{file.name}</Typography>
                            </ExpansionPanelSummary>
                            <ExpansionPanelDetails>
                                <Typography>
                                Lorem ipsum dolor sit amet, 
                                </Typography>
                            </ExpansionPanelDetails>
                        </ExpansionPanel>
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
