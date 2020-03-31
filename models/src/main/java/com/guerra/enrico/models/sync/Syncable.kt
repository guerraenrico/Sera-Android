package com.guerra.enrico.models.sync

/**
 * Created by enrico
 * on 15/02/2020.
 */
interface Syncable {
  fun toSyncAction(operation: Operation): SyncAction
}