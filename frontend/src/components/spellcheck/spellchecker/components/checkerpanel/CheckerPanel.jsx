import {withStyles}      from '@material-ui/core/styles'
import * as React        from 'react'
import styles            from './styles'
import Button            from '@material-ui/core/Button'
import {grammarServices} from '../../../../../services/grammar.services'
import {grammar}         from '../../../../../helpers/grammar'
import DialogSelect      from '../../../../dialogselect/DialogSelect'
import DialogAction      from '../../../../dialogaction/DialogAction'
import Table             from '@material-ui/core/Table'
import TableBody         from '@material-ui/core/TableBody'
import TableCell         from '@material-ui/core/TableCell'
import TableHead         from '@material-ui/core/TableHead'
import TableRow          from '@material-ui/core/TableRow'
import Grid              from '@material-ui/core/Grid'
import Typography        from '@material-ui/core/Typography'
import Slider            from '@material-ui/core/Slider'
import ZoomIn            from '@material-ui/icons/ZoomIn'
import Popover           from '@material-ui/core/Popover'
import * as FileSaver    from 'file-saver'

class CheckerPanel extends React.Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            taskName          : '',
            inputText         : '',
            dataLines         : [],
            currentLineIndex  : 0,
            currentWordIndex  : 0,
            currentLetterIndex: 0,
            currentSuggestions: [],
            textSize          : 18,
            legendOpen        : false,
            legendAnchorEl    : null
        }
    }

    handleProcess = (selectedTask) => {
        if (!selectedTask) {
            return
        }
        grammarServices.process(selectedTask.value)
            .then((res) => {
                this.setState({
                    taskName  : selectedTask.label,
                    inputText : res.input,
                    outputText: JSON.stringify(res.output),
                    dataLines : grammar.docToDataLines(res.output)
                })
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleEditWord = (indexLine, indexWord) => {
        var s = this.state.dataLines[indexLine][indexWord].suggestions.map(s => {
            return s.value
        })

        this.setState({
            currentLineIndex  : indexLine,
            currentWordIndex  : indexWord,
            currentLetterIndex: 0,
            currentSuggestions: s
        })
        this.dialogSelectSuggestion.show()
    }

    handleEditLetter = (indexLine, indexWord, indexLetter) => {
        this.setState({
            currentLineIndex  : indexLine,
            currentWordIndex  : indexWord,
            currentLetterIndex: indexLetter
        })
        var letter = this.state.dataLines[indexLine][indexWord].letters[indexLetter]

        if (letter.isModified) {
            this.dialogActionRestore.show()
        } else {
            this.dialogActionDelete.show()
        }

    }

    handleDeleteLetter = () => {
        var data                                                                                                         = this.state.dataLines
        data[this.state.currentLineIndex][this.state.currentWordIndex].letters[this.state.currentLetterIndex].isModified = true
        this.setState({data: data})
    }

    handleRestoreLetter = () => {
        var data                                                                                                         = this.state.dataLines
        data[this.state.currentLineIndex][this.state.currentWordIndex].letters[this.state.currentLetterIndex].isModified = false
        this.setState({data: data})
    }

    handleSuggestionSelector = (selectedIndex) => {
        var data                                                                = this.state.dataLines
        data[this.state.currentLineIndex][this.state.currentWordIndex].selected = selectedIndex
        this.setState({data: data})
    }

    handleExport = (selectedTask) => {
        const blob = new Blob([grammar.dataLinesToText(this.state.dataLines)], {type: 'text/plain;charset=utf-8'})
        FileSaver.saveAs(blob, this.state.taskName)
    }

    handleSave = (selectedTask) => {
        const blob = new Blob([JSON.stringify(this.state.dataLines)], {type: 'application/json;charset=utf-8'})
        FileSaver.saveAs(blob, this.state.taskName)
    }

    handleOpen = (event) => {
        var file      = event.target.files[0]
        var reader    = new FileReader()
        reader.onload = (event) => {
            console.log(event.target.result)
        }

        reader.readAsText(file)
    }

    // Get styles for a letter or a word
    getTextStyle = (item) => {
        if (item.flags.length === 0) {
            return
        }

        switch (item.flags[0]) {
            case 'CHARACTER_LEGITIMACY_ERROR':
                return styles.flagLetterCharacterLegitimacyError
            case 'GRAMMAR_LEGITIMACY_ERROR':
                return styles.flagLetterGrammarLegitimacyError
            case 'CHANGED':
                return styles.flagLetterChanged
            case 'OPTIONAL':
                return styles.flagLetterOptional
            case 'NOT_IN_DICTIONARY': // Word style
                return styles.flagWordNotInDictionary
            case 'HAS_SUGGESTIONS': // Word style
                return styles.flagWordHasSuggestions
            default:
                return
        }
    }

    getStateDataAsLines = () => {
        var dataLines = [[]]
        this.state.dataLines.forEach(function (word) {
            if (word.value === 'NEW_LINE') {
                dataLines.push([])
            } else {
                dataLines[dataLines.length - 1].push(word)
            }
        })
        return dataLines
    }

    handleTextSizeChange = (event, newValue) => {
        this.setState({textSize: newValue})
    }

    handleLegendClose = () => {
        this.setState({legendAnchorEl: null})
    }

    handleLegendOpen = (event) => {
        // this.setAnchorEl(event.currentTarget)

        this.setState({legendAnchorEl: event.currentTarget})
        // console.log(this.state)
    }

    legendOpen = () => {
        return Boolean(this.state.legendAnchorEl)
    }

    render() {
        const {selectedTask} = this.props
        const legendOpen     = Boolean(this.state.legendAnchorEl)
        const id             = this.state.legendOpen ? 'simple-popover' : undefined

        return (
            <div className={this.props.className}>
                <div style={styles.workspace}>

                    {/* Suggestions selector dialog box */}
                    <DialogSelect
                        title='Suggestions'
                        message='Select an alternative word'
                        items={this.state.currentSuggestions}
                        ref={dialog => this.dialogSelectSuggestion = dialog}
                        onOk={this.handleSuggestionSelector}
                    />

                    {/* Delete dialog box */}
                    <DialogAction
                        title='Delete'
                        message='No suggestions found. Click `Delete` to delete the letter.'
                        action='DELETE'
                        ref={dialog => this.dialogActionDelete = dialog}
                        onOk={this.handleDeleteLetter}
                    />

                    {/* Restore dialog box */}
                    <DialogAction
                        title='Restore'
                        message='The letter is deleted. Click `Restore` to undo it.'
                        action='Restore'
                        ref={dialog => this.dialogActionRestore = dialog}
                        onOk={this.handleRestoreLetter}
                    />

                    {/* Legend */}
                    <Popover
                        open={legendOpen}
                        anchorEl={this.state.legendAnchorEl}
                        anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
                        transformOrigin={{vertical: 'top', horizontal: 'left'}}
                        onClose={this.handleLegendClose}
                        disableRestoreFocus
                    >
                        <div style={styles.legendPanel}>
                            :: Characters ::
                            <div style={styles.flagLetterCharacterLegitimacyError}>RED: CHARACTER LEGITIMACY ERROR</div>
                            <div style={styles.flagLetterGrammarLegitimacyError}>ORANGE: GRAMMAR LEGITIMACY ERROR</div>
                            <div style={styles.flagLetterChanged}>BLUE: LETTER CHANGED</div>
                            <div style={styles.flagLetterOptional}>GREEN: OPTIONAL CHARACTER</div>
                            <div style={styles.flagLetterDeleted}>GREY & STRIKETHROUGH: DELETED CHARACTER</div>
                            <br/>:: Words ::
                            <div style={styles.flagWordNotInDictionary}>HIGHLIGHTED & UNDERLINED: NOT IN
                                DICTIONARY</div>
                            <div style={styles.flagWordHasSuggestions}>BOLD: HAS SUGGESTIONS</div>
                        </div>
                    </Popover>

                    {/* Control bar */}
                    <Grid container style={styles.centerAll}>

                        {/* Process button */}
                        <Grid item xs={10} style={styles.inlineContent}>
                            <Button variant="contained" color="secondary"
                                    onClick={(e) => this.handleProcess(selectedTask)}
                                    style={styles.smallMargin}>Process</Button>
                            <Button variant="outlined" color="secondary" onClick={(e) => this.handleExport()}
                                    style={styles.smallMargin}>Export</Button>

                            <Button variant="outlined" color="secondary" style={styles.smallMargin}
                                    onClick={(e) => this.handleSave()}>Save</Button>
                            <input accept='*' onChange={this.handleOpen} style={{display: 'none'}}
                                   id='raised-button-file' multiple type='file'/>
                            <label htmlFor="raised-button-file">
                                <Button variant="outlined" component="span" color="secondary"
                                        style={styles.smallMargin}>Open</Button>
                            </label>

                            <Button aria-describedby={id} variant="outlined" color="secondary"
                                    style={styles.smallMargin}
                                    onClick={this.handleLegendOpen}>Legend</Button>
                        </Grid>

                        {/* Zoom */}
                        <Grid item xs={2}>
                            <Grid container>
                                <Grid item>
                                    <ZoomIn/>
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

                    <Typography style={{...styles.smallMargin, ...styles.subtitle}}
                                id="discrete-slider"> Task: <i>{selectedTask && selectedTask.label}</i> </Typography>
                    <br/>

                    {/* Editing Area */}
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell>#</TableCell>
                                <TableCell>Before process</TableCell>
                                <TableCell>After process</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {this.state.dataLines.map((dataLine, indexLine) => (
                                <TableRow key={indexLine}>
                                    <TableCell>{indexLine}</TableCell>

                                    <TableCell style={{fontSize: this.state.textSize}}>
                                        {dataLine.map((word, index) => (
                                            <span key={index}> {word.value} </span>
                                        ))}
                                    </TableCell>

                                    <TableCell style={{fontSize: this.state.textSize}}>
                                        {dataLine.map((word, indexWord) => [
                                            <span key={indexWord}>
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
                                                                    <span
                                                                        onClick={this.handleEditLetter.bind(this, indexLine, indexWord, indexLetter)}
                                                                        style={styles.flagLetterDeleted}>
                                                                        {letter.value}
                                                                    </span>
                                                                    <span
                                                                        onClick={this.handleEditLetter.bind(this, indexLine, indexWord, indexLetter)}
                                                                        style={styles.flagLetterInserted}>
                                                                        {letter.newValue}
                                                                    </span>
                                                                </span>
                                                            ) : (
                                                                <span key={indexLetter}
                                                                      onClick={this.handleEditLetter.bind(this, indexLine, indexWord, indexLetter)}
                                                                      style={this.getTextStyle(letter)}>
                                                                    {letter.value}
                                                                </span>
                                                            )
                                                        ) : (
                                                            <span key={indexLetter}
                                                                  style={this.getTextStyle(letter)}>{letter.value}</span>
                                                        )
                                                    ))}
                                                    </span>
                                                )}

                                                {/* Level 2 word */}
                                                {word.level === 2 && (
                                                    word.selected === -1 ? (
                                                        <span
                                                            onClick={this.handleEditWord.bind(this, indexLine, indexWord)}
                                                            style={this.getTextStyle(word)}>{word.value}</span>
                                                    ) : (
                                                        <span
                                                            onClick={this.handleEditWord.bind(this, indexLine, indexWord)}
                                                            style={this.getTextStyle(word.suggestions[word.selected])}>{word.suggestions[word.selected].value}</span>
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

export default withStyles(styles, {withTheme: true})(CheckerPanel)
