package com.bank.matthew

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AccountRepository {

    fun getById(id: String): Account? {
        return id.toLongOrNull()?.let { Account.findById(it) }
    }
}