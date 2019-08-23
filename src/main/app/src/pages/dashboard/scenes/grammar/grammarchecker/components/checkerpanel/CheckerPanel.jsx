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
            currentWordIndex: 0,
            currentLetterIndex: 0,
            currentSuggestions: [],
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
                })
            })
            .catch((error) => {
                console.log(error);
            });
    };

    handleEditWord = (index) => {
        var s = this.state.data[index].suggestions.map(s => {
            return s.value;
        });

        this.setState({
            currentWordIndex: index,
            currentLetterIndex: 0,
            currentSuggestions: s
        })
        this.dialogSelectSuggestion.show();
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

    handleSuggestionSelector = (selectedIndex) => {
        var data = this.state.data;
        data[this.state.currentWordIndex].selected = selectedIndex;
        this.setState({data: data});
    }

    // Get styles for a letter or a word
    getTextStyle = (item) => {
        if (item.flags.length === 0) {
            return;
        }

        switch (item.flags[0]) {
            case 'CHARACTER_LEGITIMACY_ERROR':
                return styles.flagLetterCharacterLegitimacyError;
            case 'GRAMMAR_LEGITIMACY_ERROR':
                return styles.flagLetterGrammarLegitimacyError;
            case 'CHANGED':
                return styles.flagLetterChanged;
            case 'OPTIONAL':
                return styles.flagLetterOptional;
            case 'NOT_IN_DICTIONARY': // Word style 
                return styles.flagWordNotInDictionary;
            case 'HAS_SUGGESTIONS': // Word style
                return styles.flagWordHasSuggestions;
            default:
                return;
        }
    }

    render() {
        const {selectedTask}   = this.props

        return (
            <div className={this.props.className}>
                <div style={styles.workspace}>

                    {/* Suggestions selector dialog box */}
                    <DialogSelect
                        title   = 'Suggestions'
                        message = 'Select an alternative word'
                        items   = {this.state.currentSuggestions}
                        ref     = {dialog => this.dialogSelectSuggestion = dialog}
                        onOk    = {this.handleSuggestionSelector}
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

                    {/* Processs button */}
                    <Button variant="contained" color="primary"  onClick={(e) => this.handleProcess(selectedTask)}>Process</Button>

                    {/* Editing Area */}
                    <div>
                        {this.state.data.map((word, index) => {
                            return (
                                <span key={index}>
                                    {/* Level -1 word */}
                                    {word.level === -1 && word.value === 'NEW_LINE' && <br/>}

                                    {/* Level 0 word */}
                                    {word.level === 0 && word.value}

                                    {/* Level 1 word */}
                                    {word.level === 1 && (
                                        <span style={styles.flagWordIllegitimate}>
                                        {word.letters.map((letter, indexLetter) => (
                                            letter.flags.length > 0 ? (
                                                letter.isModified ? (
                                                    <span key={indexLetter}>
                                                        <span onClick={this.handleEditLetter.bind(this, index, indexLetter)} style={styles.flagLetterDeleted}>
                                                            {letter.value}
                                                        </span>
                                                        <span onClick={this.handleEditLetter.bind(this, index, indexLetter)} style={styles.flagLetterInserted}>
                                                            {letter.newValue}
                                                        </span>
                                                    </span>
                                                ) : (
                                                    <span key={indexLetter} onClick={this.handleEditLetter.bind(this, index, indexLetter)} style={this.getTextStyle(letter)}>
                                                        {letter.value}
                                                    </span>
                                                )
                                            ) : (
                                                <span key={indexLetter} style={this.getTextStyle(letter)}>{letter.value}</span>
                                            )
                                        ))}
                                        </span>
                                    )}

                                    {/* Level 2 word */}
                                    {word.level === 2 && (
                                        word.selected === -1 ? (
                                            <span onClick={this.handleEditWord.bind(this, index)} style={this.getTextStyle(word)}>{word.value}</span>
                                        ) : (
                                            <span onClick={this.handleEditWord.bind(this, index)} style={this.getTextStyle(word.suggestions[word.selected])}>{word.suggestions[word.selected].value}</span>
                                        )
                                    )}
                                    
                                    {/* Space after words */}
                                    {word.level !== -1 && <span>&nbsp;</span>}
                                </span>
                            )
                        })}
                    </div>

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