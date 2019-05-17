import NoSsr            from '@material-ui/core/NoSsr'
import {withStyles}     from '@material-ui/core/styles'
import PropTypes        from 'prop-types'
import React            from 'react'
import Select           from 'react-select'
import Control          from './components/Control'
import Menu             from './components/Menu'
import MultiValue       from './components/MultiValue'
import NoOptionsMessage from './components/NoOptionsMessage'
import Option           from './components/Option'
import Placeholder      from './components/Placeholder'
import SingleValue      from './components/SingleValue'
import ValueContainer   from './components/ValueContainer'
import {styles}         from './styles'

const components = {
    Control,
    Menu,
    MultiValue,
    NoOptionsMessage,
    Option,
    Placeholder,
    SingleValue,
    ValueContainer,
}

class AutoCompleteSingle extends React.Component {

    state = {
        single: null,
        multi : null,
    }

    handleChange = name => value => {
        this.setState({
            [name]: value,
        })
    }

    render() {
        const {classes, theme} = this.props

        const selectStyles = {
            input: base => ({
                ...base,
                color    : theme.palette.text.primary,
                '& input': {
                    font: 'inherit',
                },
            }),
        }

        return (
            <div className={this.props.className}>
                <NoSsr>
                    <Select
                        classes={classes}
                        styles={selectStyles}
                        options={this.props.options}
                        components={components}
                        value={this.state.single}
                        onChange={this.handleChange('single')}
                        placeholder={this.props.placeholder}
                        isClearable
                    />
                </NoSsr>
            </div>
        )
    }
}

AutoCompleteSingle.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(AutoCompleteSingle)