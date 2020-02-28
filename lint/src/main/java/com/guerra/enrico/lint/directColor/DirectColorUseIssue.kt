@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.directColor

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

object DirectColorUseIssue {
  private const val ID = "DirectColorUse"
  private const val DESCRIPTION = "Color used directly"
  const val EXPLANATION =
    "Avoid direct use of colors in XML files. This will cause issues with different theme support"
  private val CATEGORY = Category.CORRECTNESS
  private const val PRIORITY = 6
  private val SEVERITY = Severity.WARNING

  val ISSUE = Issue.create(
    id = ID,
    briefDescription = DESCRIPTION,
    explanation = EXPLANATION,
    category = CATEGORY,
    priority = PRIORITY,
    severity = SEVERITY,
    implementation = Implementation(
      DirectColorUseDetector::class.java,
      Scope.RESOURCE_FILE_SCOPE
    )
  )
}
