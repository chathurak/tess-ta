import {withStyles}           from '@material-ui/core/styles'
import {InlineDateTimePicker} from 'material-ui-pickers'
import PropTypes              from 'prop-types'
import React                  from 'react'
import {fileServices}         from '../../../../services'
import AutoCompleteAsync      from './components/autocomplete/AutoCompleteAsync'
import {styles}               from './styles'

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
        const mask           = [
            /\d/,
            /\d/,
            /\d/,
            /\d/,
            '/',
            /\d/,
            /\d/,
            '/',
            /\d/,
            /\d/,
            ' ',
            /\d/,
            /\d/,
            ':',
            /\d/,
            /\d/,
        ]

        const promiseUserFiles = () => {
            let options = fileServices.getUserFiles()
                .then(userFiles => {
                    return userFiles.map(userFile => ({
                        value: userFile.id,
                        label: userFile.name
                    }))
                })

            return new Promise(resolve => {
                setTimeout(() => {
                    resolve(options)
                }, 1000)
            })
        }

        const promiseUserTasks = () => {
            let options = fileServices.getUserFiles()
                .then(userFiles => {
                    return userFiles.map(userFile => ({
                        value: userFile.id,
                        label: userFile.name
                    }))
                })

            return new Promise(resolve => {
                setTimeout(() => {
                    resolve(options)
                }, 1000)
            })
        }

        return (
            <div>
                <div className={classes.root}>
                    <InlineDateTimePicker
                        className={classes.datetimepicker}
                        value={selectedDate}
                        onChange={this.handleDateChange}
                        onError={console.log}
                        keyboard
                        ampm={false}
                        format="yyyy/MM/dd HH:mm"
                        mask={mask}
                        label="Start"
                    />
                    <InlineDateTimePicker
                        className={classes.datetimepicker}
                        value={selectedDate}
                        onChange={this.handleDateChange}
                        onError={console.log}
                        keyboard
                        ampm={false}
                        format="yyyy/MM/dd HH:mm"
                        mask={mask}
                        disableFuture
                        label="End"
                    />
                    <AutoCompleteAsync
                        className={classes.select}
                        placeholder="Select file ..."
                        label="User-file"
                        loadOptions={promiseUserFiles}
                    />
                    <AutoCompleteAsync
                        className={classes.select}
                        placeholder="Select task ..."
                        label="File-task"
                        loadOptions={promiseUserTasks}
                    />
                </div>
            </div>
        )
    }

}

TaskPicker.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(TaskPicker)
