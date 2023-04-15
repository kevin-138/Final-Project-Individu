package com.kevin.netkick.domain.entity.fixtures.subfixture

data class Fixture(
    val id: Int,
    val referee: String,
    val timezone: String,
    val date: String,
    val venue: VenueF,
    val status: Status
)
