package com.bank.matthew

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.util.*
import javax.persistence.Entity

@Entity
class Account : PanacheEntity {
    companion object : PanacheCompanion<Account>

    lateinit var bankUserId: String

    lateinit var type: AccountType

    var balance = 0L

    constructor()
    constructor(bankUserId: String, type: AccountType) : super() {
        this.bankUserId = bankUserId
        this.type = type
        this.balance = 0L
    }

    enum class AccountType {
        PF, PJ
    }
}
