package com.bank.matthew

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class AccountService {

    @Inject
    lateinit var accountRepository: AccountRepository

    fun checkIfOperationIsValidAndGetAccount(accountId: String, amount: Long): Account? {
        val account = accountRepository.getById(accountId) ?: throw Exception("Account not found")
        return if (account.balance >= amount) account else null
    }

}