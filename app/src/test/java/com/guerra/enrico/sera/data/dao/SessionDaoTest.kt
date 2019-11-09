package com.guerra.enrico.sera.data.dao

import androidx.room.EmptyResultSetException
import com.guerra.enrico.sera.BaseDatabaseTest
import com.guerra.enrico.data.local.dao.SessionDao
import com.guerra.enrico.sera.session1
import com.guerra.enrico.sera.session2
import org.junit.Test

/**
 * Created by enrico
 * on 05/01/2019.
 */
class SessionDaoTest: BaseDatabaseTest() {
    lateinit var sessionDao: com.guerra.enrico.data.local.dao.SessionDao

    override fun setup() {
        super.setup()
        sessionDao = db.sessionDao()
    }

    @Test
    fun insert() {
        sessionDao.insert(session1)
        sessionDao.getFirst()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(session1)
    }

    @Test
    fun lastSession() {
        sessionDao.insert(session1)
        sessionDao.insert(session2)
        sessionDao.getFirst()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(session2)
    }

    @Test
    fun noSession() {
        sessionDao.getFirst()
                .test()
                .assertSubscribed()
                .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun clear() {
        sessionDao.getFirst()
                .test()
                .assertSubscribed()
                .assertError(EmptyResultSetException::class.java)
    }
}