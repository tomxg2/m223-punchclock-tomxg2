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

import ch.zli.m223.dto.TagDto;
import ch.zli.m223.service.TagService;

@Path("/tags")
@Tag(name = "Tags", description = "Handling of tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagController {

    @Inject
    TagService tagService;

    @GET
    @Operation(summary = "Index all tags.")
    public List<TagDto> index() {
        return tagService.findAll().stream().map(EntryMapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one tag by id.")
    public TagDto get(@PathParam("id") Long id) {
        return EntryMapper.toDto(tagService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new tag.")
    public TagDto create(@NotNull @Valid TagDto dto) {
        return EntryMapper.toDto(tagService.create(EntryMapper.toEntity(dto)));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a tag.")
    public TagDto update(@PathParam("id") Long id, @NotNull @Valid TagDto dto) {
        return EntryMapper.toDto(tagService.update(id, EntryMapper.toEntity(dto)));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a tag.")
    public void delete(@PathParam("id") Long id) {
        tagService.delete(id);
    }
}
