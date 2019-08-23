const styles = {
    workspace: {
        margin: 10,
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
        // background-color: yellow,
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
        // background-color: yellow,
        textDecoration: 'underline',
        fontWeight: 'bold',
        cursor: 'pointer',
    },
    flagWordIllegitimate: {
        // background-color: yellow,
        textDecoration: 'underline',
    },
    flagWordHasSuggestions: {
        // text-decoration: underline,
        fontWeight: 'bold',
        cursor: 'pointer',
    },

}

export default styles