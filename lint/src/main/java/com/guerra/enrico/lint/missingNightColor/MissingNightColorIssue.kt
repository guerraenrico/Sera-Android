@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.missingNightColor

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

object MissingNightColorIssue {
  private const val ID = "MissingNightColor"
  private const val DESCRIPTION = "Missing night color"
  const val EXPLANATION = "The night version of this color is missing"
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
      MissingNightColorDetector::class.java,
      Scope.RESOURCE_FILE_SCOPE
    )
  )
}