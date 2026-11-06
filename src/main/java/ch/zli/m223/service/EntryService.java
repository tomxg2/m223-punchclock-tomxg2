package ch.zli.m223.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    EntryRepository entryRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    TagRepository tagRepository;

    public List<Entry> findAll() {
        return entryRepository.listAll();
    }

    public Entry findById(Long id) {
        Entry entry = entryRepository.findById(id);
        if (entry == null) {
            throw new NotFoundException("Entry " + id + " nicht gefunden");
        }
        return entry;
    }

    @Transactional
    public Entry createEntry(Entry entry, Long categoryId, List<Long> tagIds) {
        entry.setCategory(loadCategory(categoryId));
        entry.setTags(loadTags(tagIds));
        entryRepository.persist(entry);
        return entry;
    }

    @Transactional
    public Entry updateEntry(Long id, Entry entry, Long categoryId, List<Long> tagIds) {
        Entry existing = findById(id);
        existing.setCheckIn(entry.getCheckIn());
        existing.setCheckOut(entry.getCheckOut());
        existing.setCategory(loadCategory(categoryId));
        existing.setTags(loadTags(tagIds));
        return existing;
    }

    @Transactional
    public void deleteEntry(Long id) {
        boolean deleted = entryRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Entry " + id + " nicht gefunden");
        }
    }

    // Unbekannte IDs -> 404 Not Found
    private Category loadCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new NotFoundException("Category " + categoryId + " nicht gefunden");
        }
        return category;
    }

    private Set<Tag> loadTags(List<Long> tagIds) {
        Set<Tag> tags = new LinkedHashSet<>();
        if (tagIds == null) {
            return tags;
        }
        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId);
            if (tag == null) {
                throw new NotFoundException("Tag " + tagId + " nicht gefunden");
            }
            tags.add(tag);
        }
        return tags;
    }
}
