@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.directDimens

import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

/**
 * Created by enrico
 * on 29/02/2020.
 */

private val regexNumber = Regex("[0-9]")

class DirectDimenUseDetector : ResourceXmlDetector() {
  override fun getApplicableAttributes(): Collection<String>? {
    return listOf(
      "layout_width",
      "layout_height",
      "minHeight",
      "minWidth",
      "maxHeight",
      "maxWidth",
      "width",
      "height",
      "radius",
      "bottom",
      "left",
      "right",
      "top",
      "textSize",
      "elevation",
      "padding",
      "paddingTop",
      "paddingBottom",
      "paddingLeft",
      "paddingStart",
      "paddingRight",
      "paddingEnd",
      "layout_margin",
      "layout_marginTop",
      "layout_marginBottom",
      "layout_marginLeft",
      "layout_marginStart",
      "layout_marginRight",
      "layout_marginEnd"
    )
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    if (regexNumber.containsMatchIn(attribute.value)) {
      context.report(
        issue = DirectDimenUseIssue.ISSUE,
        location = context.getLocation(attribute),
        message = DirectDimenUseIssue.EXPLANATION
      )
    }
  }
}