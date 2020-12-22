package CostManager.Model;

import java.sql.Date;

public class CostItem {

    private int id;
    private Date date;
    private Category category;
    private String description;
    private Currency currency;
    private double totalPrice;

    /**
     * CostItem Parameterized Constructor that builds CostItem object from Database.
     *
     @param id          Variable for holding the cost id from the database.
     @param date        Represents the date of the purchase.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param totalPrice  The total price of the cost.
     @param currency    The currency that used during the purchase.
     *
     */
    public CostItem(int id, Date date, Category category, String description, Currency currency, double totalPrice) {
        this.id = id;
        setDate(date);
        setCategory(category);
        setDescription(description);
        setCurrency(currency);
        setTotalPrice(totalPrice);
    }

    /**
     * CostItem Parameterized Constructor that builds CostItem object without id.
     *
     @param date        Represents the date of the purchase.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param totalPrice  The total price of the cost.
     @param currency    The currency that used during the purchase.
     *
     */
    public CostItem(Date date, Category category, String description, Currency currency, double totalPrice) {
        this.id = -1;
        setDate(date);
        setCategory(category);
        setDescription(description);
        setCurrency(currency);
        setTotalPrice(totalPrice);
    }

    /**
     * CostItem Getters
     */
    public double getId() { return id; }
    public Date getDate() { return date; }
    public Category getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public Currency getCurrency() { return currency; }
    public double getTotalPrice() { return totalPrice; }

    /**
     * CostItem Setters
     * !!!!! need to add proper validation and move to the constructor
     */
    public void setDate(Date date) { this.date = date; }
    public void setCategory(Category category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
