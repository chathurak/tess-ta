import {withStyles}             from '@material-ui/core/styles'
import {KeyboardDateTimePicker} from '@material-ui/pickers'
import PropTypes                from 'prop-types'
import React                    from 'react'
import {connect}                from 'react-redux'
import {documentServices}       from '../../../../services'
import AutoCompleteAsync        from './components/autocomplete/AutoCompleteAsync'
import {actions}                from './duck'
import {styles}                 from './styles'

class DocumentPicker extends React.Component {

    state = {
        // The first commit of Material-UI
        selectedDate: new Date('2014-08-18T21:11:54'),
    }

    handleDateChange = date => {
        this.setState({selectedDate: date})
    }

    handleDocumentChange = item => {
        const {dispatch} = this.props
        dispatch(actions.selectDocument(item))
    }

    promiseDocuments = () => {
        let options = documentServices.getDocuments()
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

    render() {
        const {classes}      = this.props
        const {selectedDate} = this.state

        return (
            <div>
                <div className={classes.documentPickerRoot}>
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
                        loadOptions={this.promiseDocuments}
                        onChange={this.handleDocumentChange}
                    />
                </div>
            </div>
        )
    }

}

DocumentPicker.propTypes = {
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

const styledComponent = withStyles(styles, {withTheme: true})(DocumentPicker)
export default connect(mapStateToProps)(styledComponent)