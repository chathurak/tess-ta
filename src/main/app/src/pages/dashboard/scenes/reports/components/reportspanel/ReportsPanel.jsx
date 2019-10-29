import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import { LineChart, Line, CartesianGrid, XAxis, YAxis } from 'recharts';
import Card                     from '@material-ui/core/Card';
import CardActions              from '@material-ui/core/CardActions';
import Button                   from '@material-ui/core/Button';
import Typography               from '@material-ui/core/Typography';
import FormGroup                from '@material-ui/core/FormGroup';
import FormControlLabel         from '@material-ui/core/FormControlLabel';
import Checkbox                 from '@material-ui/core/Checkbox';
import { reportServices }       from '../../../../../../services'

class ReportsPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            checked_confusion_matrix: true,
            checked_confusion_summary: true,
            checked_diff: true,
            tasksSummary: []
        };
    }

    handleCheck = (name) => (event) => {
        this.setState({
            ...this.state,
            ["checked_" + name]: event.target.checked
        });
    };

    handleSaveToDrive = () => {
        reportServices.saveToDrive(
            this.checked_confusion_matrix,
            this.checked_confusion_summary,
            this.diff
        );
    };

    handleDownload = () => {
        reportServices.download(
            this.checked_confusion_matrix,
            this.checked_confusion_summary,
            this.diff
        );
    };

    componentDidMount() {
        reportServices
            .getTasksSummary()
            .then((res) => {
                this.setState({ tasksSummary: res });
            })
            .catch((error) => {
                console.log(error);
            });
    }

    render() {
        return (
            <div className={this.props.className}>
                <LineChart
                    style={styles.chart}
                    width={800}
                    height={300}
                    data={this.state.tasksSummary}
                >
                    <Line
                        type="monotone"
                        dataKey="uv"
                        stroke="#8884d8"
                        strokeWidth={2}
                    />
                    <CartesianGrid stroke="#ccc" />
                    <XAxis dataKey="name" />
                    <YAxis />
                </LineChart>

                <Card style={styles.card}>
                    <Typography gutterBottom variant="h5" component="h3">
                        Export reports
                    </Typography>
                    <Typography
                        variant="body2"
                        color="textSecondary"
                        component="p"
                    >
                        Select the reports you want to export.
                    </Typography>

                    <FormGroup style={styles.formGroup}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={
                                        this.state.checked_confusion_matrix
                                    }
                                    onChange={this.handleCheck(
                                        "confusion_matrix"
                                    )}
                                    value="confusion_matrix"
                                />
                            }
                            label="Confusion Matrix"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={
                                        this.state.checked_confusion_summary
                                    }
                                    onChange={this.handleCheck(
                                        "confusion_summary"
                                    )}
                                    value="confusion_summary"
                                />
                            }
                            label="Confusion Summary"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={this.state.checked_diff}
                                    onChange={this.handleCheck("diff")}
                                    value="diff"
                                />
                            }
                            label="Diff"
                        />
                    </FormGroup>

                    <CardActions style={{ justifyContent: "flex-end" }}>
                        <Button
                            variant="contained"
                            color="secondary"
                            onClick={this.handleDownload}
                        >
                            Download
                        </Button>
                        <Button
                            variant="contained"
                            color="secondary"
                            onClick={this.handleSaveToDrive}
                        >
                            Save to Drive
                        </Button>
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(ReportsPanel);
