package com.tearas.expanemanagementjc.usecases.remind

data class RemindUseCases(
    val deleteRemind: DeleteRemind,
    val insertRemind: InsertRemind,
    val updateRemind: UpdateRemind,
    val getRemind: GetRemind,
    val getReminds: GetReminds
)