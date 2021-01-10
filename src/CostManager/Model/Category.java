package CostManager.Model;

import java.util.Objects;

public class Category {

    private final int id;
    private String categoryName;

    /**
     * Category Parameterized Constructor that builds Category object from Database.
     *
     @param id              Variable for holding the category id from the database.
     @param categoryName    Represents the category name that the cost items will be belong to.
     *
     */
    public Category(int id, String categoryName) {
        this.id = id;
        setCategoryName(categoryName);
    }

    /**
     * Category Parameterized Constructor that builds Category object without id.
     *
     @param categoryName    Represents the category name that the cost items will be belong to.
     *
     */
    public Category(String categoryName) {
        this.id = -1;
        setCategoryName(categoryName);
    }

    /**
     * Category Getters
     */
    public int getId() { return id; }
    public String getCategoryName() { return categoryName; }

    /**
     * Category Setters
     */
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    /**
     * toString Method
     *
     * @return          String representation of Category object
     *
     */
    @Override
    public String toString() {
        return "Category: {" +
                "id = " + id +
                ", category name = " + categoryName + '}';
    }

    /**
     * equals Method
     *
     * @return          Check if the Category object equals to another object
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Category category = (Category) obj;

        return id == category.id && Objects.equals(categoryName, category.categoryName);
    }
}
