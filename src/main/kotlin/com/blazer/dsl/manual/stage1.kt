package com.blazer.dsl.manual

import com.blazer.dsl.Address
import com.blazer.dsl.Gender
import com.blazer.dsl.Person
import java.time.LocalDate

/**
 * Stage 1: create person manually
 */
fun main() {
    val db1 = listOf(
            Person(
                    firstName = "Linnick",
                    lastName = "Constantine",
                    patronymic = "",
                    dateOfBirth = LocalDate.of(1988, 8, 17),
                    gender = Gender.MALE,
                    addresses = listOf(
                            Address(
                                    country = "Russia",
                                    region = "Kemerovskaya oblast",
                                    area = null,
                                    city = "Kemerovo",
                                    street = "Bakinsky pereulok",
                                    house = "18b",
                                    building = null,
                                    room = "112"
                            )
                    )
            ),
            Person(
                    firstName = "Vasilisa",
                    lastName = "Pupkina",
                    patronymic = "Petrovna",
                    dateOfBirth = LocalDate.of(1996, 2, 29),
                    gender = Gender.FEMALE,
                    addresses = listOf(
                            Address(
                                    country = "Russia",
                                    region = "Novosibirskaya oblast",
                                    area = "Bolotny rayon",
                                    city = "Demidovo",
                                    street = "Lenina",
                                    house = "1",
                                    building = null,
                                    room = null
                            ),
                            Address(
                                    country = "Russia",
                                    region = "Kemerovskaya oblast",
                                    area = null,
                                    city = "Novokuznetsk",
                                    street = "Sovetskaya",
                                    house = "10",
                                    building = "2",
                                    room = "20"
                            )
                    )
            )
    )

    println(db1)
}
