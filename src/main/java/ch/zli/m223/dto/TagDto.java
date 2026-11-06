package ch.zli.m223.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class TagDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    public Long id;

    @NotBlank(message = "Titel ist ein Pflichtfeld")
    public String title;
}
