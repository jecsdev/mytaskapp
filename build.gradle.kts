buildscript {
    extra["compose_ui_version"] = "1.4.1"
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.43.2" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
