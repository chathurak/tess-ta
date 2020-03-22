import {withStyles}         from '@material-ui/core/styles'
import * as React           from 'react'
import {styles}             from './styles'
import ListItem             from '@material-ui/core/ListItem'
import ListItemText         from '@material-ui/core/ListItemText'
import {FixedSizeList}      from 'react-window'
import Paper                from '@material-ui/core/Paper'
import IconButton           from '@material-ui/core/IconButton'
import {dictionaryServices} from '../../../services/dictionary.services'
import TextField            from '@material-ui/core/TextField';
import Button               from '@material-ui/core/Button';
import ButtonGroup          from '@material-ui/core/ButtonGroup';
import Grid                 from '@material-ui/core/Grid';
import RefreshIcon          from '@material-ui/icons/Refresh';
import NotificationBox      from '../../../components/notificationbox/NotificationBox'


class Dictionary extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            dictionaryPrimary: {
                words: [],
                size : 0
            },
            dictionarySecondary: {
                words: [],
                size : 0
            },
            inputText: '',
            selectedWord: ''
        }
    }

    handleInputTextChange = (e, text) => {
        this.setState({inputText: text})
    }

    handleDictionaryWordClick = (e, index) => {
        let selectedWord = this.state.dictionaryPrimary.words[index]
        this.setState({inputText: selectedWord})
        this.setState({selectedWord: selectedWord})
    }

    dictionaryPrimaryWords = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleDictionaryWordClick(e, index)}>
                <ListItemText primary={this.state.dictionaryPrimary.words[index]} />
            </ListItem>
        )
    }

    dictionarySecondaryWords = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleDictionaryWordClick(e, index)}>
                <ListItemText primary={this.state.dictionarySecondary.words[index]} />
            </ListItem>
        )
    }

    handleAdd = () => {
        if (this.state.inputText === ''){
            this.notificationBox.showError("Please enter a word to insert")
            return
        }


        dictionaryServices.addWord(this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess(`Successfully Added '${this.state.inputText}'`)
                this.handleLoad();
            })
            .catch((error) => {
                this.notificationBox.showError("Error in Inserting word")
                console.log(error)
            })
    }

    handleUpdate = () => {
        if (this.state.inputText === ''){
            this.notificationBox.showError("Please enter a word to update")
            return
        }

        dictionaryServices.updateWord(this.state.selectedWord, this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess("Successfully Updated")
                this.handleLoad();
            })
            .catch((error) => {
                this.notificationBox.showError("Error in Updating word")
                console.log(error)
            })
    }

    handleDelete = () => {
        if (this.state.selectedWord === ''){
            this.notificationBox.showError("Please select a word to delete")
            return
        }

        dictionaryServices.deleteWord(this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess("Successfully Deleted")
                this.handleLoad();
            })
            .catch((error) => {
                this.notificationBox.showError("Error in Deleting word")
                console.log(error)
            })
    }

    handleLoad = () => {
        dictionaryServices.getWords()
            .then((words) => {
                this.setState({
                    dictionaryPrimary: {
                        words: words,
                        size: words.length
                    }
                })
            })
            .catch((error) => {
                console.log(error)
            })
    }

    render() {
        const {classes} = this.props

        return (
            <div>
                <div className={classes.root}>
                    <Grid container>
                        <Grid item xs={3}></Grid>
                        <Grid item xs={6}>
                            <Paper style={{padding: 10}}>
                                <Grid container className={classes.root}>
                                    <Grid item xs={6}>
                                    <form>
                                        <TextField className={classes.inputTextArea} label={this.state.selectedWord} variant="outlined" value={this.state.inputText} onChange={e => this.setState({ inputText: e.target.value })}/>
                                    </form>
                                    </Grid>
                                    <Grid item xs={6}>
                                        <ButtonGroup className={classes.buttonGroup} variant='outlined'>
                                            <Button color="secondary" onClick={this.handleAdd}>Add</Button>
                                            <Button color="secondary" onClick={this.handleUpdate}>Update</Button>
                                            <Button color="secondary" onClick={this.handleDelete}>Delete</Button>
                                        </ButtonGroup>
                                    </Grid>
                                </Grid>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}></Grid>
                    </Grid>

                    <Grid container className={classes.dictionaryPanel}>
                        {/* Primary Dictionary Panel */}
                        <Grid item xs={6}>
                            <Paper className={classes.paper}>
                                <Grid container>
                                    <Grid item xs={10}>
                                        <h2 className={classes.dictionaryTitle}>Primary Word Inventory</h2>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <IconButton className={classes.margin} onClick={this.handleLoad}>
                                            <RefreshIcon fontSize="large"/>
                                        </IconButton>
                                    </Grid>
                                </Grid>

                                <FixedSizeList className={classes.wordListView} height={400} itemSize={20} itemCount={this.state.dictionaryPrimary.size}>
                                    {this.dictionaryPrimaryWords}
                                </FixedSizeList>
                            </Paper>
                        </Grid>


                        {/* Secondary Dictionary Panel */}
                        <Grid item xs={6}>
                            <Paper className={classes.paper}>
                                <Grid container>
                                    <Grid item xs={10}>
                                        <h2 className={classes.dictionaryTitle}>Secondary Word Inventory</h2>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <IconButton className={classes.margin} onClick={this.handleLoad}>
                                            <RefreshIcon fontSize="large"/>
                                        </IconButton>
                                    </Grid>
                                </Grid>

                                <FixedSizeList className={classes.wordListView} height={400} itemSize={20} itemCount={this.state.dictionarySecondary.size}>
                                    {this.dictionarySecondaryWords}
                                </FixedSizeList>
                            </Paper>
                        </Grid>

                    </Grid>




                    <NotificationBox ref={notification => this.notificationBox = notification} />
                </div>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Dictionary)
