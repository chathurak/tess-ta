import {withStyles} from '@material-ui/core'
import PropTypes    from 'prop-types'
import * as React   from 'react'
import {styles}     from './styles'

class Home extends React.Component {

    render() {
        return (
            <div>
            </div>
        )
    }

}

Home.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(Home)
