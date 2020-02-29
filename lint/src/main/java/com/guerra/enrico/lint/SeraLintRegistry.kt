@file:Suppress("UnstableApiUsage")

package com.guerra.enrico.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.guerra.enrico.lint.directColor.DirectColorUseIssue
import com.guerra.enrico.lint.directDimens.DirectDimenUseIssue
import com.guerra.enrico.lint.missingNightColor.MissingNightColorIssue

/**
 * Created by enrico
 * on 27/02/2020.
 */
class SeraLintRegistry : IssueRegistry() {
  override val issues: List<Issue>
    get() = listOf(
      DirectColorUseIssue.ISSUE,
      DirectDimenUseIssue.ISSUE,
      MissingNightColorIssue.ISSUE
    )

  override val api: Int
    get() = CURRENT_API
}