package com.tearas.expanemanagementjc.usecases.manager_user

data class LocalUserUseCases(
    val setPassword: SetPassword,
    val getPassword: GetPassword,
    val deletePassword: DeletePassword
)
