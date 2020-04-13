package com.languagematters.tessta.grammar.config;

import com.google.common.collect.ImmutableBiMap;
import org.jetbrains.annotations.Contract;

public class SinhalaUnicode {

    private static final ImmutableBiMap<String, Character> vowels;

    private static final ImmutableBiMap<String, Character> consonants;

    private static final ImmutableBiMap<String, Character> modifiers;

    static {
        vowels = ImmutableBiMap.<String, Character>builder()
                .put("SINHALA_LETTER_AYANNA", '\u0D85')   // sinhala letter a
                .put("SINHALA_LETTER_AAYANNA", '\u0D86')   // sinhala letter aa
                .put("SINHALA_LETTER_AEYANNA", '\u0D87')   // sinhala letter ae
                .put("SINHALA_LETTER_AEEYANNA", '\u0D88')   // sinhala letter aae
                .put("SINHALA_LETTER_IYANNA", '\u0D89')   // sinhala letter i
                .put("SINHALA_LETTER_IIYANNA", '\u0D8A')   // sinhala letter ii
                .put("SINHALA_LETTER_UYANNA", '\u0D8B')   // sinhala letter u
                .put("SINHALA_LETTER_UUYANNA", '\u0D8C')   // sinhala letter uu
                .put("SINHALA_LETTER_IRUYANNA", '\u0D8D')   // sinhala letter vocalic r
                .put("SINHALA_LETTER_IRUUYANNA", '\u0D8E')   // sinhala letter vocalic rr
                .put("SINHALA_LETTER_ILUYANNA", '\u0D8F')   // sinhala letter vocalic l
                .put("SINHALA_LETTER_ILUUYANNA", '\u0D90')   // sinhala letter vocalic ll
                .put("SINHALA_LETTER_EYANNA", '\u0D91')   // sinhala letter e
                .put("SINHALA_LETTER_EEYANNA", '\u0D92')   // sinhala letter ee
                .put("SINHALA_LETTER_AIYANNA", '\u0D93')   // sinhala letter ai
                .put("SINHALA_LETTER_OYANNA", '\u0D94')   // sinhala letter o
                .put("SINHALA_LETTER_OOYANNA", '\u0D95')   // sinhala letter oo
                .put("SINHALA_LETTER_AUYANNA", '\u0D96')   // sinhala letter au
                .build();

        consonants = ImmutableBiMap.<String, Character>builder()
                .put("SINHALA_LETTER_ALPAPRAANA_KAYANNA", '\u0D9A')  // sinhala letter ka
                .put("SINHALA_LETTER_MAHAAPRAANA_KAYANNA", '\u0D9B')  // sinhala letter kha
                .put("SINHALA_LETTER_ALPAPRAANA_GAYANNA", '\u0D9C')  // sinhala letter ga
                .put("SINHALA_LETTER_MAHAAPRAANA_GAYANNA", '\u0D9D')  // sinhala letter gha
                .put("SINHALA_LETTER_KANTAJA_NAASIKYAYA", '\u0D9E')  // sinhala letter nga
                .put("SINHALA_LETTER_SANYAKA_GAYANNA", '\u0D9F')  // sinhala letter nnga
                .put("SINHALA_LETTER_ALPAPRAANA_CAYANNA", '\u0DA0')  // sinhala letter ca
                .put("SINHALA_LETTER_MAHAAPRAANA_CAYANNA", '\u0DA1')  // sinhala letter cha
                .put("SINHALA_LETTER_ALPAPRAANA_JAYANNA", '\u0DA2')  // sinhala letter ja
                .put("SINHALA_LETTER_MAHAAPRAANA_JAYANNA", '\u0DA3')  // sinhala letter jha
                .put("SINHALA_LETTER_TAALUJA_NAASIKYAYA", '\u0DA4')  // sinhala letter nya
                .put("SINHALA_LETTER_TAALUJA_SANYOOGA_NAAKSIKYAYA", '\u0DA5')  // sinhala letter jnya
                .put("SINHALA_LETTER_SANYAKA_JAYANNA", '\u0DA6')  // sinhala letter nyja
                .put("SINHALA_LETTER_ALPAPRAANA_TTAYANNA", '\u0DA7')  // sinhala letter tta
                .put("SINHALA_LETTER_MAHAAPRAANA_TTAYANNA", '\u0DA8')  // sinhala letter ttha
                .put("SINHALA_LETTER_ALPAPRAANA_DDAYANNA", '\u0DA9')  // sinhala letter dda
                .put("SINHALA_LETTER_MAHAAPRAANA_DDAYANNA", '\u0DAA')  // sinhala letter ddha
                .put("SINHALA_LETTER_MUURDHAJA_NAYANNA", '\u0DAB')  // sinhala letter nna
                .put("SINHALA_LETTER_SANYAKA_DDAYANNA", '\u0DAC')  // sinhala letter nndda
                .put("SINHALA_LETTER_ALPAPRAANA_TAYANNA", '\u0DAD')  // sinhala letter ta
                .put("SINHALA_LETTER_MAHAAPRAANA_TAYANNA", '\u0DAE')  // sinhala letter tha
                .put("SINHALA_LETTER_ALPAPRAANA_DAYANNA", '\u0DAF')  // sinhala letter da
                .put("SINHALA_LETTER_MAHAAPRAANA_DAYANNA", '\u0DB0')  // sinhala letter dha
                .put("SINHALA_LETTER_DANTAJA_NAYANNA", '\u0DB1')  // sinhala letter na
                .put("SINHALA_LETTER_SANYAKA_DAYANNA", '\u0DB3')  // sinhala letter nda
                .put("SINHALA_LETTER_ALPAPRAANA_PAYANNA", '\u0DB4')  // sinhala letter pa
                .put("SINHALA_LETTER_MAHAAPRAANA_PAYANNA", '\u0DB5')  // sinhala letter pha
                .put("SINHALA_LETTER_ALPAPRAANA_BAYANNA", '\u0DB6')  // sinhala letter ba
                .put("SINHALA_LETTER_MAHAAPRAANA_BAYANNA", '\u0DB7')  // sinhala letter bha
                .put("SINHALA_LETTER_MAYANNA", '\u0DB8')  // sinhala letter ma
                .put("SINHALA_LETTER_AMBA_BAYANNA", '\u0DB9')  // sinhala letter mba
                .put("SINHALA_LETTER_YAYANNA", '\u0DBA')  // sinhala letter ya
                .put("SINHALA_LETTER_RAYANNA", '\u0DBB')  // sinhala letter ra
                .put("SINHALA_LETTER_DANTAJA_LAYANNA", '\u0DBD')  // sinhala letter la
                .put("SINHALA_LETTER_VAYANNA", '\u0DC0')  // sinhala letter va
                .put("SINHALA_LETTER_TAALUJA_SAYANNA", '\u0DC1')  // sinhala letter sha
                .put("SINHALA_LETTER_MUURDHAJA_SAYANNA", '\u0DC2')  // sinhala letter ssa
                .put("SINHALA_LETTER_DANTAJA_SAYANNA", '\u0DC3')  // sinhala letter sa
                .put("SINHALA_LETTER_HAYANNA", '\u0DC4')  // sinhala letter ha
                .put("SINHALA_LETTER_MUURDHAJA_LAYANNA", '\u0DC5')  // sinhala letter lla
                .put("SINHALA_LETTER_FAYANNA", '\u0DC6')  // sinhala letter fa
                .build();

        modifiers = ImmutableBiMap.<String, Character>builder()
                .put("SINHALA_VOWEL_SIGN_AELA-PILLA", '\u0DCF')  // sinhala vowel sign aa
                .put("SINHALA_VOWEL_SIGN_KETTI_AEDA-PILLA", '\u0DD0')  // sinhala vowel sign ae
                .put("SINHALA_VOWEL_SIGN_DIGA_AEDA-PILLA", '\u0DD1')  // sinhala vowel sign aae
                .put("SINHALA_VOWEL_SIGN_KETTI_IS-PILLA", '\u0DD2')  // sinhala vowel sign i
                .put("SINHALA_VOWEL_SIGN_DIGA_IS-PILLA", '\u0DD3')  // sinhala vowel sign ii
                .put("SINHALA_VOWEL_SIGN_KETTI_PAA-PILLA", '\u0DD4')  // sinhala vowel sign u
                .put("SINHALA_VOWEL_SIGN_DIGA_PAA-PILLA", '\u0DD6')  // sinhala vowel sign uu
                .put("SINHALA_VOWEL_SIGN_GAETTA-PILLA", '\u0DD8')  // sinhala vowel sign vocalic r
                .put("SINHALA_VOWEL_SIGN_KOMBUVA", '\u0DD9')  // sinhala vowel sign e
                .put("SINHALA_VOWEL_SIGN_DIGA_KOMBUVA", '\u0DDA')  // sinhala vowel sign ee
                .put("SINHALA_VOWEL_SIGN_KOMBU_DEKA", '\u0DDB')  // sinhala vowel sign ai
                .put("SINHALA_VOWEL_SIGN_KOMBUVA_HAA_AELAPILLA", '\u0DDC')  // sinhala vowel sign o
                .put("SINHALA_VOWEL_SIGN_KOMBUVA_HAA_DIGA_AELA-PILLA", '\u0DDD')  // sinhala vowel sign oo
                .put("SINHALA_VOWEL_SIGN_KOMBUVA_HAA_GAYANUKITTA", '\u0DDE')  // sinhala vowel sign au
                .put("SINHALA_VOWEL_SIGN_DIGA_GAETTA-PILLA", '\u0DF2')  // sinhala vowel sign vocalic rr
                .put("SINHALA_VOWEL_SIGN_DIGA_GAYANUKITTA", '\u0DF3')  // sinhala vowel sign vocalic ll
                .put("SINHALA_SIGN_ANUSVARAYA", '\u0D82')  // anusvara
                .put("SINHALA_SIGN_VISARGAYA", '\u0D83')  // visarga
                .put("SINHALA_SIGN_AL-LAKUNA", '\u0DCA') //  virama
                .build();
    }

    /**
     * Check whether a char is a vowel
     *
     * @param c
     * @return
     */
    @Contract(pure = true)
    public static boolean isVowel(char c) {
        return vowels.containsValue(c);
    }

    /**
     * Check whether a char is a consonant
     *
     * @param c
     * @return
     */
    @Contract(pure = true)
    public static boolean isConsonant(char c) {
        return consonants.containsValue(c);
    }

    /**
     * Check whether a char is a modifier
     *
     * @param c
     * @return
     */
    @Contract(pure = true)
    public static boolean isModifier(char c) {
        return modifiers.containsValue(c);
    }

    /**
     * Get character by it's name
     *
     * @param s
     * @return
     */
    public static char get(String s) {
        if (vowels.containsKey(s)) {
            return vowels.get(s);
        } else if (consonants.containsKey(s)) {
            return consonants.get(s);
        } else {
            return modifiers.get(s);
        }
    }

}
