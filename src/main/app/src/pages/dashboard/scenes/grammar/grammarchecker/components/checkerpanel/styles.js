const styles = {
    workspace: {
        margin: 10,
    },
    centerAll: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    flagLetterCharacterLegitimacyError: {
        color: 'red',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagLetterGrammarLegitimacyError: {
        color: 'orange',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagLetterChanged: {
        // backgroundColor: 'yellow',
        color: 'blue',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagLetterOptional: {
        color: 'green',
        fontWeight: 'bold',
    },
    flagLetterDeleted: {
        color: 'gray',
        fontWeight: 'bold',
        textDecoration: 'line-through',
        cursor: 'pointer',
    },
    flagLetterInserted: {
        color: 'blue',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagWordNotInDictionary: {
        backgroundColor: 'yellow',
        textDecoration: 'underline',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagWordIllegitimate: {
        backgroundColor: 'yellow',
        textDecoration: 'underline',
    },
    flagWordHasSuggestions: {
        fontWeight: 'bold',
        cursor: 'pointer',
    },

}

export default styles