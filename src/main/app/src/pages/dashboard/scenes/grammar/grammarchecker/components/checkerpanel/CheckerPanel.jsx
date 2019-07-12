import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'

class CheckerPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = { };
    }

    render() {
        return (
            <div className={this.props.className}>
                {/* TODO: Implement */}
            </div>
        )
    }
}

export default withStyles(styles, { withTheme: true })(CheckerPanel);
