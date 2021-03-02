package il.ac.shenkar.costmanager.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class CostItem {

    private int id;
    private Date date;
    private Category category;
    private String description;
    private Currency currency;
    private double totalPrice;

    /**
     * CostItem Parameterized Constructor that builds CostItem object (from Database data).
     *
     @param id          Variable for holding the cost id from the database.
     @param date        Represents the date of the purchase.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param currency    The currency that used during the purchase.
     @param totalPrice  The total price of the cost.
     *
     */
    public CostItem(int id, Date date, Category category, String description, Currency currency, double totalPrice) throws CostManagerException {
        setId(id);
        setDate(date);
        setCategory(category);
        setDescription(description);
        setCurrency(currency);
        setTotalPrice(totalPrice);
    }

    /**
     * CostItem Parameterized Constructor that builds CostItem object (from GUI Inputs).
     *
     @param id          Variable for holding the cost id from the database.
     @param date        Represents the date of the purchase.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param currency    The currency that used during the purchase.
     @param totalPrice  The total price of the cost.
     *
     */
    public CostItem(int id, String date, Category category, String description, String currency, String totalPrice) throws CostManagerException {
        setId(id);
        setDate(date);
        setCategory(category);
        setDescription(description);
        setCurrency(currency);
        setTotalPrice(totalPrice);
    }

    /**
     * CostItem Parameterized Constructor that builds CostItem object without id (from GUI Inputs).
     *
     @param date        Represents the date of the purchase.
     @param category    Represents the category that the cost item belong to.
     @param description Represents any info about the cost item.
     @param currency    The currency that used during the purchase.
     @param totalPrice  The total price of the cost.
     *
     */
    public CostItem(String date, Category category, String description, String currency, String totalPrice) throws CostManagerException {
        setId( - 1);
        setDate(date);
        setCategory(category);
        setDescription(description);
        setCurrency(currency);
        setTotalPrice(totalPrice);
    }

    /**
     * CostItem Getters
     *
     */
    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public Category getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public Currency getCurrency() {
        return currency;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * CostItem Setters
     *
     */
    public void setId(int id) {
        this.id = id;
    }
    public void setDate(String date) throws CostManagerException {
        if(date == null || date.length() == 0) {
            throw new CostManagerException("Date cannot be empty!");
        }
        this.date = validDate(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setDescription(String description) throws CostManagerException {
        if(description == null || description.length() == 0) {
            throw new CostManagerException("Description cannot be empty!");
        }
        this.description = description;
    }
    public void setCurrency(Currency currency) { this.currency = currency; }
    public void setCurrency(String currency) throws CostManagerException {
        switch (currency) {
            case "EUR" -> this.currency = Currency.EUR;
            case "USD" -> this.currency = Currency.USD;
            case "GBP" -> this.currency = Currency.GBP;
            case "ILS" -> this.currency = Currency.ILS;
            default -> throw new CostManagerException("Invalid currency!");
        }
    }
    public void setTotalPrice(double totalPrice) throws CostManagerException {
        if (totalPrice <= 0)
            throw new CostManagerException("Invalid price!");
        else
            this.totalPrice = totalPrice;
    }
    public void setTotalPrice(String totalPrice) throws CostManagerException {
        double totalPriceConverted = 0;
        try{
            totalPriceConverted = Double.parseDouble(totalPrice);
        }
        catch (NumberFormatException ex){
            throw new CostManagerException("Invalid price!");
        }

        if (totalPriceConverted <= 0) throw new CostManagerException("Invalid price!");
        else this.totalPrice = totalPriceConverted;
    }

    /**
     * Static functions
     *
     * validDate - Check the validation of date (if date is exist on the calendar).
     *
     */
    public static Date validDate(String date) throws CostManagerException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");

        try {
            sdf.parse(date);
            sdf.setLenient(false);
        } catch(ParseException e) {
            throw new CostManagerException("Invalid date!");
        }

        Date dateConverted;

        try{
           dateConverted = Date.valueOf(date);
        }
        catch (IllegalArgumentException ex)
        {
            throw new CostManagerException("Invalid date!");
        }

        return dateConverted;
    }

    /**
     * This method returns the string representation of the class.
     * @return String representation of CostItem object.
     *
     */
    @Override
    public String toString() {
        return "CostItem: {" + "id = " + id + ", date = " + date + ", category = " + category.getCategoryName() + ", description = " + description + ", currency = " + currency + ", totalPrice = " + totalPrice + '}';
    }

    /**
     * This is native method that returns the integer hash code value of the object.
     * @return int hash code value of the object.
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, date, category, description, currency, totalPrice);
    }

    /**
     * This method check if all values between two CostItem objects are equal.
     * @return bool If values are equals return true, else return false.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        CostItem costItem = (CostItem) obj;

        return id == costItem.id && Objects.equals(date, costItem.date) && Objects.equals(category, costItem.category) && Objects.equals(description, costItem.description) && currency == costItem.currency && Double.compare(totalPrice, costItem.totalPrice) == 0;
    }
}