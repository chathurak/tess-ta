import NoSsr            from '@material-ui/core/NoSsr'
import {withStyles}     from '@material-ui/core/styles'
import PropTypes        from 'prop-types'
import React            from 'react'
import AsyncSelect      from 'react-select/async'
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

class AutoCompleteAsync extends React.Component {

    state = {
        single: null,
        multi : null,
    }

    handleChange = (name, outerFun) => {
        return value => {
            this.setState({
                [name]: value,
            })

            outerFun(value)
        }
    }

    render() {
        const {classes, theme}                             = this.props
        const {isMulti, options, placeholder, loadOptions} = this.props

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
                    <AsyncSelect
                        classes={classes}
                        styles={selectStyles}
                        textFieldProps={{
                            label          : this.props.label,
                            InputLabelProps: {
                                shrink: true,
                            },
                        }}
                        options={options}
                        components={components}
                        value={isMulti ? this.state.multi : this.state.single}
                        onChange={this.handleChange((isMulti ? 'multi' : 'single'), this.props.onChange)}
                        placeholder={placeholder}
                        isClearable={!isMulti}
                        isMulti={isMulti}

                        // Async stuff
                        cacheOptions
                        defaultOptions
                        loadOptions={loadOptions}
                    />
                </NoSsr>
            </div>
        )
    }

}

AutoCompleteAsync.propTypes = {
    classes: PropTypes.object.isRequired,
    theme  : PropTypes.object.isRequired,
}

export default withStyles(styles, {withTheme: true})(AutoCompleteAsync)