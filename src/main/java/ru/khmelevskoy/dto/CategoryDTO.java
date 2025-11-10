package ru.khmelevskoy.dto;

import java.util.Objects;

public class CategoryDTO {

    private long id;

    private String categoryName;

    private String categoryGroup;

    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return id == that.id && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryGroup, that.categoryGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, categoryGroup);
    }
}