package com.bank.matthew

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ForeignKey
import kotlin.properties.Delegates

@Entity
class Transaction : PanacheEntity {
    companion object : PanacheCompanion<Transaction>

    lateinit var bankUserId: String

    lateinit var accountId: String

    lateinit var type: TransactionType

    var amount = 0L

    var currentBalance = 0L

    lateinit var timestamp: Date

    constructor()
    constructor(bankUserId: String, accountId: String, type: TransactionType, amount: Long, timestamp: Date) : super() {
        this.bankUserId = bankUserId
        this.accountId = accountId
        this.type = type
        this.amount = amount
        this.timestamp = timestamp
    }

    enum class TransactionType {
        CREDIT, DEBIT
    }
}
