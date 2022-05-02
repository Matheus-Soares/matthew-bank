package com.bank.matthew.client


class Transaction {
    lateinit var bankUserId: String

    lateinit var accountId: String

    lateinit var type: TransactionType

    var amount = 0L

    var currentBalance = 0L

    enum class TransactionType {
        CREDIT, DEBIT
    }
}