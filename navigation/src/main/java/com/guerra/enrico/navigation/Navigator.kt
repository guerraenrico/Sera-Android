package com.guerra.enrico.navigation

import android.net.Uri

interface Navigator {
  fun getUriLogin(): Uri
  fun getUriMain(): Uri
  fun getUriTodos(): Uri
  fun getUriGoals(): Uri
  fun getUriResults(): Uri
  fun getUriSettings(): Uri
}