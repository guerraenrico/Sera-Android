package com.guerra.enrico.navis_processor

/**
 * Created by enrico
 * on 15/06/2020.
 */
class OptionParser {

  companion object {
    private const val PORTUM_PATH = "portumPath"

    private const val GENERATED_FILE_PATH = "generated\\source\\kapt"
    private const val BUILD_TYPE_DEBUG = "debug"
    private const val BUILD_TYPE_RELEASE = "release"
  }

  val supportedOptions: Set<String>
    get() = setOf(PORTUM_PATH)

  fun filePath(options: Map<String, String>): String {
    val portumPath = options[PORTUM_PATH]
    return "$portumPath\\$GENERATED_FILE_PATH\\$BUILD_TYPE_DEBUG"
  }
}