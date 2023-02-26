package hu.tb.tasky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.tasky.data.date_source.MockTask
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import hu.tb.tasky.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMockTaskDatabase(): MockTask {
        return MockTask()
    }

    @Provides
    @Singleton
    fun provideMockTaskRepository(db: MockTask): TaskRepository {
        return TaskRepositoryImpl(db)
    }
}