package com.bank.matthew

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.time.LocalDate
import java.time.Month
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class BankUser : PanacheEntity {
    companion object: PanacheCompanion<BankUser>

    @Column(length = 100)
    lateinit var firstName: String

    @Column(length = 100)
    lateinit var lastName: String

    @Column(length = 50)
    lateinit var email: String

    lateinit var phone: String

    lateinit var birth: LocalDate

    @Column(length = 100)
    lateinit var address: String

    @Column(length = 50)
    lateinit var country: String

    constructor()
    constructor(firstName: String, lastName: String, birth: LocalDate) {
        LocalDate.of(1910, Month.FEBRUARY, 1)
        this.firstName = firstName
        this.lastName = lastName
        this.birth = birth
    }
}
