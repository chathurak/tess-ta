import React, {Component} from 'react'
import {Link}             from 'react-router-dom'
import {styles}           from './styles'
import PropTypes          from 'prop-types'
import {withStyles}       from '@material-ui/core/styles'

class NotFound extends Component {
    render() {
        const {classes} = this.props

        return (
            <div className="page-not-found">
                <h1 className="title">
                    404
                </h1>
                <div className="desc">
                    The Page you're looking for was not found.
                </div>
                <Link to="/">
                    <button className="go-back-btn btn btn-primary" type="button">Go Back</button>
                </Link>
            </div>
        )
    }
}

NotFound.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(NotFound)