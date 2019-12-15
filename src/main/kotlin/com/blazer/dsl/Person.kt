package com.blazer.dsl

import java.time.LocalDate

data class Person(
        val firstName: String? = null,
        val lastName: String? = null,
        val patronymic: String? = null,
        val dateOfBirth: LocalDate? = null,
        val gender: Gender? = null,
        val addresses: List<Address>? = null
)
