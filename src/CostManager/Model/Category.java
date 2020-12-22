package CostManager.Model;

public class Category {

    private int id;
    private String categoryName;

    /**
     * Category Parameterized Constructor that builds Category object from Database.
     *
     @param id          Variable for holding the category id from the database.
     @param categoryName    Represents the category that the cost item belong to.
     *
     */
    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    /**
     * Category Parameterized Constructor that builds Category object without id.
     *
     @param categoryName    Represents the category that the cost item belong to.
     *
     */
    public Category(String categoryName) {
        this.id = -1;
        this.categoryName = categoryName;
    }

}
