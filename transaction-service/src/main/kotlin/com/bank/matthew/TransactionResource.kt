package com.bank.matthew

import java.net.URI
import java.util.*
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/transactions")
class TransactionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): Response {
        val transactions = Transaction.listAll()
        return Response.ok(transactions).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bankUser/{id}")
    fun getAllByBankUserId(@PathParam("id") id: String): Response {
        val userTransactions =  Transaction.list("bankuserid", id).sortedBy { it.timestamp }
        return Response.ok(userTransactions).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/account/{id}")
    fun getAllByAccountId(@PathParam("id") id: String): Response {
        val accountTransactions =  Transaction.list("accountid", id).sortedBy { it.timestamp }
        return Response.ok(accountTransactions).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val transaction = Transaction.findById(id)
        return if (transaction != null)
            Response.ok(transaction).build()
        else
            Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createTransaction(transaction: Transaction): Response {
        transaction.timestamp = Date()
        transaction.persist()
        return if (transaction.isPersistent())
            Response.created(URI.create("/transactions/${transaction.id}")).build()
        else
            Response.status(Response.Status.BAD_REQUEST).build()
    }
}