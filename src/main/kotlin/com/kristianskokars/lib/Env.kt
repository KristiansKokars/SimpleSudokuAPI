package com.kristianskokars.lib

import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv {
    ignoreIfMalformed = true
    ignoreIfMissing = true
}
