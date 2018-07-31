package com.example.miteki.fastsports;

public abstract class Config {
    // copy this keys from your developer dashboard
    public static final String ACCESS_TOKEN = BuildConfig.DASHBOARD_ACCESS_TOKEN;

    public static final LanguageConfig[] languages = new LanguageConfig[]{
            new LanguageConfig("ja", BuildConfig.LANGUAGE_ACCESS_TOKEN),
    };

    public static final String[] events = new String[]{
            "hello_event",
            "goodbye_event",
            "how_are_you_event"
    };
}
