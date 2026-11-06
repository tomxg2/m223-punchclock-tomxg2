package ch.zli.m223.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.*;

@Entity
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime checkIn;

  @Column(nullable = false)
  private LocalDateTime checkOut;

  // n:1 – viele Entries gehören zu (höchstens) einer Category
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  // n:m – Entries <-> Tags über die Join-Tabelle entry_tag
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "entry_tag",
      joinColumns = @JoinColumn(name = "entry_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new LinkedHashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public LocalDateTime getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

  public LocalDateTime getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }

  public Category getCategory() { return category; }
  public void setCategory(Category category) { this.category = category; }

  public Set<Tag> getTags() { return tags; }
  public void setTags(Set<Tag> tags) { this.tags = tags; }
}
