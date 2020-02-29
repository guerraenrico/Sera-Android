@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.missingNightColor

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element

/**
 * Created by enrico
 * on 29/02/2020.
 */
private const val COLOR = "color"

class MissingNightColorDetector : ResourceXmlDetector() {
  private val nightColors = mutableListOf<String>()
  private val regularColors = mutableMapOf<String, Location>()

  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.VALUES
  }

  override fun getApplicableElements(): Collection<String>? {
    return listOf(COLOR)
  }

  override fun visitElement(context: XmlContext, element: Element) {
    val configuration = context.getFolderConfiguration() ?: return
    if (configuration.isDefault) {
      regularColors[element.getAttribute("name")] = context.getLocation(element)
    } else if (configuration.nightModeQualifier.isValid) {
      nightColors.add(element.getAttribute("name"))
    }
  }

  override fun afterCheckEachProject(context: Context) {
    regularColors.forEach { (color, location) ->
      if (!nightColors.contains(color)) {
        context.report(
          MissingNightColorIssue.ISSUE,
          location,
          MissingNightColorIssue.EXPLANATION
        )
      }
    }
  }

}