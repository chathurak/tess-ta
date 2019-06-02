import {withStyles}     from '@material-ui/core'
import Button           from '@material-ui/core/Button'
import Checkbox         from '@material-ui/core/Checkbox'
import FormControl      from '@material-ui/core/FormControl'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import FormGroup        from '@material-ui/core/FormGroup'
import FormLabel        from '@material-ui/core/FormLabel'
import Grid             from '@material-ui/core/Grid'
import Icon             from '@material-ui/core/Icon'
import * as React       from 'react'
import {connect}        from 'react-redux'
import {actions}        from './duck'
import {styles}         from './styles'

class TaskPanel extends React.Component {

    constructor(props, context) {
        super(props, context)

        this.state = {
            diffReport     : true,
            confusionReport: true,
        }
    }

    handleChange = name => {
        return event => {
            return this.setState({...this.state, [name]: event.target.checked})
        }
    }

    scheduleTask = () => {
        const {dispatch, selectedDocument} = this.props

        dispatch(actions.scheduleTask(selectedDocument.value))
    }

    render() {
        const {classes, selectedDocument}   = this.props
        const {diffReport, confusionReport} = this.state

        return (
            <div className={this.props.className + ' ' + classes.root}>
                <Grid container spacing={8}>
                    <Grid item xs={12}>
                        <p className={classes.paper}>{selectedDocument ? selectedDocument.label : ''}</p>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl component="fieldset" className={classes.formControl}>
                            <FormLabel component="legend">Select reports</FormLabel>
                            <FormGroup>
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            checked={diffReport}
                                            onChange={this.handleChange('diffReport')}
                                            value="diffRep"
                                        />
                                    }
                                    label="Diff Report"
                                />
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            checked={confusionReport}
                                            onChange={this.handleChange('confusionReport')}
                                            value="confRep"
                                        />
                                    }
                                    label="Confusion Report"
                                />
                            </FormGroup>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <Button onClick={this.scheduleTask} variant="contained" color="primary"
                                className={classes.runButton}>
                            RUN
                            <Icon className={classes.buttonIcon}>send</Icon>
                        </Button>
                    </Grid>
                </Grid>
            </div>
        )
    }

}

const mapStateToProps = (state) => {
    const {selectedDocument} = state.taskPickerReducer
    return {
        selectedDocument,
    }
}

const styledComponent = withStyles(styles, {withTheme: true})(TaskPanel)
export default connect(mapStateToProps)(styledComponent)