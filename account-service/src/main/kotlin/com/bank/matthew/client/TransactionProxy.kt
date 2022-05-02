package com.bank.matthew.client

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.transaction.Transactional
import javax.ws.rs.Consumes

import javax.ws.rs.GET;
import javax.ws.rs.POST
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
interface TransactionProxy {

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createTransaction(transaction: Transaction): Transaction
}