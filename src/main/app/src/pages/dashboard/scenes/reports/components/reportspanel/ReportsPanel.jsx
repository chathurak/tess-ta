import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'

class ReportsPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div className = {this.props.className}>
                Reports
            </div>
        );
    }
}


export default withStyles(styles, { withTheme: true })(ReportsPanel);
