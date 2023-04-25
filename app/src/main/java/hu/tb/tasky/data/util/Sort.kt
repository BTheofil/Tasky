package hu.tb.tasky.data.util

sealed class Sort{
    object Title : Sort()
    object Date : Sort()
}
