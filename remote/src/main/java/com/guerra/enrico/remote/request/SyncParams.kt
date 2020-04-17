package com.guerra.enrico.remote.request

import com.guerra.enrico.models.sync.SyncAction
import java.util.*

/**
 * Created by enrico
 * on 16/04/2020.
 */
data class SyncParams(
  val lastSync: Date?,
  val syncActions: List<SyncAction>
)