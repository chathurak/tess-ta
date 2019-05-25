import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import {styles}     from './styles'

class TaskPanel extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={this.props.className}>

            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(TaskPanel)