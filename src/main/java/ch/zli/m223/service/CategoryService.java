package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Category;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;

@ApplicationScoped
public class CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    @Inject
    EntryRepository entryRepository;

    public List<Category> findAll() {
        return categoryRepository.listAll();
    }

    public Category findById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NotFoundException("Category " + id + " nicht gefunden");
        }
        return category;
    }

    @Transactional
    public Category create(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    @Transactional
    public Category update(Long id, Category category) {
        Category existing = findById(id);
        existing.setTitle(category.getTitle());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        // Zuerst die Verknüpfung in den Entries entfernen, sonst verletzt das Löschen den Fremdschlüssel
        entryRepository.update("category = null where category.id = ?1", id);
        categoryRepository.deleteById(id);
    }
}
