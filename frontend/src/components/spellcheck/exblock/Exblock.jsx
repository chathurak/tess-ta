import {withStyles}      from '@material-ui/core/styles'
import * as React        from 'react'
import {styles}          from './styles'
import ListItem          from '@material-ui/core/ListItem'
import ListItemText      from '@material-ui/core/ListItemText'
import {FixedSizeList}   from 'react-window'
import Paper             from '@material-ui/core/Paper'
import IconButton        from '@material-ui/core/IconButton'
import {exblockServices} from '../../../services/exblock.services'
import TextField         from '@material-ui/core/TextField'
import Button            from '@material-ui/core/Button'
import ButtonGroup       from '@material-ui/core/ButtonGroup'
import Grid              from '@material-ui/core/Grid'
import RefreshIcon       from '@material-ui/icons/Refresh'
import NotificationBox   from '../../notificationbox/NotificationBox'


class Exblock extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            exblockLetters: {
                letters: [],
                size   : 0
            },
            inputText     : '',
            selectedLetter: ''
        }
    }

    handleInputTextChange = (e, text) => {
        this.setState({inputText: text})
    }

    handleExblockLetterClick = (e, index) => {
        let selectedLetter = this.state.exblockLetters.letters[index]
        this.setState({inputText: selectedLetter})
        this.setState({selectedLetter: selectedLetter})
    }

    exblockLetters = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleExblockLetterClick(e, index)}>
                <ListItemText primary={this.state.exblockLetters.letters[index]}/>
            </ListItem>
        )
    }

    handleAdd = () => {
        if (this.state.inputText === '') {
            this.notificationBox.showError('Please enter a letter to insert')
            return
        }


        exblockServices.addLetter(this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess(`Successfully Added '${this.state.inputText}'`)
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Inserting letter')
                console.log(error)
            })
    }

    handleUpdate = () => {
        if (this.state.inputText === '') {
            this.notificationBox.showError('Please enter a letter to update')
            return
        }

        exblockServices.updateLetter(this.state.selectedLetter, this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess('Successfully Updated')
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Updating letter')
                console.log(error)
            })
    }

    handleDelete = () => {
        if (this.state.selectedLetter === '') {
            this.notificationBox.showError('Please select a letter to delete')
            return
        }

        exblockServices.deleteLetter(this.state.inputText)
            .then((res) => {
                this.notificationBox.showSuccess('Successfully Deleted')
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Deleting letter')
                console.log(error)
            })
    }

    handleLoad = () => {
        exblockServices.getLetters()
            .then((letters) => {
                this.setState({
                    exblockLetters: {
                        letters: letters,
                        size   : letters.length
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
                                            <TextField className={classes.inputTextArea}
                                                       label={this.state.selectedLetter} variant="outlined"
                                                       value={this.state.inputText}
                                                       onChange={e => this.setState({inputText: e.target.value})}/>
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

                    {/* Exblock Panel */}
                    <Grid container className={classes.exblockPanel}>
                        <Paper className={classes.paper}>
                            <Grid container>
                                <Grid item xs={10}>
                                    <h2 className={classes.exblockTitle}>Extended Block Letters</h2>
                                </Grid>
                                <Grid item xs={2}>
                                    <IconButton className={classes.margin} onClick={this.handleLoad}>
                                        <RefreshIcon fontSize="large"/>
                                    </IconButton>
                                </Grid>
                            </Grid>

                            <FixedSizeList className={classes.letterListView} height={400} itemSize={20}
                                           itemCount={this.state.exblockLetters.size}>
                                {this.exblockLetters}
                            </FixedSizeList>
                        </Paper>
                    </Grid>

                    <NotificationBox ref={notification => this.notificationBox = notification}/>
                </div>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Exblock)
