package com.guerra.enrico.remote.request

import com.guerra.enrico.models.sync.SyncAction
import java.util.*

data class SyncParams(
  val lastSync: Date?,
  val syncActions: List<SyncAction>
)