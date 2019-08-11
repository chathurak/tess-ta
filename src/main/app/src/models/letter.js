export class Letter {

    constructor(value) {
      this.value = value;
      this.flags = [];
      this.isModified = false;
      this.newValue = "";
    }

    STYLE_NAME = {
        'CHARACTER_LEGITIMACY_ERROR' : 'flag-letter-character-legitimacy-error',
        'GRAMMAR_LEGITIMACY_ERROR'   : 'flag-letter-grammar-legitimacy-error',
        'CHANGED'                    : 'flag-letter-changed',
        'OPTIONAL'                   : 'flag-letter-optional'
    }


    setFlags(flags){
        for (var f in flags){
            this.flags.push(flags[f]);
        }
    }

    modifyTo(value){
        this.isModified = true;
        this.newValue = value;
    }

    reset(){
        this.isModified = false;
        this.newValue = "";
    }

    getStyle() {
        if (this.flags.length > 0){
            return this.STYLE_NAME[this.flags[0]];
        }
        return "";
    }
}
