package de.fhro.mis.dockerCompose.api;

import de.fhro.mis.dockerCompose.entities.ToDo;
import de.fhro.mis.dockerCompose.models.dto.CountResult;
import de.fhro.mis.dockerCompose.models.dto.ToDoArrayResult;
import de.fhro.mis.dockerCompose.repository.Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Peter Kurfer
 * Created on 8/24/17.
 */
@Stateless
@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
public class ToDoApi {

	private final Logger logger;
	private Repository<ToDo> toDoRepository;

	@Inject
	public ToDoApi(Repository<ToDo> toDoRepository) {
		logger = Logger.getLogger(ToDoApi.class.getName());
		this.toDoRepository = toDoRepository;
	}

	@GET
	@Path("/")
	@Operation(summary = "Get a page of todos", method = "GET")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully queried todo page"),
			@ApiResponse(responseCode = "500", description = "Error while fetching todo page")
	})
	public Response getAllToDos(
			@DefaultValue("0")
			@QueryParam("skip")
			@Max(Integer.MAX_VALUE)
			@Min(0)
					int skip,
			@DefaultValue("20")
			@QueryParam("take")
			@Max(100)
			@Min(1)
					int take
	) {
		try {
			return Response.ok(new ToDoArrayResult(toDoRepository.findAll(take, skip))).build();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e, () -> "Failed to query a page of todo entries");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("{id}")
	@Operation(summary = "Get a specific todo entry", method = "GET")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully queried todo entry"),
			@ApiResponse(responseCode = "404", description = "Todo with specified id not found"),
			@ApiResponse(responseCode = "500", description = "Internal error while querying todo with specified id")
	})
	public Response getToDo(
			@PathParam("id")
			@Min(1)
					long id) {
		try {
			ToDo result = toDoRepository.find(id);
			if (result == null) return Response.status(Response.Status.NOT_FOUND).build();
			return Response.ok(result).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, e, () -> "Failed to query todo entry by id");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("count")
	@Operation(summary = "Get count of todo entries", method = "GET")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully queried todo count"),
			@ApiResponse(responseCode = "500", description = "Internal error while querying todo count")
	})
	public Response getCount() {
		return Response.ok(new CountResult(toDoRepository.count())).build();
	}

	@POST
	@Path("/")
	@Operation(summary = "Create a new todo", method = "POST")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully created a new todo"),
			@ApiResponse(responseCode = "500", description = "Failed to create new todo")
	})
	public Response createTodo(@Valid ToDo toDoToCreate) {
		try {
			toDoRepository.create(toDoToCreate);
			return Response.ok(toDoToCreate).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, e, () -> "Failed to create a new todo entry");
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("{id}/done")
	@Operation(summary = "Mark an existing todo item as done")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Updated successfully"),
			@ApiResponse(responseCode = "404", description = "Todo entry with specified id not found"),
			@ApiResponse(responseCode = "409", description = "Item already done"),
			@ApiResponse(responseCode = "500", description = "Internal error while marking todo item as done")
	})
	public Response markAsDone(@PathParam("id") long id) {
		ToDo item = toDoRepository.find(id);
		if (item == null) return Response.status(Response.Status.NOT_FOUND).build();
		if (item.getCompleted() != null) return Response.status(Response.Status.CONFLICT).build();
		item.setCompleted(LocalDateTime.now());
		toDoRepository.edit(item);
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	@Operation(summary = "Delete a todo entry", method = "DELETE")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully deleted todo entry"),
			@ApiResponse(responseCode = "404", description = "Todo entry with specified id not found"),
			@ApiResponse(responseCode = "500", description = "Internal error while deleting todo entry")
	})
	public Response deleteTodo(
			@PathParam("id")
			@Min(1)
					long id
	) {
		try {
			logger.log(Level.INFO, "test log");
			ToDo toDoToDelete = toDoRepository.find(id);
			if (toDoToDelete == null) return Response.status(404).build();
			toDoRepository.remove(toDoToDelete);
			return Response.ok(toDoToDelete).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, e, () -> "Failed to delete a todo entry by id");
			return Response.serverError().build();
		}
	}
}
