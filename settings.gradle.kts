pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            setUrl("https://jitpack.io")
        }
        maven {
            setUrl("https://maven.aliyun.com/nexus/content/repositories/releases")
        }
    }
}

rootProject.name = "my-project"
//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app", ":lib_custom_view")
include(":lib_common")
include(":lib_media_picker")
include(":lib_video_record")
include(":lib_wheel_picker")
include(":lib_zxing")




