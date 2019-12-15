package com.blazer.dsl.builder

import com.blazer.dsl.Address
import com.blazer.dsl.Gender
import com.blazer.dsl.Person
import java.time.LocalDate

@PersonDsl
class PersonBuilder {
    var firstName: String? = null
    var lastName: String? = null
    var patronymic: String? = null
    var dateOfBirth: LocalDate? = null
    var gender: Gender? = null
    var addresses: MutableList<Address> = mutableListOf()

    fun name(firstName: String, lastName: String): PersonBuilder {
        this.firstName = firstName
        this.lastName = lastName
        return this
    }

    fun name(firstName: String, lastName: String, patronymic: String): PersonBuilder {
        this.firstName = firstName
        this.lastName = lastName
        this.patronymic = patronymic
        return this
    }

    fun dateOfBirth(year: Int, month: Int, day: Int): PersonBuilder {
        this.dateOfBirth = LocalDate.of(year, month, day)
        return this
    }

    fun gender(gender: Gender): PersonBuilder {
        this.gender = gender
        return this
    }

    fun address(address: Address): PersonBuilder {
        this.addresses.add(address)
        return this
    }

    fun build() = Person(firstName, lastName, patronymic, dateOfBirth, gender, addresses)
}
