package com.guerra.enrico.models.sync

/**
 * Created by enrico
 * on 11/04/2020.
 */
data class SyncedEntity(
  val entityName: String,
  val entityId: String,
  val entitySnapshot: String,
  val operation: Operation
)