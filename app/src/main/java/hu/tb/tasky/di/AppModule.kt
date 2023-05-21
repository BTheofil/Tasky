package hu.tb.tasky.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.tasky.data.date_source.TaskyDatabase
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.data.repository.TaskEntityRepositoryImpl
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskyDatabase {
        return Room.databaseBuilder(
            app,
            TaskyDatabase::class.java,
            TaskyDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskyDatabase): TaskEntityRepositoryImpl {
        return TaskEntityRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideAlarmReceiver(appContext: Application): AlarmScheduler {
        return AlarmScheduler(appContext)
    }

    @Singleton
    @Provides
    fun provideDataStoreProto(app: Application): DataStoreProtoRepository {
        return DataStoreProtoRepository(app)
    }
}

