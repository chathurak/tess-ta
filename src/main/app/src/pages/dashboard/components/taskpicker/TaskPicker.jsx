import {withStyles}      from '@material-ui/core/styles/index'
import PropTypes         from 'prop-types'
import React             from 'react'
import {connect}         from 'react-redux'
import {taskServices}    from '../../../../services'
import AutoCompleteAsync from './components/autocomplete/AutoCompleteAsync'
import DocumentPicker    from './DocumentPicker'
import {styles}          from './styles'

class TaskPicker extends React.Component {

    handleTaskChange = (item) => {
        console.log(item)
    }

    promiseUserTasks = () => {
        const {selectedDocument} = this.props

        let options = selectedDocument ? taskServices.getTasks(selectedDocument.value)
            .then(tasks => {
                return tasks.map(task => ({
                    value: task.id,
                    label: task.name
                }))
            })
            .catch(err => {
                console.log(err)
            }) : []

        return new Promise(resolve => {
            setTimeout(() => {
                resolve(options)
            }, 1000)
        })
    }

    render() {
        const {classes, selectedDocument} = this.props

        return (
            <div>
                <DocumentPicker/>
                <div className={classes.taskPickerRoot}>
                    <AutoCompleteAsync
                        key={selectedDocument ? selectedDocument.value : 'x'}
                        className={classes.select}
                        placeholder="Select task ..."
                        label="Task"
                        loadOptions={this.promiseUserTasks}
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