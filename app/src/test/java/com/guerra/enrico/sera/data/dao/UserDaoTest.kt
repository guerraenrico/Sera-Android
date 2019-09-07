package com.guerra.enrico.sera.data.dao

import androidx.room.EmptyResultSetException
import com.guerra.enrico.sera.BaseDatabaseTest
import com.guerra.enrico.sera.data.local.dao.UserDao
import com.guerra.enrico.sera.user1
import org.junit.Test

/**
 * Created by enrico
 * on 05/01/2019.
 */
class UserDaoTest: BaseDatabaseTest() {
    lateinit var userDao: UserDao

    override fun setup() {
        super.setup()
        userDao = db.userDao()
    }

    @Test
    fun insert() {
        userDao.insert(user1)
        userDao.getFirst(userId = user1.id)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(user1)
    }

    @Test
    fun clear() {
        userDao.clear()
        userDao.getFirst(userId = user1.id)
                .test()
                .assertSubscribed()
                .assertError(EmptyResultSetException::class.java)
    }
}