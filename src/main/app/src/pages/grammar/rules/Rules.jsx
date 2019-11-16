import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import {styles}     from './styles'

class Rules extends React.Component {
    render() {
        return (
            <div>
                Rules
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Rules)
