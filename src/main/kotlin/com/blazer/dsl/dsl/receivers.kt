package com.blazer.dsl.dsl

import com.blazer.dsl.builder.AddressBuilder
import com.blazer.dsl.builder.PersonBuilder
import com.blazer.dsl.builder.PersonDsl

/**
 * Lambda with receiver
 */
@PersonDsl
fun person(body: PersonBuilder.() -> Unit) = PersonBuilder().apply(body).build()

/**
 * Add method to person builder
 */
fun PersonBuilder.address(body: AddressBuilder.() -> Unit) {
    val ab = AddressBuilder()
    ab.body()
    addresses.add(ab.build())
}
