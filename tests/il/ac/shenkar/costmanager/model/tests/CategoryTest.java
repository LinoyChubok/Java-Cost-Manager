package il.ac.shenkar.costmanager.model.tests;

import il.ac.shenkar.costmanager.model.Category;
import il.ac.shenkar.costmanager.model.CostManagerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() throws CostManagerException  { category = new Category("Food"); }

    @AfterEach
    void tearDown() { category = null; }

    @Test
    void getId() {
        int expected = -1;
        int actual = category.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getCategoryName() {
        String expected = "Food";
        String actual = category.getCategoryName();
        assertEquals(expected, actual);
    }

    @Test
    void setCategoryName() throws CostManagerException{
        String expected = "Electric";
        category.setCategoryName("Electric");
        String actual = category.getCategoryName();
        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        String expected =  "Category: {" +
                "id = " + "-1" +
                ", category name = " + "Food" + '}';
        String actual = category.toString();
        assertEquals(expected, actual);
    }
}