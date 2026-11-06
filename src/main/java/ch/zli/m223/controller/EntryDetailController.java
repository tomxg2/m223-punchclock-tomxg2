package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.EntryDetailDto;
import ch.zli.m223.service.EntryService;

@Path("/entry-details")
@Tag(name = "Entry Details", description = "Entries with nested category and tags")
@Produces(MediaType.APPLICATION_JSON)
public class EntryDetailController {

    @Inject
    EntryService entryService;

    @GET
    @Operation(summary = "Index all entry details.", description = "Returns all entries with nested category and tags.")
    public List<EntryDetailDto> index() {
        return entryService.findAll().stream().map(EntryMapper::toDetailDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one entry detail.", description = "Returns one entry with nested category and tags or 404.")
    public EntryDetailDto get(@PathParam("id") Long id) {
        return EntryMapper.toDetailDto(entryService.findById(id));
    }
}
