package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class TagService {
    @Inject
    TagRepository tagRepository;

    @Inject
    EntryRepository entryRepository;

    public List<Tag> findAll() {
        return tagRepository.listAll();
    }

    public Tag findById(Long id) {
        Tag tag = tagRepository.findById(id);
        if (tag == null) {
            throw new NotFoundException("Tag " + id + " nicht gefunden");
        }
        return tag;
    }

    @Transactional
    public Tag create(Tag tag) {
        tagRepository.persist(tag);
        return tag;
    }

    @Transactional
    public Tag update(Long id, Tag tag) {
        Tag existing = findById(id);
        existing.setTitle(tag.getTitle());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Tag tag = findById(id);
        // Entry verwaltet die Join-Tabelle entry_tag: Tag zuerst aus allen Entries entfernen
        List<Entry> entries = entryRepository
                .find("select e from Entry e join e.tags t where t.id = ?1", id)
                .list();
        for (Entry entry : entries) {
            entry.getTags().remove(tag);
        }
        tagRepository.delete(tag);
    }
}
