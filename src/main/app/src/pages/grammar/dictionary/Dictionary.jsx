import {withStyles}    from '@material-ui/core/styles'
import * as React      from 'react'
import {styles}        from './styles'
import ListItem        from '@material-ui/core/ListItem'
import ListItemText    from '@material-ui/core/ListItemText'
import {FixedSizeList} from 'react-window'
import Paper           from '@material-ui/core/Paper'
import IconButton      from '@material-ui/core/IconButton'
import {dictionaryServices} from '../../../services/dictionary.services'
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Grid, { GridSpacing } from '@material-ui/core/Grid';
import RefreshIcon from '@material-ui/icons/Refresh';


class Dictionary extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            mainDictionary: {
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
        let selectedWord = this.state.mainDictionary.words[index]
        this.setState({inputText: selectedWord})
        this.setState({selectedWord: selectedWord})
    }

    mainDictionaryWords = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleDictionaryWordClick(e, index)}>
                <ListItemText primary={this.state.mainDictionary.words[index]} />
            </ListItem>
        )
    }

    handleAdd = (event) => {
        dictionaryServices.addWord(this.state.inputText)
            .then((res) => {
                this.handleLoad();
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleUpdate = (event) => {
        dictionaryServices.updateWord(this.state.selectedWord, this.state.inputText)
            .then((res) => {
                this.handleLoad();
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleDelete = (event) => {
        dictionaryServices.deleteWord(this.state.inputText)
            .then((res) => {
                this.handleLoad();
            })
            .catch((error) => {
                console.log(error)
            })
    }

    handleLoad = (event) => {
        dictionaryServices.getWords()
            .then((words) => {
                this.setState({
                    mainDictionary: {
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

                    {/* Primary Dictionary Panel */}
                    <Paper className={classes.paper}>
                        <Grid container>
                            <Grid item xs={10}>
                                <h2 className={classes.dictionaryTitle}>Primary Dictionary</h2>
                            </Grid>
                            <Grid item xs={2}>
                                <IconButton className={classes.margin} onClick={this.handleLoad}>
                                    <RefreshIcon fontSize="large"/>
                                </IconButton>
                            </Grid>
                        </Grid>

                        <FixedSizeList className={classes.wordListView} height={400} itemSize={20} itemCount={this.state.mainDictionary.size}>
                            {this.mainDictionaryWords}
                        </FixedSizeList>
                    </Paper>
                </div>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Dictionary)
