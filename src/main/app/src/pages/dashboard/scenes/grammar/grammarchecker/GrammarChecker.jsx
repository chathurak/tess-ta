import {withStyles}  from '@material-ui/core'
import * as React    from 'react'
import {styles}      from './styles'

class GrammarChecker extends React.Component {
    render() {
        return (
            <div>
                Checker
            </div>
        )
    }
}

export default withStyles(styles, {withTheme: true})(GrammarChecker)
