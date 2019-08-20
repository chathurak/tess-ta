import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import {connect}                from 'react-redux'
import Button                   from '@material-ui/core/Button';
import { grammarServices }      from '../../../../../../../services'
import { grammar }              from '../../../../../../../helpers/grammar'
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import InputLabel from '@material-ui/core/InputLabel';
import Input from '@material-ui/core/Input';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import DialogSelect            from '../../../../../../../components/dialogselect/DialogSelect';


class CheckerPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            inputText: "",
            data: [],
            dialogSelectItems: [
                "A",
                "B",
            ]
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
                    outputText: JSON.stringify(res.output),
                    data: grammar.docToModel(res.output)
                })
            })
            .catch((error) => {
                console.log(error);
            });
    };

    handleEditWord = (index) => {
        console.log("editing word");
    };

    handleEditLetter = (index) => {
        console.log("editing letter");
    };

    handleEdit = () => {
        // TODO: Update dialog content
        this.dialogSelect.handleClickOpen();
    };

    handleDialogResponse = (result) => {
        console.log(result)
    }

    render() {
        const {selectedTask}   = this.props

        return (
            <div className={this.props.className}>
                <div style={styles.workspace}>
                    <Button variant="contained" color="primary"  onClick={(e) => this.handleCheck(selectedTask)}>Process</Button>
                
                    <p> { this.state.inputText } </p>
                    <p> { this.state.outputText } </p>

                    <DialogSelect
                        title="Suggestions"
                        message="Select an alternative word"
                        items={this.state.dialogSelectItems}
                        ref={dialog => this.dialogSelect = dialog}
                        onOk={this.handleEditWord}
                    />

                    <Button onClick={this.handleEdit.bind(this)}>test</Button>


                    {this.state.data.map((word, index) => (
                        <span key={index} onClick={this.handleEdit}>{word.value}</span>
                    ))}
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