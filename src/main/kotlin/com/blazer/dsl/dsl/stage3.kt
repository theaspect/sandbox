package com.blazer.dsl.dsl

import com.blazer.dsl.Gender

fun main() {
    // Create via DSL version
    val db3 = listOf(
            person {
                name("Linnick", "Constantine")
                dateOfBirth(1988, 8, 17)
                gender(Gender.MALE)
                address {
                    country("Russia")
                    region("Kemerovskaya oblast")
                    city("Kemerovo")
                    street("Bakinsky pereulok")
                    house("18b")
                    room("112")
                }
            },
            person {
                name("Vasilisa", "Pupkina", "Petrovna")
                dateOfBirth(1996, 2, 29)
                gender(Gender.FEMALE)
                address {

                    // Parent lambda call forbidden
                    // gender(Gender.FEMALE)

                    country("Russia")
                    region("Novosibirskaya oblast")
                    area("Bolotny rayon")
                    city("Demidovo")
                    street("Lenina")
                    house("1")
                }
                address {
                    country("Russia")
                    region("Kemerovskaya oblast")
                    city("Novokuznetsk")
                    street("Sovetskaya")
                    house("10")
                    building("2")
                    room("20")
                }
            }
    )

    // Pattern DSL
    println(db3)
}
