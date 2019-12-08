package com.guerra.enrico.sera.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.sera.utils.CoroutineContextProviderTest
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.data.remote.Api
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.remote.RemoteDataManagerImpl
import com.guerra.enrico.sera.data.dao.CategoryDaoTest
import com.guerra.enrico.sera.data.dao.SessionDaoTest
import com.guerra.enrico.sera.data.dao.TaskDaoTest
import com.guerra.enrico.sera.data.dao.UserDaoTest
import com.guerra.enrico.sera.data.repo.AuthRepositoryTest
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.auth.AuthRepositoryImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton


/**
 * Created by enrico
 * on 08/12/2019.
 */
@Singleton
@Component(modules = [
  TestDataManagerModule::class
])
interface TestComponent {
  fun inject(test: AuthRepositoryTest)
  fun inject(test: CategoryDaoTest)
  fun inject(test: SessionDaoTest)
  fun inject(test: TaskDaoTest)
  fun inject(test: UserDaoTest)
}

@Module(includes = [TestRoomDatabaseModule::class, TestRetrofitModule::class])
class TestDataManagerModule {
  @Singleton
  @Provides
  fun provideAuhRepositoryTest(): AuthRepositoryTest = AuthRepositoryTest()

  @Singleton
  @Provides
  fun provideAuhRepository(context: Context, remoteDataManager: RemoteDataManager, localDbManager: LocalDbManager): AuthRepository =
          AuthRepositoryImpl(context, remoteDataManager, localDbManager)

  @Singleton
  @Provides
  fun provideLocalDbManager(database: SeraDatabase): LocalDbManager =
          LocalDbManagerImpl(database)

  @Singleton
  @Provides
  fun provideRemoteDataManager(api: Api, gson: Gson, coroutineContextProvider: CoroutineContextProvider): RemoteDataManager =
          RemoteDataManagerImpl(api, gson, coroutineContextProvider)
}

@Module
class TestRetrofitModule {
  @Singleton
  @Provides
  fun provideApi(): Api = Mockito.mock(Api::class.java)

  @Singleton
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Singleton
  @Provides
  fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProviderTest()
}

@Module
class TestRoomDatabaseModule {
  @Provides
  fun provideContext(): Context = ApplicationProvider.getApplicationContext()

  @Singleton
  @Provides
  fun provideDatabase(context: Context): SeraDatabase =
          Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
                  .allowMainThreadQueries()
                  .build()
}