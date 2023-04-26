package hu.tb.tasky.domain.util

sealed class Sort(var type: SortType){
    class Title(type: SortType) : Sort(type)
    class Date(type: SortType) : Sort(type)
}

sealed class SortType{
    object Ascending: SortType()
    object Descending: SortType()
}


