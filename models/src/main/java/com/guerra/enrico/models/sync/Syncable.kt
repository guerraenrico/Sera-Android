package com.guerra.enrico.models.sync

import com.google.gson.Gson

interface Syncable {
  fun toSyncAction(operation: Operation, gson: Gson): SyncAction
}