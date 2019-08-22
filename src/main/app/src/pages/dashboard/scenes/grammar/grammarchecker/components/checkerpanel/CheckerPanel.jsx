import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import {connect}                from 'react-redux'
import Button                   from '@material-ui/core/Button';
import { grammarServices }      from '../../../../../../../services'
import { grammar }              from '../../../../../../../helpers/grammar'
import DialogSelect            from '../../../../../../../components/dialogselect/DialogSelect';
import DialogAction            from '../../../../../../../components/dialogaction/DialogAction';


class CheckerPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            inputText        : "",
            data             : [],
            dialogSelectItems: []
        };
    }

    handleProcess = (selectedTask) => {
        if (!selectedTask) {
            return;
        }

        grammarServices.process(selectedTask.value)
            .then((res) => {
                this.setState({
                    inputText : res.input,
                    outputText: JSON.stringify(res.output),
                    data      : grammar.docToModel(res.output),
                    currentWordIndex: 0,
                    currentLetterIndex: 0,
                })
            })
            .catch((error) => {
                console.log(error);
            });
    };

    handleEditWord = (index) => {
        // TODO: Update dialog content
        console.log("editing word");
    };

    handleEditLetter = (index, indexLetter, event) => {
        this.setState({currentWordIndex: index, currentLetterIndex: indexLetter})
        var letter = this.state.data[index].letters[indexLetter];

        if (letter.isModified) {
            this.dialogActionRestore.show();
        } else {
            this.dialogActionDelete.show();
        }

    };

    handleDeleteLetter = () => {
        var data = this.state.data;
        data[this.state.currentWordIndex].letters[this.state.currentLetterIndex].isModified = true;
        this.setState({data: data});
    }

    handleRestoreLetter = () => {
        var data = this.state.data;
        data[this.state.currentWordIndex].letters[this.state.currentLetterIndex].isModified = false;
        this.setState({data: data});
    }

    render() {
        const {selectedTask}   = this.props

        return (
            <div className={this.props.className}>
                <div style={styles.workspace}>
                    <Button variant="contained" color="primary"  onClick={(e) => this.handleProcess(selectedTask)}>Process</Button>
                
                    <p> { this.state.inputText } </p>
                    <p> { this.state.outputText } </p>

                    {/* Suggestions selector dialog box */}
                    <DialogSelect
                        title   = 'Suggestions'
                        message = 'Select an alternative word'
                        items   = {this.state.dialogSelectItems}
                        ref     = {dialog => this.dialogSelect = dialog}
                        onOk    = {this.handleEditWord}
                    />

                    {/* Delete dialog box */}
                    <DialogAction
                        title   = 'Delete'
                        message = 'No suggestions found. Click `Delete` to delete the letter.'
                        action  = 'DELETE'
                        ref     = {dialog => this.dialogActionDelete = dialog}
                        onOk    = {this.handleDeleteLetter}
                    />

                    {/* Restore dialog box */}
                    <DialogAction
                        title   = 'Restore'
                        message = 'The letter is deleted. Click `Restore` to undo it.'
                        action  = 'Restore'
                        ref     = {dialog => this.dialogActionRestore = dialog}
                        onOk    = {this.handleRestoreLetter}
                    />

                    {this.state.data.map((word, index) => {
                        return (
                            <span key={index}>
                                {/* Level -1 word */}
                                {word.level == -1 && word.value == 'NEW_LINE' && <br/>}

                                {/* Level 0 word */}
                                {word.level == 0 && word.value}

                                {/* Level 1 word */}
                                {word.level == 1 && (
                                    word.letters.map((letter, indexLetter) => (
                                        letter.flags.length > 0 ? (
                                            <span key={indexLetter} onClick={this.handleEditLetter.bind(this, index, indexLetter)}>{letter.value}</span>
                                        ) : (
                                            <span key={indexLetter}>{letter.value}</span>
                                        )
                                    ))
                                )}

                                {/* Level 2 word */}
                                {word.level == 2 && <span onClick={this.handleEditWord.bind(this, index)}>{word.value}</span>}

                                &nbsp;
                            </span>

                            
                        )
                    })}
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