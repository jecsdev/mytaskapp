package com.jecs.mytasklist.feature_task.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
