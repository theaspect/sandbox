package com.blazer.dsl.builder

import com.blazer.dsl.Gender

fun main() {
    // Create via pattern builder
    val db2 = listOf(
            PersonBuilder()
                    .name("Linnick", "Constantine")
                    .dateOfBirth(1988, 8, 17)
                    .gender(Gender.MALE)
                    .address(
                            AddressBuilder()
                                    .country("Russia")
                                    .region("Kemerovskaya oblast")
                                    .city("Kemerovo")
                                    .street("Bakinsky pereulok")
                                    .house("18b")
                                    .room("112")
                                    .build()
                    )
                    .build(),
            PersonBuilder()
                    .name("Vasilisa", "Pupkina", "Petrovna")
                    .dateOfBirth(1996, 2, 29)
                    .gender(Gender.FEMALE)
                    .address(
                            AddressBuilder()
                                    .country("Russia")
                                    .region("Novosibirskaya oblast")
                                    .area("Bolotny rayon")
                                    .city("Demidovo")
                                    .street("Lenina")
                                    .house("1")
                                    .build()
                    )
                    .address(
                            AddressBuilder()
                                    .country("Russia")
                                    .region("Kemerovskaya oblast")
                                    .city("Novokuznetsk")
                                    .street("Sovetskaya")
                                    .house("10")
                                    .building("2")
                                    .room("20")
                                    .build()
                    )
                    .build()
    )

    println(db2)
}
