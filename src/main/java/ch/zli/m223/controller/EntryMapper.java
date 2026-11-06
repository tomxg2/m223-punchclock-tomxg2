package ch.zli.m223.controller;

import ch.zli.m223.dto.CategoryDto;
import ch.zli.m223.dto.EntryDetailDto;
import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.dto.TagDto;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;

// Mapping zwischen DTOs und Entities (wird von den Controllern verwendet)
public final class EntryMapper {

    private EntryMapper() {
    }

    public static Entry toEntity(EntryDto dto) {
        Entry entry = new Entry();
        entry.setCheckIn(dto.checkIn);
        entry.setCheckOut(dto.checkOut);
        return entry;
    }

    public static EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();
        dto.id = entry.getId();
        dto.checkIn = entry.getCheckIn();
        dto.checkOut = entry.getCheckOut();
        dto.categoryId = entry.getCategory() == null ? null : entry.getCategory().getId();
        dto.tagIds = entry.getTags().stream().map(Tag::getId).toList();
        return dto;
    }

    public static EntryDetailDto toDetailDto(Entry entry) {
        EntryDetailDto dto = new EntryDetailDto();
        dto.id = entry.getId();
        dto.checkIn = entry.getCheckIn();
        dto.checkOut = entry.getCheckOut();
        dto.category = entry.getCategory() == null ? null : toDto(entry.getCategory());
        dto.tags = entry.getTags().stream().map(EntryMapper::toDto).toList();
        return dto;
    }

    public static Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setTitle(dto.title);
        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.title = category.getTitle();
        return dto;
    }

    public static Tag toEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setTitle(dto.title);
        return tag;
    }

    public static TagDto toDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.id = tag.getId();
        dto.title = tag.getTitle();
        return dto;
    }
}
