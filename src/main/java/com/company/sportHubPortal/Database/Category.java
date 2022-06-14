package com.company.sportHubPortal.Database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "category")
public class Category {
  @Id
  private Long id;
  private String name;
  private Long parentId;
  private boolean hidden;

  public Category(@NonNull Long id, @NonNull String name, @NonNull Long parentId,
                  @NonNull boolean hidden) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.hidden = hidden;
  }

  public Category() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
}
