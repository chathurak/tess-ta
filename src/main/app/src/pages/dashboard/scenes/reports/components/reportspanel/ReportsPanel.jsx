import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import { LineChart, Line, CartesianGrid, XAxis, YAxis } from 'recharts';

class ReportsPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            confusion_matrix_checked: true,
            confusion_summary_checked: true,
            diff_checked: true,
        };
    }

    render() {
        const data = [
            {name: 'Task 01', uv: 400, pv: 2400, amt: 2400},
            {name: 'Task 02', uv: 300, pv: 2400, amt: 2400},
            {name: 'Task 03', uv: 50, pv: 2400, amt: 2400},
            {name: 'Task 03', uv: 600, pv: 2400, amt: 2400},
        ]

        return (
            <div className={this.props.className}>
                <LineChart style={{margin: "0 auto", marginTop: 30}} width={800} height={300} data={data}>
                    <Line type="monotone" dataKey="uv" stroke="#8884d8" strokeWidth={2}/>
                    <CartesianGrid stroke="#ccc" />
                    <XAxis dataKey="name" />
                    <YAxis />
                </LineChart>
            </div>
        );
    }
}


export default withStyles(styles, { withTheme: true })(ReportsPanel);
