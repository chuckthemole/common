package com.rumpus.common.util;

/**
 * TODO: This could probably use more language codes and a better way to handle them.
 * Just using the ISO 639-3 standard for now.
 */
public final class Language {

    private Language() {
        // private constructor to prevent instantiation
    }

    // create an enum for the languages
    // language the language code, which follows ISO 639-3 standard.
    public enum LanguageCode {
        ENGLISH("eng"),
        GERMAN("deu"),
        FRENCH("fra"),
        SPANISH("spa"),
        ITALIAN("ita"),
        DUTCH("nld"),
        PORTUGUESE("por"),
        RUSSIAN("rus"),
        JAPANESE("jpn"),
        CHINESE("chi_sim"),
        KOREAN("kor");

        private final String language;

        LanguageCode(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }
    }
}
