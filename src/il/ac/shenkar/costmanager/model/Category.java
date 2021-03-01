package il.ac.shenkar.costmanager.model;

import java.util.Objects;

public class Category {

    private int id;
    private String categoryName;

    /**
     * Category Parameterized Constructor that builds Category object from Database.
     *
     @param id              Variable for holding the category id from the database.
     @param categoryName    Represents the category name that the cost items will be belong to.
     *
     */
    public Category(int id, String categoryName) throws CostManagerException {
        setId(id);
        setCategoryName(categoryName);
    }

    /**
     * Category Parameterized Constructor that builds Category object without id.
     *
     @param categoryName    Represents the category name that the cost items will be belong to.
     *
     */
    public Category(String categoryName) throws CostManagerException {
        setId( - 1);
        setCategoryName(categoryName);
    }

    /**
     * Category Getters
     */
    public int getId() {
        return id;
    }
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Category Setters
     */
    public void setId(int id) {
        this.id = id;
    }
    public void setCategoryName(String categoryName) throws CostManagerException {
        if (categoryName == null || categoryName.length() == 0) {
            throw new CostManagerException("Category name cannot be empty!");
        }
        this.categoryName = categoryName;
    }

    /**
     * This method returns the string representation of the class.
     * @return String representation of Category object.
     *
     */
    @Override
    public String toString() {
        return "Category: {" + "id = " + id + ", category name = " + categoryName + '}';
    }

    /**
     * This is native method that returns the integer hash code value of the object.
     * @return int hash code value of the object.
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }

    /**
     * This method check if all values between two Category objects are equal.
     * @return bool If values are equals return true, else return false.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Category category = (Category) obj;

        return id == category.id && Objects.equals(categoryName, category.categoryName);
    }
}