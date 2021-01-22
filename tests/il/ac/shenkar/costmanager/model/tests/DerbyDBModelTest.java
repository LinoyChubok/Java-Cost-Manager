package il.ac.shenkar.costmanager.model.tests;

import il.ac.shenkar.costmanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        List<Category> actual = db.getAllCategories();
        List<Category> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() throws CostManagerException {
        Category categoryFood = new Category(1, "Food");

        db.addCategory(categoryFood);
        List<Category> actual = db.getAllCategories();

        List<Category> expected = new ArrayList<>();
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
        List<CostItem> actual = db.getAllCostItems();

        List<CostItem> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void getAllCostItems() throws CostManagerException {
        Category category = new Category(1,"Food");
        CostItem item = new CostItem(1, Date.valueOf("2020-12-22"), new Category("Food"), "Pizza Slice", Currency.ILS, 27.6);

        db.addCategory(category);
        db.addCostItem(item);
        List<CostItem> actual = db.getAllCostItems();

        List<CostItem> expected = new ArrayList<>();
        expected.add(item);

        assertEquals(expected, actual);
    }

   @Test
   void getReportSummary() throws CostManagerException {

       List<CostItem> actual = db.getReportSummary(Date.valueOf("2020-12-22"),Date.valueOf("2020-12-24"));

       List<CostItem> expected = new ArrayList<>();


       assertEquals(expected, actual);
   }

    @Test
    void checkyaron() throws CostManagerException {
//        db.getPieChartSummary(Date.valueOf("2021-10-01"),Date.valueOf("2021-10-08"), Currency.ILS);



    }
}
