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

import ch.zli.m223.dto.CategoryDto;
import ch.zli.m223.service.CategoryService;

@Path("/categories")
@Tag(name = "Categorys", description = "Handling of categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    @Operation(summary = "Index all categories.")
    public List<CategoryDto> index() {
        return categoryService.findAll().stream().map(EntryMapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one category by id.")
    public CategoryDto get(@PathParam("id") Long id) {
        return EntryMapper.toDto(categoryService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new category.")
    public CategoryDto create(@NotNull @Valid CategoryDto dto) {
        return EntryMapper.toDto(categoryService.create(EntryMapper.toEntity(dto)));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a category.")
    public CategoryDto update(@PathParam("id") Long id, @NotNull @Valid CategoryDto dto) {
        return EntryMapper.toDto(categoryService.update(id, EntryMapper.toEntity(dto)));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a category.")
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }
}
