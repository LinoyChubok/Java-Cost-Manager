package CostManager.Model.tests;

import CostManager.Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DerbyDBModelTest {

    private DerbyDBModel db;

    @BeforeEach
    void setUp() throws CostManagerException {
        db = new DerbyDBModel();
    }

    @AfterEach
    void tearDown() {
        db = null;
    }

    @Test
    void deleteCategory() throws CostManagerException {
        Category category = new Category(2, "Food");

        db.addCategory(category);
        db.deleteCategory(1);
        ArrayList<Category> actual = db.getAllCategories();

        ArrayList<Category> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() throws CostManagerException {
        Category categoryFood = new Category(1, "Food");

        db.addCategory(categoryFood);
        ArrayList<Category> actual = db.getAllCategories();

        ArrayList<Category> expected = new ArrayList<>();
        expected.add(categoryFood);

        assertEquals(expected, actual);
    }

    @Test
    void deleteCostItem() throws CostManagerException {
        Category category = new Category("Food");
        CostItem item = new CostItem(Date.valueOf("2020-12-22"), category, "Pizza Slice", Currency.ILS, 27.6);

        db.addCategory(category);
        db.addCostItem(item);
        db.deleteCostItem(1);
        ArrayList<CostItem> actual = db.getAllCostItems();

        ArrayList<CostItem> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void getAllCostItems() throws CostManagerException {
        Category category = new Category(1,"Food");
        CostItem item = new CostItem(1, Date.valueOf("2020-12-22"), new Category("Food"), "Pizza Slice", Currency.ILS, 27.6);

        db.addCategory(category);
        db.addCostItem(item);
        ArrayList<CostItem> actual = db.getAllCostItems();

        ArrayList<CostItem> expected = new ArrayList<>();
        expected.add(item);

        assertEquals(expected, actual);
    }

    //Between two dates
   @Test
   void testGetAllCostItems() throws CostManagerException {
       Category category = new Category(1,"Food");
       CostItem itemA = new CostItem(1, Date.valueOf("2020-12-22"), new Category( "Food"), "Pizza Slice", Currency.ILS, 27.6);
       CostItem itemB = new CostItem(2, Date.valueOf("2020-12-23"), new Category( "Food"), "Pizza Slice", Currency.ILS, 27.6);
       CostItem itemC = new CostItem(3, Date.valueOf("2020-12-24"), new Category( "Food"), "Pizza Slice", Currency.ILS, 27.6);
       CostItem itemD = new CostItem(4, Date.valueOf("2020-12-26"), new Category( "Food"), "Pizza Slice", Currency.ILS, 27.6);

       db.addCategory(category);
       db.addCostItem(itemA);
       db.addCostItem(itemB);
       db.addCostItem(itemC);
       db.addCostItem(itemD);
       ArrayList<CostItem> actual = db.getAllCostItemsReport(Date.valueOf("2020-12-22"),Date.valueOf("2020-12-24"));

       ArrayList<CostItem> expected = new ArrayList<>();
       expected.add(itemA);
       expected.add(itemB);
       expected.add(itemC);

       assertEquals(expected, actual);
   }
}
