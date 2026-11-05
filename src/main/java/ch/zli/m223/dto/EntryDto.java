package ch.zli.m223.dto;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class EntryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    public Long id;

    @NotNull(message = "Check-in ist ein Pflichtfeld")
    public LocalDateTime checkIn;

    @NotNull(message = "Check-out ist ein Pflichtfeld")
    public LocalDateTime checkOut;

    @AssertTrue(message = "Check-in muss vor Check-out liegen")
    @JsonIgnore
    public boolean isCheckInBeforeCheckOut() {
        return checkIn == null || checkOut == null || checkIn.isBefore(checkOut);
    }
}