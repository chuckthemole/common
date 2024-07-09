package com.rumpus.common.util;

public class Uri implements com.rumpus.common.ICommon {

    private static final String[] HTTP_HTTPS_SCHEMES = {"http","https"};

    // is url valid taken from https://stackoverflow.com/questions/2230676/how-to-check-for-a-valid-url-in-java
    // TODO: maybe implement my own at some point instead of using Apache Commons. Can look at regex example for inspo - chuck 4/30/2024
    /**
     * Check if a URL is valid
     * 
     * @param url the URL to check
     * @param restrictToHttpAndHttps whether to use http/https schemes or include all schemes
     * @return true if the URL is valid, false otherwise
     */
    public static boolean isValidURL(final String url, final boolean restrictToHttpAndHttps) {
        org.apache.commons.validator.routines.UrlValidator urlValidator = 
            restrictToHttpAndHttps ? // use http or https schemes if specified
            new org.apache.commons.validator.routines.UrlValidator(Uri.HTTP_HTTPS_SCHEMES) :
            new org.apache.commons.validator.routines.UrlValidator();
        return urlValidator.isValid(url);
    }
}
