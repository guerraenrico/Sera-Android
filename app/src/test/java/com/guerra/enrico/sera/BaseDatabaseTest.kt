package com.guerra.enrico.sera

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.data.remote.Api
import kotlinx.coroutines.newSingleThreadContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * Created by enrico
 * on 05/01/2019.
 */
abstract class BaseDatabaseTest: BaseTest() {
    lateinit var db: SeraDatabase
        private set

    val api: Api = mock(Api::class.java)
    val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    open fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(IOException::class)
    open fun closeDb() {
        db.close()
    }
}