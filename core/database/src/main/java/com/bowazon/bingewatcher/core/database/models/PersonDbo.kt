package com.bowazon.bingewatcher.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bowazon.bingewatcher.core.entities.Person

@Entity
data class PersonDbo (
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val country: String,
    val birthday: String,
    val gender: String,
)

fun PersonDbo.toPerson() : Person {
    return Person(
        id = id,
        name = name,
        image = image,
        country = country,
        birthday = birthday,
        gender = gender
    )
}