package CostManager.Model;

public class CostItem {
<<<<<<< HEAD
    private String description;
    private double sum;
    private Currency currency;
    private int id;
=======
>>>>>>> refs/remotes/origin/master

    private int id;
    private Category category;
    private String description;
    private double totalPrice;
    private Currency currency;

    /**
     * CostItem Parameterized Constructor that builds CostItem object.
     *
     @param id - Variable for holding the cost id from the database.
     @param category - Represents the category that the cost item belong to.
     @param description - Represents any info about the cost item.
     @param totalPrice  - The total price of the cost.
     @param currency - The currency that used during the purchase.
     *
     */
    public CostItem(Category category, String description, double totalPrice, Currency currency, int id) {
        this.id = -1;
        this.category = category;
        this.description = description;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public double getSum() {
//        return sum;
//    }
//
//    public void setSum(double sum) {
//        this.sum = sum;
//    }

    //public Currency getCurrency() {
    //   return currency;
    //}

    //public void setCurrency(Currency currency) {
    //this.currency = currency;
    //}
}
