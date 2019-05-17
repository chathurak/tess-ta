import {withStyles} from '@material-ui/core'
import * as React   from 'react'
import styles       from './styles'

class Ocr extends React.Component {

    render() {
        const {classes} = this.props

        return (
            <div className={classes.root}>
            </div>
        )
    }

}

export default withStyles(styles, {withTheme: true})(Ocr)
