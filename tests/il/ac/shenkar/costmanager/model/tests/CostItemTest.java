package il.ac.shenkar.costmanager.model.tests;

import il.ac.shenkar.costmanager.model.Category;
import il.ac.shenkar.costmanager.model.CostItem;
import il.ac.shenkar.costmanager.model.CostManagerException;
import il.ac.shenkar.costmanager.model.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class CostItemTest {

    private Category category = null;
    private CostItem item = null;

    @BeforeEach
    void setUp() throws CostManagerException {
        category = new Category("Food");
        item = new CostItem("2020-12-22", category, "Pizza Slice", "ILS", "27.6");
    }

    @AfterEach
    void tearDown() {
        category = null;
        item = null;
    }

    @Test
    void getId() {
        int expected = -1;
        int actual = item.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getDate() {
        Date expected = Date.valueOf("2020-12-22");
        Date actual = item.getDate();
        assertEquals(expected, actual);
    }

    @Test
    void getCategory() throws CostManagerException {
        Category expected = new Category("Food");
        Category actual = item.getCategory();
        assertEquals(expected, actual);
    }

    @Test
    void getDescription() {
        String expected = "Pizza Slice";
        String actual = item.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrency() {
        Currency expected = Currency.ILS;
        Currency actual = item.getCurrency();
        assertEquals(expected, actual);
    }

    @Test
    void getTotalPrice() {
        double expected = 27.6;
        double actual = item.getTotalPrice();
        assertEquals(expected, actual);
    }

    @Test
    void setDate() throws CostManagerException {
        Date expected = Date.valueOf("2020-07-06");
        item.setDate("2020-07-06");
        Date actual = item.getDate();
        assertEquals(expected, actual);
    }

    @Test
    void setCategory() throws CostManagerException{
        Category expected = new Category("Cinema");
        item.setCategory(new Category("Cinema"));
        Category actual = item.getCategory();
        assertEquals(expected, actual);
    }

    @Test
    void setDescription() throws CostManagerException {
        String expected = "Nachos";
        item.setDescription("Nachos");
        String actual = item.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void setCurrency() throws CostManagerException {
        Currency expected = Currency.USD;
        item.setCurrency("USD");
        Currency actual = item.getCurrency();
        assertEquals(expected, actual);
    }

    @Test
    void setTotalPrice() throws CostManagerException {
        double expected = 15.90;
        item.setTotalPrice("15.90");
        double actual = item.getTotalPrice();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        String expected =  "CostItem: {" +
                "id = " + "-1" +
                ", date = " + "2020-12-22" +
                ", category = " + "Food" +
                ", description = " + "Pizza Slice" +
                ", currency = " + "ILS" +
                ", totalPrice = " + "27.6" + '}';
        String actual = item.toString();
        assertEquals(expected, actual);
    }
}