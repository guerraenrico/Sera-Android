package com.guerra.enrico.sera.data.dao

import com.guerra.enrico.sera.BaseDatabaseTest
import com.guerra.enrico.sera.categories
import com.guerra.enrico.sera.category3
import com.guerra.enrico.data.local.dao.CategoryDao
import org.junit.Test

/**
 * Created by enrico
 * on 05/01/2019.
 */
class CategoryDaoTest: BaseDatabaseTest() {
    private lateinit var categoryDao: com.guerra.enrico.data.local.dao.CategoryDao

    override fun setup() {
        super.setup()
        categoryDao = db.categoryDao()
    }

    @Test
    fun insertAll() {
        categoryDao.insertAll(categories)
        categoryDao.getAllFlowable()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(categories)
    }

    @Test
    fun insertOne() {
        categoryDao.insertOne(category3)
        categoryDao.getAllFlowable()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue { it.contains(category3) }
    }

    @Test
    fun clear() {
        categoryDao.clear()
//        assertThat(categoryDao.getAll(), `is`(emptyList()))
        categoryDao.getAllFlowable()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(emptyList())
    }
}