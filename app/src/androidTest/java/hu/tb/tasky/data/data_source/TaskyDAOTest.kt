package hu.tb.tasky.data.data_source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import hu.tb.tasky.data.date_source.TaskyDAO
import hu.tb.tasky.data.date_source.TaskyDatabase
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskyDAOTest {

    private lateinit var dao: TaskyDAO
    private lateinit var db: TaskyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TaskyDatabase::class.java
        ).build()
        dao = db.taskDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList(){
        val task = TaskEntity(
            title = "Test",
            description = "Test description",
            expireDate = null,
            expireTime = null,
            isTaskDone = false,
            listId = 1,
        )
        /*dao.insertTaskEntity(task)
        val byName = dao.getTaskEntities()
        assertThat(byName.collect{ it.first() }, equalTo(task))*/
    }
}