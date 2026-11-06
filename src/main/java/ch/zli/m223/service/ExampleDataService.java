package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class ExampleDataService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    EntryRepository entryRepository;

    // Standardwert false, nur im Dev-Profil true (siehe application.properties)
    @ConfigProperty(name = "punchclock.example-data.enabled", defaultValue = "false")
    boolean enabled;

    void onStart(@Observes StartupEvent event) {
        if (enabled) {
            createExampleData();
        }
    }

    @Transactional
    public void createExampleData() {
        // Sobald eine Tabelle Daten enthält, wird nichts mehr angelegt
        if (categoryRepository.count() > 0 || tagRepository.count() > 0 || entryRepository.count() > 0) {
            return;
        }

        Category arbeit = category("Arbeit");
        Category schule = category("Schule");
        Category ueK = category("ÜK");

        Tag meeting = tag("Meeting");
        Tag programmieren = tag("Programmieren");
        Tag dokumentation = tag("Dokumentation");

        entry(LocalDateTime.of(2026, 11, 2, 8, 0), LocalDateTime.of(2026, 11, 2, 12, 0), arbeit, Set.of(programmieren));
        entry(LocalDateTime.of(2026, 11, 2, 13, 0), LocalDateTime.of(2026, 11, 2, 17, 0), arbeit, Set.of(meeting, dokumentation));
        entry(LocalDateTime.of(2026, 11, 3, 8, 0), LocalDateTime.of(2026, 11, 3, 16, 30), schule, Set.of());
        entry(LocalDateTime.of(2026, 11, 5, 8, 15), LocalDateTime.of(2026, 11, 5, 16, 45), ueK, Set.of(programmieren, dokumentation));
        entry(LocalDateTime.of(2026, 11, 6, 8, 15), LocalDateTime.of(2026, 11, 6, 12, 0), null, Set.of(meeting));
    }

    private Category category(String title) {
        Category category = new Category();
        category.setTitle(title);
        categoryRepository.persist(category);
        return category;
    }

    private Tag tag(String title) {
        Tag tag = new Tag();
        tag.setTitle(title);
        tagRepository.persist(tag);
        return tag;
    }

    private void entry(LocalDateTime checkIn, LocalDateTime checkOut, Category category, Set<Tag> tags) {
        Entry entry = new Entry();
        entry.setCheckIn(checkIn);
        entry.setCheckOut(checkOut);
        entry.setCategory(category);
        entry.getTags().addAll(tags);
        entryRepository.persist(entry);
    }
}
