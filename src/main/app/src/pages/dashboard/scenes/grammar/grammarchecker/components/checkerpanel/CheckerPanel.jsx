import {withStyles}             from '@material-ui/core'
import * as React               from 'react'
import styles                   from './styles'
import {connect}                from 'react-redux'
import Button                   from '@material-ui/core/Button';
import { grammarServices }      from '../../../../../../../services'
import { grammar }              from '../../../../../../../helpers/grammar'
import DialogSelect             from '../../../../../../../components/dialogselect/DialogSelect';
import DialogAction             from '../../../../../../../components/dialogaction/DialogAction';
import Table                    from '@material-ui/core/Table';
import TableBody                from '@material-ui/core/TableBody';
import TableCell                from '@material-ui/core/TableCell';
import TableHead                from '@material-ui/core/TableHead';
import TableRow                 from '@material-ui/core/TableRow';
import Grid                     from '@material-ui/core/Grid';
import Typography               from '@material-ui/core/Typography';
import Slider                   from '@material-ui/core/Slider';
import ZoomIn from '@material-ui/icons/ZoomIn';


class CheckerPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            taskName         : '',
            inputText        : '',
            data             : [],
            currentWordIndex: 0,
            currentLetterIndex: 0,
            currentSuggestions: [],
            textSize: 18,
        };
    }

    handleProcess = (selectedTask) => {
        if (!selectedTask) {
            return;
        }

        grammarServices.process(selectedTask.value)
            .then((res) => {
                this.setState({
                    taskName  : selectedTask.label,
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

    handleEditLetter = (index, indexLetter) => {
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

    getStateDataAsLines = () => {
        var dataLines = [[]]
        this.state.data.forEach(function(word) {
            if (word.value === 'NEW_LINE') {
                dataLines.push([])
            } else {
                dataLines[dataLines.length - 1].push(word)
            }
        })
        return dataLines;
    }

    handleTextSizeChange = (event, newValue) => {
        this.setState({textSize: newValue})
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

                    {/* Control bar */}
                    <Grid container style={styles.centerAll}>

                        {/* Processs button */}
                        <Grid item xs={2}>
                            <Button variant="contained" color="secondary"  onClick={(e) => this.handleProcess(selectedTask)}>Process</Button>
                        </Grid>

                        <Grid item xs={1}>
                            <Typography id="discrete-slider"> {this.state.taskName} </Typography>
                        </Grid>

                        <Grid item xs={7}> </Grid>

                        {/* Zoom */}
                        <Grid item xs={2}>
                            <Grid container >
                                <Grid item>
                                    <ZoomIn />
                                </Grid>
                                
                                <Grid item xs>
                                    <Slider
                                        aria-labelledby="continuous-slider"
                                        valueLabelDisplay="auto"
                                        step={1}
                                        marks
                                        min={9}
                                        max={30}
                                        value={this.state.textSize}
                                        onChange={this.handleTextSizeChange}
                                    />
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>

                    {/* Editing Area */}
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell>Before process</TableCell>
                                <TableCell>After process</TableCell>
                            </TableRow>
                        </TableHead>
                        
                        <TableBody>
                            {this.getStateDataAsLines().map((dataLine, index) => (
                                <TableRow key={index}>
                                    <TableCell style={{fontSize: this.state.textSize}}>
                                        {dataLine.map((word, index) => (
                                            <span key={index}> {word.value} </span>
                                        ))}
                                    </TableCell>

                                    <TableCell style={{fontSize: this.state.textSize}}>
                                        {dataLine.map((word, index) => [
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
                                        ])}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>

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