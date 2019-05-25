import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {styles}      from './styles'

class Dictionary extends React.Component {
    render() {
        return (
            <div>
                Dictionary
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(Dictionary)
