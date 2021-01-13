package CostManager.Model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class CostItem {

    private final int id;
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
     @param currency    The currency that used during the purchase.
     @param totalPrice  The total price of the cost.
     *
     */
    public CostItem(int id, Date date, Category category, String description, Currency currency, double totalPrice) throws CostManagerException{
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
     @param currency    The currency that used during the purchase.
     @param totalPrice  The total price of the cost.
     *
     */
    public CostItem(Date date, Category category, String description, Currency currency, double totalPrice) throws CostManagerException{
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
    public int getId() { return id; }
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
     */
    public void setDate(Date date) throws CostManagerException {
        this.date = validDate(date);
    }
    public void setCategory(Category category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    public void setTotalPrice(double totalPrice) throws CostManagerException {
        if (totalPrice <= 0)
            throw new CostManagerException("Invalid price!");
        else
            this.totalPrice = totalPrice;
    }

    /**
     * Static functions
     */
    public static Date validDate(Date date) throws CostManagerException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");

        try {
            sdf.parse(date.toString());
            sdf.setLenient(false);
        } catch (ParseException e) {
            throw new CostManagerException("Invalid date!");
        }
        return date;
    }


    /**
     * toString Method
     *
     * @return          String representation of CostItem object
     *
     */
    @Override
    public String toString() {
        return "CostItem: {" +
                "id = " + id +
                ", date = " + date +
                ", category = " + category +
                ", description = " + description +
                ", currency = " + currency +
                ", totalPrice = " + totalPrice + '}';
    }

    /**
     * equals Method
     *
     * @return          Check if the CostItem object equals to another object
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        CostItem costItem = (CostItem) obj;

        return id == costItem.id &&
                Objects.equals(date, costItem.date) &&
                Objects.equals(category, costItem.category) &&
                Objects.equals(description, costItem.description) &&
                currency == costItem.currency &&
                Double.compare(totalPrice, costItem.totalPrice) == 0;
    }
}