import {withStyles}   from '@material-ui/core/styles'
import * as React     from 'react'
import DocumentPicker from '../../../taskpicker/DocumentPicker'
import {styles}       from './styles'

class OptionPanel extends React.Component {

    render() {
        return (
            <div className={this.props.className}>
                <DocumentPicker handleDocumentChange={this.props.handleDocumentChange}/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(OptionPanel)
