@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.directColor

import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

class DirectColorUseDetector : ResourceXmlDetector() {
  override fun getApplicableAttributes(): Collection<String>? {
    return listOf(
      "background",
      "foreground",
      "src",
      "textColor",
      "textColorHighlight",
      "textColorHint",
      "textColorLink",
      "tint",
      "shadowColor",
      "fillColor",
      "strokeColor",
      "color"
    )
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    if (attribute.value.startsWith("#")) {
      context.report(
        issue = DirectColorUseIssue.ISSUE,
        location = context.getLocation(attribute),
        message = DirectColorUseIssue.EXPLANATION
      )
    }
  }
}