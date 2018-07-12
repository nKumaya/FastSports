package com.example.miteki.fastsports;

public abstract class Config {
    // copy this keys from your developer dashboard
    public static final String ACCESS_TOKEN = "97e5f8a97d0f413796e77061c1928069";

    public static final LanguageConfig[] languages = new LanguageConfig[]{
            new LanguageConfig("ja", "b92617a3f82e4b52b3db44436d2d4b8b"),
    };

    public static final String[] events = new String[]{
            "hello_event",
            "goodbye_event",
            "how_are_you_event"
    };
}
