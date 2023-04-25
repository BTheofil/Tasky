package hu.tb.tasky.data.data_source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import hu.tb.tasky.data.date_source.TaskEntityDAO
import hu.tb.tasky.data.date_source.TaskEntityDatabase
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class DAOTest {

    private lateinit var database: TaskEntityDatabase
    private lateinit var dao: TaskEntityDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskEntityDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.taskDao
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllTask() = runBlocking {
        val shoppingItem = TaskEntity(
            title = "hello",
            description = "",
            expireDate = LocalDate.now(),
            expireTime = LocalTime.now(),
            isTaskDone = false,
        )
        dao.insertTaskEntity(shoppingItem)

        val item = dao.getTaskEntityById(1)

        if (item != null) {
            Assert.assertEquals(item.title, "hello")
        }
    }
}