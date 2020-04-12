package com.guerra.enrico.models.sync

import com.google.gson.Gson

/**
 * Created by enrico
 * on 15/02/2020.
 */
interface Syncable {
  fun toSyncAction(operation: Operation, gson: Gson): SyncAction
}