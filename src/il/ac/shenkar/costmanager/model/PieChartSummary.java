package il.ac.shenkar.costmanager.model;

import java.util.Objects;

public class PieChartSummary {

    private Category category;
    private double totalPrice;

    /**
     * PieChartSummary Parameterized Constructor that builds PieChartSummary object
     * this object will be used as data for the pie chart view.
     *
     @param category    Represents the category of the total costs.
     @param totalPrice  Represents The total price of the category.
     *
     */

    public PieChartSummary(Category category, double totalPrice) {
        setCategory(category);
        setTotalPrice(totalPrice);
    }

    /**
     * PieChartSummary Getters
     *
     */
    public Category getCategory() {
        return category;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * PieChartSummary Setters
     *
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method returns the string representation of the class.
     * @return String representation of PieChartSummary object.
     *
     */
    @Override
    public String toString() {
        return "PieChartSummary: {" +
                "category = " + category.getCategoryName() +
                ", totalPrice = " + totalPrice + '}';
    }

    /**
     * This is native method that returns the integer hash code value of the object.
     * @return int hash code value of the object.
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(category, totalPrice);
    }

    /**
     * This method check if all values between two PieChartSummary objects are equal.
     * @return bool If values are equals return true, else return false.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        PieChartSummary pieChartSummary = (PieChartSummary) obj;

        return Objects.equals(category, pieChartSummary.category) &&
                Double.compare(pieChartSummary.totalPrice, totalPrice) == 0;
    }
}
