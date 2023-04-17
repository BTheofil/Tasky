package hu.tb.tasky.domain.use_case

import hu.tb.tasky.ui.add_edit_task.AddEditTaskState
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class ValidateDateTimeTest {

    private val validateDateTime = ValidateDateTime()
    private lateinit var mockState: AddEditTaskState

    @Before
    fun setup() {
        mockState = AddEditTaskState(
            title = "",
            description = "",
            expireDate = LocalDate.now(),
            expireTime = LocalTime.now(),
            initialChecked = false,
            isDateTimeError = false,
            isTitleError = false,
        )
    }

    @Test
    fun validate_date_and_time_now() {
        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_yesterday() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().minusDays(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_lastMonth() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().minusMonths(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_lastYear() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().minusYears(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_lastHour() {

        mockState = mockState.copy(
            expireTime = LocalTime.now().minusHours(1)
        )

        val result = validateDateTime.execute(mockState)


        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_lastMinutes() {

        mockState = mockState.copy(
            expireTime = LocalTime.now().minusMinutes(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.ERROR, result)
    }

    @Test
    fun validate_date_and_time_plusMinutes() {

        mockState = mockState.copy(
            expireTime = LocalTime.now().plusMinutes(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

    @Test
    fun validate_date_and_time_plusHour() {

        mockState = mockState.copy(
            expireTime = LocalTime.now().plusHours(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

    @Test
    fun validate_date_and_time_plusDay() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().plusDays(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

    @Test
    fun validate_date_and_time_plusMonth() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().plusMonths(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

    @Test
    fun validate_date_and_time_plusYear() {

        mockState = mockState.copy(
            expireDate = LocalDate.now().plusYears(1)
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

    @Test
    fun validate_date_and_time_null() {

        mockState = mockState.copy(
            expireDate = null,
            expireTime = null
        )

        val result = validateDateTime.execute(mockState)

        Assert.assertEquals(ValidationResult.SUCCESS, result)
    }

}