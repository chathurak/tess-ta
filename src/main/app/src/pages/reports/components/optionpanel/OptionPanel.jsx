import {withStyles}   from '@material-ui/core/styles'
import * as React     from 'react'
import DocumentPicker from '../../../../components/taskpicker/DocumentPicker'
import {styles}       from './styles'

class OptionPanel extends React.Component {

    render() {
        return (
            <div className={this.props.className}>
                <DocumentPicker/>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(OptionPanel)