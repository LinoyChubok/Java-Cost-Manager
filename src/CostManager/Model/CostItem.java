package CostManager.Model;

public class CostItem {
    private String description;
    private double sum;
    private Currency currency;
    private int id;

    public CostItem(String description, double sum) {
        //this.description = description;
        //this.sum = sum;
        //this.currency = currency;
        setDescription(description);
        //setCurrency(currency);
        setSum(sum);
        //assigning id with a unique value
        //..
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    //public Currency getCurrency() {
    //   return currency;
    //}

    //public void setCurrency(Currency currency) {
    //this.currency = currency;
    //}
}
