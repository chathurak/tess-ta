import { Word } from '../models/word';
import { Letter } from '../models/letter';

// Convert document text to data lines
const docToDataLines = (docJson) => {
    var dataLines = [[]];

    // For each word in doc
    for (var w in docJson) {
        var wordJson = docJson[w];
        var word = jsonToWord(wordJson);
        if (word.value === 'NEW_LINE') {
            dataLines.push([]);
        } else {
            dataLines[dataLines.length - 1].push(word);
        }
    }

    return dataLines;
}


// Convert dataLines to text
const dataLinesToText = (dataLines) => {
    var text = "";
    if (dataLines){
        dataLines.forEach(line => {
            line.forEach((word) => {
                word.getLetters().forEach((letter) => {
                    if (letter.isModified) {
                        text += letter.newValue;
                    } else {
                        text += letter.value;
                    }
                })
                text += " ";
            })
            text = text.slice(0, -1) + '\n';
        })
    }
    return text;
}

const jsonToDataLines = (str) => {
    var obj = JSON.parse(str);
    var dataLines = [[]];

    // For each word in doc
    for (var line in obj) {
        for (var w in line){
            var wordJson = obj[w];
            var word = jsonToWord(wordJson);
            dataLines[dataLines.length - 1].push(word);
        }
        dataLines.push([]);
    }

    return dataLines;
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
        // Add self to the suggestion list
        var wordCopy = Object.assign({}, word);
        delete wordCopy.suggestions;
        word.addSuggestion(wordCopy);

        // Add suggestion words
        for (var s in obj.suggestions) {
            // var sugg = obj.suggestions[s];
            var suggWord = this.jsonToWord(obj.suggestions[s]);
            delete suggWord.suggestions;
            word.addSuggestion(suggWord);
        }
    }

    return word;
}

export const grammar = {
    docToDataLines,
    dataLinesToText,
    jsonToDataLines,
    jsonToWord
}