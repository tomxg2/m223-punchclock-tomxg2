package ch.zli.m223.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  // n:m – Gegenseite von Entry.tags (Join-Tabelle wird von Entry verwaltet)
  @ManyToMany(mappedBy = "tags")
  private Set<Entry> entries = new LinkedHashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public Set<Entry> getEntries() { return entries; }
  public void setEntries(Set<Entry> entries) { this.entries = entries; }
}
