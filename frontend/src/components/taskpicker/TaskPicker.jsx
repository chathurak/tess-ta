import {withStyles}      from '@material-ui/core/styles/index'
import PropTypes         from 'prop-types'
import React             from 'react'
import {taskServices}    from '../../services/task.services'
import AutoCompleteAsync from './components/autocomplete/AutoCompleteAsync'
import DocumentPicker    from './DocumentPicker'
import {styles}          from './styles'


class TaskPicker extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            selectedTask    : null,
            selectedDocument: null
        }
    }

    handleTaskChange = (item) => {
        this.setState({selectedTask: item})
    }

    handleDocumentChange = (document) => {
        this.setState({selectedDocument: document})
        this.promiseUserTasks();
    }

    promiseUserTasks = () => {
        console.log(this.state.selectedDocument)
        let options = this.state.selectedDocument ? taskServices.getTasks(this.state.selectedDocument.value)
            .then(tasks => {
                console.log(tasks)
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
                console.log(options)
            }, 1000)
        })
    }

    render() {
        const {classes}          = this.props
        const {selectedDocument} = this.props
        const {handleTaskChange} = this.props

        return (
            <div>
                <DocumentPicker handleDocumentChange={this.handleDocumentChange}/>
                <div className={classes.taskPickerRoot}>
                    <AutoCompleteAsync
                        key={selectedDocument ? selectedDocument.value : 'x'}
                        className={classes.select}
                        placeholder="Select task ..."
                        label="Task"
                        loadOptions={this.promiseUserTasks}
                        onChange={handleTaskChange}
                        defaultOptions={true}
                    />
                </div>
            </div>
        )
    }

}

TaskPicker.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired
}

export default withStyles(styles, {withTheme: true})(TaskPicker)
