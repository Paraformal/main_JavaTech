package com.example.main_s2024;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    public static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure().directory("src/main/java/com/example/main_s2024/.env")
                .ignoreIfMalformed().ignoreIfMissing().load();
    }
}
