package com.bank.matthew

import java.net.URI
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/bankusers")
class BankUserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): Response {
        val bankUsers = BankUser.listAll()
        return Response.ok(bankUsers).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val bankUser = BankUser.findById(id)
        return if (bankUser != null)
            Response.ok(bankUser).build()
        else
            Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createUser(bankUser: BankUser): Response {
        bankUser.persist()
        return if (bankUser.isPersistent())
            Response.created(URI.create("/bankusers/${bankUser.id}")).build()
        else
            Response.status(Response.Status.BAD_REQUEST).build()
    }

    @DELETE
    @Transactional
    @Path("{id}")
    fun deleteUserById(@PathParam("id") id: Long): Response {
        val deleted = BankUser.deleteById(id)
        return if (deleted)
            Response.noContent().build()
        else
            Response.status(Response.Status.BAD_REQUEST).build()
    }
}