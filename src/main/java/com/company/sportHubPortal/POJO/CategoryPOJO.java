package com.company.sportHubPortal.POJO;

public class CategoryPOJO {
    private Long id;
    private String name;
    private boolean hidden;
    private boolean isTeam;

    public CategoryPOJO() {
    }

    public CategoryPOJO(Long id, String name, boolean hidden, boolean isTeam) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.isTeam = isTeam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setIsTeam(boolean isTeam) {
        this.isTeam = isTeam;
    }
}
