package CostManager.Model;

import java.sql.Date;

public class TestModel {
    public static void main(String args[])
    {
        System.out.println("Main Method Invoked");

        try
        {
            Category categoryObj = new Category(1, "Food");
            CostItem costItemObj = new CostItem(1, Date.valueOf("2020-12-22"), categoryObj, "Pizza Slice", Currency.ILS, 27.6);
            System.out.println(costItemObj);

            DerbyDBModel db = new DerbyDBModel();
            db.closeConnection();
        }
        catch (CostManagerException e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("Finish Testing");
        }

    }
}
