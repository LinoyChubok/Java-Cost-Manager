package CostManager.Model;

public class CostItem {

    private int id;
    private Category category;
    private String description;
    private double totalPrice;
    private Currency currency;

    /**
     * CostItem Parameterized Constructor that builds CostItem object from Database.
     *
     @param id          Variable for holding the cost id from the database.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param totalPrice  The total price of the cost.
     @param currency    The currency that used during the purchase.
     *
     */
    public CostItem(int id, Category category, String description, double totalPrice, Currency currency) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    /**
     * CostItem Parameterized Constructor that builds CostItem object without id.
     *
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param totalPrice  The total price of the cost.
     @param currency    The currency that used during the purchase.
     *
     */
    public CostItem(Category category, String description, double totalPrice, Currency currency) {
        this.id = -1;
        this.category = category;
        this.description = description;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    /**
     * CostItem Getters
     */
    public double getId() { return id; }
    public Category getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public double getTotalPrice() { return totalPrice; }
    public Currency getCurrency() { return currency; }

    /**
     * CostItem Setters
     * !!!!! need to add proper validation and move to the constructor
     */
    public void setCategory(Category category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setCurrency(Currency currency) { this.currency = currency; }
}
