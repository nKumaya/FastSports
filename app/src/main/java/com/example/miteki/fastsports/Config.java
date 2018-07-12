package com.example.miteki.fastsports;

public abstract class Config {
    // copy this keys from your developer dashboard
    public static final String ACCESS_TOKEN = "3485a96fb27744db83e78b8c4bc9e7b7";

    public static final LanguageConfig[] languages = new LanguageConfig[]{
            new LanguageConfig("ja", "b92617a3f82e4b52b3db44436d2d4b8b"),
    };

    public static final String[] events = new String[]{
            "hello_event",
            "goodbye_event",
            "how_are_you_event"
    };
}
