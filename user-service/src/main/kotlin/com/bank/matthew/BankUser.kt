package com.bank.matthew

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class User : PanacheEntity {
    companion object: PanacheCompanion<User>

    @Column(length = 100)
    lateinit var firstName: String

    @Column(length = 100)
    lateinit var lastName: String

    lateinit var birth: LocalDate

    constructor()
    constructor(firstName: String, lastName: String, birth: LocalDate) {
        this.firstName = firstName
        this.lastName = lastName
        this.birth = birth
    }
}
