package ch.zli.m223.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Nur für die Ausgabe: Kategorie und Tags als verschachtelte Objekte (ohne Rückverweis auf Entries)
public class EntryDetailDto {
    public Long id;
    public LocalDateTime checkIn;
    public LocalDateTime checkOut;
    public CategoryDto category;              // null, wenn keine Kategorie
    public List<TagDto> tags = new ArrayList<>(); // leer, wenn keine Tags
}
