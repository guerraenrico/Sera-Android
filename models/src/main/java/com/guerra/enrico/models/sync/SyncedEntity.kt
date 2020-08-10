package com.guerra.enrico.models.sync

import com.guerra.enrico.models.EntityData

data class SyncedEntity(
  val entityData: EntityData,
  val operation: Operation
)