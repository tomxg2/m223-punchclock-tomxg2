package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries with category id and tag ids.")
    public List<EntryDto> index() {
        return entryService.findAll().stream().map(EntryMapper::toDto).toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry. Unknown category or tag ids return 404.")
    public EntryDto create(@NotNull @Valid EntryDto dto) {
        Entry created = entryService.createEntry(EntryMapper.toEntity(dto), dto.categoryId, dto.tagIds);
        return EntryMapper.toDto(created);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates the entry with the given id. Unknown ids return 404.")
    public EntryDto update(@PathParam("id") Long id, @NotNull @Valid EntryDto dto) {
        Entry updated = entryService.updateEntry(id, EntryMapper.toEntity(dto), dto.categoryId, dto.tagIds);
        return EntryMapper.toDto(updated);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes the entry with the given id.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }
}
