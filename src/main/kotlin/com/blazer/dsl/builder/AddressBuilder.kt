package com.blazer.dsl.builder

import com.blazer.dsl.Address

@PersonDsl
class AddressBuilder {
    var country: String? = null
    var region: String? = null
    var area: String? = null
    var city: String? = null
    var street: String? = null
    var house: String? = null
    var building: String? = null
    var room: String? = null

    val countries = setOf("Russia")

    fun country(country: String): AddressBuilder {
        check(this.country == null) { "Country already declared" }
        check(validateCountry(country)) { "Uknown country $country" }

        this.country = country
        return this
    }

    fun region(region: String): AddressBuilder {
        check(this.region == null) { "Region already declared" }
        check(this.country != null) { "Country not declared" }
        check(validateRegion(region)) { "Unknown region $region" }

        this.region = region
        return this
    }

    fun area(area: String): AddressBuilder {
        // TOOD checks
        this.area = area
        return this
    }

    fun city(city: String): AddressBuilder {
        // TOOD checks
        this.city = city
        return this
    }

    fun street(street: String): AddressBuilder {
        // TOOD checks
        this.street = area
        return this
    }

    fun house(house: String): AddressBuilder {
        // TOOD checks
        this.house = house
        return this
    }

    fun building(building: String): AddressBuilder {
        // TOOD checks
        this.building = building
        return this
    }

    fun room(room: String): AddressBuilder {
        // TOOD checks
        this.room = room
        return this
    }

    private fun validateCountry(country: String): Boolean = country in countries
    private fun validateRegion(region: String): Boolean = true

    fun build() = Address(
        country,
        region,
        area,
        city,
        street,
        house,
        building,
        room
    )
}
