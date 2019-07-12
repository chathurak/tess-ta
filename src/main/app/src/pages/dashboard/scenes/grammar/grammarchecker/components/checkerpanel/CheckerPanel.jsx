import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import {connect}                from 'react-redux'
import Button                   from '@material-ui/core/Button';
import { grammarServices }      from '../../../../../../../services'


class CheckerPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            inputText: "",
            data: [],
        };
    }

    handleCheck = (selectedTask) => {
        if (!selectedTask) {
            return;
        }

        grammarServices.process(selectedTask.value)
            .then((res) => {
                this.setState({
                    inputText: res.input,
                    data: res.output
                })
            })
            .catch((error) => {
                console.log(error);
            });
    };

    render() {
        const {selectedTask}   = this.props

        return (
            <div className={this.props.className}>
                <div style={styles.workspace}>
                    <Button variant="contained" color="primary"  onClick={(e) => this.handleCheck(selectedTask)}>Process</Button>
                
                    <p> { this.state.inputText } </p>
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    const {selectedDocument, selectedTask} = state.taskPickerReducer
    return {
        selectedDocument,
        selectedTask,
    }
}

const styledComponent = withStyles(styles, {withTheme: true})(CheckerPanel)
export default connect(mapStateToProps)(styledComponent)