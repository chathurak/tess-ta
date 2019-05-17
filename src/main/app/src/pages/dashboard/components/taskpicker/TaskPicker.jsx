import Grid               from '@material-ui/core/Grid'
import Paper              from '@material-ui/core/Paper'
import {withStyles}       from '@material-ui/core/styles'
import {DateTimePicker}   from 'material-ui-pickers'
import PropTypes          from 'prop-types'
import React              from 'react'
import AutoCompleteSingle from './components/autocomplete/AutoCompleteSingle'
import {styles}           from './styles'

const suggestions = [
    {label: 'Afghanistan'},
    {label: 'Aland Islands'},
    {label: 'Albania'},
    {label: 'Algeria'},
    {label: 'American Samoa'},
    {label: 'Andorra'},
    {label: 'Angola'},
    {label: 'Anguilla'},
    {label: 'Antarctica'},
    {label: 'Antigua and Barbuda'},
    {label: 'Argentina'},
    {label: 'Armenia'},
    {label: 'Aruba'},
    {label: 'Australia'},
    {label: 'Austria'},
    {label: 'Azerbaijan'},
    {label: 'Bahamas'},
    {label: 'Bahrain'},
    {label: 'Bangladesh'},
    {label: 'Barbados'},
    {label: 'Belarus'},
    {label: 'Belgium'},
    {label: 'Belize'},
    {label: 'Benin'},
    {label: 'Bermuda'},
    {label: 'Bhutan'},
    {label: 'Bolivia, Plurinational State of'},
    {label: 'Bonaire, Sint Eustatius and Saba'},
    {label: 'Bosnia and Herzegovina'},
    {label: 'Botswana'},
    {label: 'Bouvet Island'},
    {label: 'Brazil'},
    {label: 'British Indian Ocean Territory'},
    {label: 'Brunei Darussalam'},
].map(suggestion => ({
    value: suggestion.label,
    label: suggestion.label,
}))

class TaskPicker extends React.Component {

    state = {
        // The first commit of Material-UI
        selectedDate: new Date('2014-08-18T21:11:54'),
    }

    handleDateChange = date => {
        this.setState({selectedDate: date})
    }

    render() {
        const {classes}      = this.props
        const {selectedDate} = this.state

        const options = [
            {value: 'chocolate', label: 'Chocolate'},
            {value: 'strawberry', label: 'Strawberry'},
            {value: 'vanilla', label: 'Vanilla'}
        ]

        return (
            <div>
                <Paper className={classes.root} elevation={1}>
                    <Grid className={classes.section1} container direction="row" justify="flex-start"
                          alignItems="center">
                        <DateTimePicker className={classes.datetimepicker} value={selectedDate}
                                        onChange={this.handleDateChange} label="Start"/>
                        <DateTimePicker className={classes.datetimepicker} value={selectedDate}
                                        onChange={this.handleDateChange} label="End"/>
                    </Grid>
                    <Grid className={classes.section2} container direction="row" justify="flex-start"
                          alignItems="center">
                        <AutoCompleteSingle className={classes.select} options={suggestions}
                                            placeholder="Select file ..."/>
                        <AutoCompleteSingle className={classes.select} options={suggestions}
                                            placeholder="Select task ..."/>
                    </Grid>
                </Paper>
            </div>
        )
    }

}

TaskPicker.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(TaskPicker)
