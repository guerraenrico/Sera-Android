@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint.directDimens

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

/**
 * Created by enrico
 * on 29/02/2020.
 */
object DirectDimenUseIssue {
  private const val ID = "DirectDimenUse"
  private const val DESCRIPTION = "Dimen used directly"
  const val EXPLANATION =
    "Avoid direct use of dimens in XML files. This will cause issues with different screen support"
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
      DirectDimenUseDetector::class.java,
      Scope.RESOURCE_FILE_SCOPE
    )
  )
}