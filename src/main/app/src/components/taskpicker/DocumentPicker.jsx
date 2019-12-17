import {withStyles}             from '@material-ui/core/styles'
import {KeyboardDateTimePicker} from '@material-ui/pickers'
import PropTypes                from 'prop-types'
import React                    from 'react'
import {documentServices}       from '../../services/document.services'
import AutoCompleteAsync        from './components/autocomplete/AutoCompleteAsync'
import {styles}                 from './styles'

class DocumentPicker extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            // The first commit of Material-UI
            selectedDate    : new Date('2014-08-18T21:11:54')
        }
    }

    handleDateChange = date => {
        this.setState({selectedDate: date})
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
        const {classes}              = this.props
        const {handleDocumentChange} = this.props
        const {selectedDate}         = this.state

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
                        onChange={handleDocumentChange}
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

export default withStyles(styles, {withTheme: true})(DocumentPicker)