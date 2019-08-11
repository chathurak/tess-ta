import { Word } from '../models/word';
import { Letter } from '../models/letter';

// Convert document text to model
const docToModel = (docJson) => {
    var docModel = [];

    // For each word in doc
    for (var w in docJson) {
        var wordJson = docJson[w];
        var word = jsonToWord(wordJson);
        docModel.push(word);
    }

    return docModel;
}

// Convert document model to text
const modelToDoc = (doc) => {
    var text = "";
    doc.forEach((word) => {
        word.getLetters().forEach((letter) => {
            if (letter.isModified) {
                text += letter.newValue;
            } else {
                text += letter.value;
            }
        })
        text += " ";
    })
    return text;
}

// Convert json object to word object
const jsonToWord = (obj) => {
    var word = new Word(obj.value);
    // Set level
    if (obj.hasOwnProperty('level')) {
        word.level = obj.level;
    }
    // Set word flags
    if (obj.hasOwnProperty('flags')) {
        word.setFlags(obj.flags);
    }
    // Set letters
    var letters = obj.letters;
    for (var l in letters) {
        var letter = new Letter(letters[l].value);
        // Set letter flags
        if (letters[l].hasOwnProperty('flags')) {
            letter.setFlags(letters[l].flags);
        }
        word.letters.push(letter);
    }
    // Set suggestions
    if (obj.hasOwnProperty('suggestions')) {
        word.addSuggestion(Object.assign({}, word)); // Add self to the suggestion list
        for (var s in obj.suggestions) {
            // var sugg = obj.suggestions[s];
            var suggWord = this.jsonToWord(obj.suggestions[s]);
            word.addSuggestion(suggWord);
        }
    }

    return word;
}

export const grammar = {
    docToModel,
    modelToDoc,
    jsonToWord
}