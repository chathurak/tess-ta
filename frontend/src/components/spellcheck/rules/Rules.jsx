import {withStyles}    from '@material-ui/core/styles'
import * as React      from 'react'
import {styles}        from './styles'
import ListItem        from '@material-ui/core/ListItem'
import ListItemText    from '@material-ui/core/ListItemText'
import {FixedSizeList} from 'react-window'
import Paper           from '@material-ui/core/Paper'
import IconButton      from '@material-ui/core/IconButton'
import {rulesServices} from '../../../services/rules.services'
import TextField       from '@material-ui/core/TextField'
import Button          from '@material-ui/core/Button'
import ButtonGroup     from '@material-ui/core/ButtonGroup'
import Grid            from '@material-ui/core/Grid'
import RefreshIcon     from '@material-ui/icons/Refresh'
import NotificationBox from '../../notificationbox/NotificationBox'


class Rules extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            mandatoryRules  : {
                rules: [],
                size : 0
            },
            optionalRules   : {
                rules: [],
                size : 0
            },
            inputRule       : {
                key  : '',
                value: ''
            },
            inputRuleType   : '',
            selectedRule    : {
                key  : '',
                value: ''
            },
            selectedRuleType: ''
        }
    }

    handleInputTextChange = (e, text) => {
        this.setState({inputText: text})
    }

    handleMandatoryRuleClick = (e, index) => {
        let selectedRule = this.state.mandatoryRules.rules[index]
        this.setState({inputText: selectedRule, selectedRuleType: 'MANDATORY'})
        this.setState({selectedRule: selectedRule})
    }

    handleOptionalRuleClick = (e, index) => {
        let selectedRule = this.state.optionalRules.rules[index]
        this.setState({inputRule: selectedRule, selectedRuleType: 'OPTIONAL'})
        this.setState({selectedRule: selectedRule})
    }

    mandatoryRules = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleMandatoryRuleClick(e, index)}>
                <ListItemText primary={this.ruleToText(this.state.mandatoryRules.rules[index])}/>
            </ListItem>
        )
    }

    optionalRules = (props) => {
        const {index, style} = props
        return (
            <ListItem button style={style} key={index} onClick={(e) => this.handleOptionalRuleClick(e, index)}>
                <ListItemText primary={this.ruleToText(this.state.optionalRules.rules[index])}/>
            </ListItem>
        )
    }

    ruleToText = (rule) => {
        return rule.key + ' > ' + rule.value
    }

    textToRule = (text) => {
        let item = text.split('>')
        return {key: item[0].trim(), value: item[1].trim()}
    }

    handleAdd = () => {
        if (this.state.inputText === '') {
            this.notificationBox.showError('Please enter a rule to insert')
            return
        }


        rulesServices.addRule(this.state.inputRule, this.state.inputRuleType)
            .then((res) => {
                this.notificationBox.showSuccess(`Successfully Added '${this.ruleToText(this.state.inputRule)}'`)
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Inserting rule')
                console.log(error)
            })
    }

    handleUpdate = () => {
        if (this.state.inputText === '') {
            this.notificationBox.showError('Please enter a rule to update')
            return
        }

        rulesServices.updateRule(this.state.selectedRule, this.state.inputRule, this.state.inputRuleType)
            .then((res) => {
                this.notificationBox.showSuccess('Successfully Updated')
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Updating rule')
                console.log(error)
            })
    }

    handleDelete = () => {
        if (this.state.selectedRule === '') {
            this.notificationBox.showError('Please select a rule to delete')
            return
        }

        rulesServices.deleteRule(this.state.inputRule, this.state.inputRuleType)
            .then((res) => {
                this.notificationBox.showSuccess('Successfully Deleted')
                this.handleLoad()
            })
            .catch((error) => {
                this.notificationBox.showError('Error in Deleting rule')
                console.log(error)
            })
    }

    handleLoad = () => {
        rulesServices.getRules('MANDATORY')
            .then((rules) => {
                this.setState({
                    mandatoryRules: {
                        rules: rules,
                        size : rules.length
                    }
                })
            })
            .catch((error) => {
                console.log(error)
            })
        rulesServices.getRules('OPTIONAL')
            .then((rules) => {
                this.setState({
                    optionalRules: {
                        rules: rules,
                        size : rules.length
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
                                    <Grid item xs={4}>
                                        <form>
                                            <TextField
                                                className={classes.inputTextArea}
                                                label={this.state.selectedRule.key}
                                                variant="outlined"
                                                value={this.state.inputRule.key}
                                                onChange={e => this.setState({
                                                    inputRule: {
                                                        key  : e.target.value,
                                                        value: this.state.inputRule.value
                                                    }
                                                })}/>
                                            <TextField
                                                className={classes.inputTextArea}
                                                label={this.state.selectedRule.value}
                                                variant="outlined"
                                                value={this.state.inputRule.value}
                                                onChange={e => this.setState({
                                                    inputRule: {
                                                        key  : this.state.inputRule.key,
                                                        value: e.target.value
                                                    }
                                                })}/>
                                        </form>
                                    </Grid>
                                    <Grid item xs={8}>
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

                    <Grid container className={classes.rulesPanel}>
                        {/* Mandatory Rules Panel */}
                        <Grid item xs={6}>
                            <Paper className={classes.paper}>
                                <Grid container>
                                    <Grid item xs={10}>
                                        <h2 className={classes.rulesTitle}>Mandatory Rules</h2>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <IconButton className={classes.margin} onClick={this.handleLoad}>
                                            <RefreshIcon fontSize="large"/>
                                        </IconButton>
                                    </Grid>
                                </Grid>

                                <FixedSizeList className={classes.ruleListView} height={400} itemSize={20}
                                               itemCount={this.state.mandatoryRules.size}>
                                    {this.mandatoryRules}
                                </FixedSizeList>
                            </Paper>
                        </Grid>


                        {/* Optional Rules Panel */}
                        <Grid item xs={6}>
                            <Paper className={classes.paper}>
                                <Grid container>
                                    <Grid item xs={10}>
                                        <h2 className={classes.rulesTitle}>Optional Rules</h2>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <IconButton className={classes.margin} onClick={this.handleLoad}>
                                            <RefreshIcon fontSize="large"/>
                                        </IconButton>
                                    </Grid>
                                </Grid>

                                <FixedSizeList className={classes.ruleListView} height={400} itemSize={20}
                                               itemCount={this.state.optionalRules.size}>
                                    {this.optionalRules}
                                </FixedSizeList>
                            </Paper>
                        </Grid>

                    </Grid>


                    <NotificationBox ref={notification => this.notificationBox = notification}/>
                </div>
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Rules)
