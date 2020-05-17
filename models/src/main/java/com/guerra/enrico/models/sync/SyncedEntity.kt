package com.guerra.enrico.models.sync

import com.guerra.enrico.models.EntityData

/**
 * Created by enrico
 * on 11/04/2020.
 */
data class SyncedEntity(
  val entityData: EntityData,
  val operation: Operation
)