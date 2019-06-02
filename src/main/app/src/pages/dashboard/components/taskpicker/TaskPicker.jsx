import {withStyles}                 from '@material-ui/core/styles'
import {KeyboardDateTimePicker}       from '@material-ui/pickers'
import PropTypes                    from 'prop-types'
import React                        from 'react'
import {connect}                    from 'react-redux'
import {fileServices, taskServices} from '../../../../services'
import AutoCompleteAsync            from './components/autocomplete/AutoCompleteAsync'
import {styles}                     from './styles'

class TaskPicker extends React.Component {

    state = {
        // The first commit of Material-UI
        selectedDate: new Date('2014-08-18T21:11:54'),
    }

    handleDateChange = date => {
        this.setState({selectedDate: date})
    }

    handleDocumentChange = x => {
        console.log(x)
    }

    handleTaskChange = (x, y) => {
        console.log(x)
        console.log(y)
    }

    render() {
        const {classes, selectedDocument} = this.props
        const {selectedDate}              = this.state

        const promiseDocuments = () => {
            let options = fileServices.getDocuments()
                .then(documents => {
                    return documents.map(document => ({
                        value: document.id,
                        label: document.name
                    }))
                })
                .catch(err => {
                    console.log(err)
                })

            return new Promise(resolve => {
                setTimeout(() => {
                    resolve(options)
                }, 1000)
            })
        }

        const promiseUserTasks = () => {
            let options = taskServices.getTasks()
                .then(tasks => {
                    return tasks.map(task => ({
                        value: task.id,
                        label: task.name
                    }))
                })
                .catch(err => {
                    console.log(err)
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
                    <KeyboardDateTimePicker
                        className={classes.datetimepicker}
                        value={selectedDate}
                        onChange={this.handleDateChange}
                        onError={console.log}
                        variant="inline"
                        ampm={false}
                        format="yyyy/MM/dd HH:mm"
                        label="Start"
                    />
                    <KeyboardDateTimePicker
                        className={classes.datetimepicker}
                        value={selectedDate}
                        onChange={this.handleDateChange}
                        onError={console.log}
                        variant="inline"
                        ampm={false}
                        format="yyyy/MM/dd HH:mm"
                        disableFuture
                        label="End"
                    />
                    <AutoCompleteAsync
                        className={classes.select}
                        placeholder="Select document ..."
                        label="Document"
                        loadOptions={promiseDocuments}
                        onChange={this.handleDocumentChange}
                        isMulti
                    />
                    <AutoCompleteAsync
                        className={classes.select}
                        placeholder="Select task ..."
                        label="Task"
                        // loadOptions={promiseUserTasks}
                        loadOptions={promiseDocuments}
                        onChange={this.handleTaskChange}
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

const mapStateToProps = (state) => {
    const {selectedDocument, selectedTask} = state.taskPickerReducer
    return {
        selectedDocument,
        selectedTask
    }
}

const styledComponent = withStyles(styles, {withTheme: true})(TaskPicker)
export default connect(mapStateToProps)(styledComponent)