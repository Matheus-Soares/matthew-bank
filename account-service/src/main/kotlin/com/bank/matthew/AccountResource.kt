package com.bank.matthew

import com.bank.matthew.client.Transaction
import com.bank.matthew.client.TransactionProxy
import com.bank.matthew.operations.Transfer
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.net.URI
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/accounts")
class AccountResource {

    @Inject
    lateinit var accountService: AccountService

    @RestClient
    lateinit var transactionProxy: TransactionProxy

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/withdraw")
    fun withdraw(withdraw: Withdraw): Response {
        val account = accountService.checkIfOperationIsValidAndGetAccount(withdraw.account, withdraw.amount)
        return if (account != null) {
            account.balance -= withdraw.amount
            account.persist()

            val transaction = Transaction().apply {
                bankUserId = account.bankUserId
                accountId = account.id.toString()
                type = Transaction.TransactionType.DEBIT
                amount = withdraw.amount
            }

            transactionProxy.createTransaction(transaction)
            Response.ok(account).build()
        } else Response.status(Response.Status.BAD_REQUEST).build()
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transfer")
    fun transfer(transfer: Transfer): Response {
        val fromAccount = accountService.checkIfOperationIsValidAndGetAccount(transfer.fromAccount, transfer.amount)
        return if (fromAccount != null) {
            val toAccount = transfer.toAccount.toLongOrNull()?.let { Account.findById(it) }
                ?: return Response.status(Response.Status.BAD_REQUEST).build()

            fromAccount.balance -= transfer.amount
            fromAccount.persist()

            toAccount.balance += transfer.amount
            toAccount.persist()

            val fromTransaction = Transaction().apply {
                bankUserId = fromAccount.bankUserId
                accountId = fromAccount.id.toString()
                type = Transaction.TransactionType.DEBIT
                amount = transfer.amount
                currentBalance = fromAccount.balance
            }

            val toTransaction = Transaction().apply {
                bankUserId = toAccount.bankUserId
                accountId = toAccount.id.toString()
                type = Transaction.TransactionType.CREDIT
                amount = transfer.amount
                currentBalance = toAccount.balance
            }

            transactionProxy.createTransaction(fromTransaction)
            transactionProxy.createTransaction(toTransaction)
            Response.ok(fromAccount).build()
        } else Response.status(Response.Status.BAD_REQUEST).build()
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deposit")
    fun deposit(deposit: Deposit): Response {
        val account = deposit.account.toLongOrNull()?.let { Account.findById(it) }
            ?: return Response.status(Response.Status.BAD_REQUEST).build()

        account.balance += deposit.amount
        account.persist()

        val transaction = Transaction().apply {
            bankUserId = account.bankUserId
            accountId = account.id.toString()
            type = Transaction.TransactionType.CREDIT
            amount = deposit.amount
            currentBalance = account.balance
        }

        transactionProxy.createTransaction(transaction)
        return Response.ok(account).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): Response {
        val accounts = Account.listAll().sortedBy { it.id!! }
        return Response.ok(accounts).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bankUser/{id}")
    fun getAllByBankUserId(@PathParam("id") id: String): Response {
        val userAccounts = Account.list("bankuserid", id)
        return Response.ok(userAccounts).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val account = Account.findById(id)
        return if (account != null)
            Response.ok(account).build()
        else
            Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createAccount(account: Account): Response {
        account.persist()
        return if (account.isPersistent())
            Response.created(URI.create("/accounts/${account.id}")).build()
        else
            Response.status(Response.Status.BAD_REQUEST).build()
    }

}