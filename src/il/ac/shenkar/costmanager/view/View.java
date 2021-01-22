package il.ac.shenkar.costmanager.view;

import il.ac.shenkar.costmanager.model.Category;
import il.ac.shenkar.costmanager.model.CostItem;
import il.ac.shenkar.costmanager.model.CostManagerException;
import il.ac.shenkar.costmanager.model.Currency;
import il.ac.shenkar.costmanager.viewmodel.IViewModel;

import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

import java.util.List;
import java.util.Map;

import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    /**
     * View class for implementing the IView interface.
     *
     */
    public View() {
        SwingUtilities.invokeLater(() -> {
            View.this.ui = new ApplicationUI();
            View.this.ui.start();
        });
    }

    /**
     * Setting the view model.
     * @param vm Variable that holding the view model.
     */
    @Override
    public void setViewModel(IViewModel vm) { this.vm = vm; }

    /**
     * Handle the display of any message at our view.
     * @param text The text message, string representation.
     */
    @Override
    public void showMessage(String text) { ui.showMessage(text); }

    /**
     * Handle the display of showing categories.
     * @param categories List of categories.
     */
    @Override
    public void showCategories(List<Category> categories) { ui.showCategories(categories); }

    /**
     * Handle the display of showing cost items.
     *
     */
    @Override
    public void showCostItems(List<CostItem> items) { ui.showCostItems(items); }

    /**
     * Handle the display of showing the report of cost items.
     * @param items List of cost items.
     */
    @Override
    public void showReportSummary(List<CostItem> items) { ui.showReportSummary(items); }

    /**
     * Handle the display of showing the pie chart.
     *
     */
    @Override
    public void showPieChartSummary(Map<Category, Double> pieChartSummary) {
        ui.showPieChartSummary(pieChartSummary);
    }

    /**
     * Inner class that implements the functionality of our UI that provided by the view class.
     *
     */
    public class ApplicationUI {
        // Frame component (for each page)
        private JFrame frame;
        private JPanel panel;
        private final MainPanel mainPanel;
        private final CostPanel costPanel;
        private final CategoryPanel categoryPanel;
        private final ReportsPanel reportsPanel;
        private final PieChartPanel pieChartPanel;

        // Constructor, to initialize the components
        public ApplicationUI() {
            // Create instances of panels
            mainPanel = new MainPanel();
            costPanel = new CostPanel();
            categoryPanel = new CategoryPanel();
            reportsPanel = new ReportsPanel();
            pieChartPanel = new PieChartPanel();

            // Create instance of JFrame with name "frame"
            frame = new JFrame("CostManager");

            // Set frame props
            frame.setLayout(new BorderLayout());
            frame.setSize(800,750);
            frame.setResizable(true);

            // On closing make sure db closed
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame = null;
                    vm.shutdownDB();
                    System.exit(0);
                }
            });
        }

        public class MainPanel extends JPanel {
            // Components of the MainPanel
            private final JLabel title;
            private final JLabel image;
            private final JPanel headerPanel;
            private final JPanel btnsPanel;

            private final JButton CostBtn;
            private final JButton CategoryBtn;
            private final JButton ReportsBtn;
            private final JButton PieChartBtn;

            // Constructor, to initialize the components
            public MainPanel() {
                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of MainPanel
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                // Create header panel
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));

                // Create btnsPanel as GridLayout
                btnsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
                btnsPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));

                // Create buttons for the main panel (each of them direct to another panel)
                CostBtn = new JButton("Costs");
                CategoryBtn = new JButton("Categories");
                ReportsBtn = new JButton("Reports");
                PieChartBtn = new JButton("Pie Chart Diagram");

                // Set font size and remove focus from buttons
                CostBtn.setFont(new Font("Arial", Font.BOLD, 17));
                CostBtn.setFocusable(false);
                CategoryBtn.setFont(new Font("Arial", Font.BOLD, 17));
                CategoryBtn.setFocusable(false);
                ReportsBtn.setFont(new Font("Arial", Font.BOLD, 17));
                ReportsBtn.setFocusable(false);
                PieChartBtn.setFont(new Font("Arial", Font.BOLD, 17));
                PieChartBtn.setFocusable(false);

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);
                btnsPanel.add(CostBtn);
                btnsPanel.add(CategoryBtn);
                btnsPanel.add(ReportsBtn);
                btnsPanel.add(PieChartBtn);

                // Add panels to MainPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(btnsPanel, BorderLayout.CENTER);

                // Handling cost button click
                CostBtn.addActionListener(e -> {
                    ApplicationUI.this.changeScreen(ApplicationUI.this.costPanel);
                    ApplicationUI.this.costPanel.clearInputs();
                    View.this.vm.getAllCategories();
                    View.this.vm.getAllCostItems();
                });

                // Handling category button click
                CategoryBtn.addActionListener(e -> {
                    ApplicationUI.this.changeScreen(ApplicationUI.this.categoryPanel);
                    ApplicationUI.this.categoryPanel.clearInputs();
                    View.this.vm.getAllCategories();
                });

                // Handling reports button click
                ReportsBtn.addActionListener(e -> {
                    ApplicationUI.this.changeScreen(ApplicationUI.this.reportsPanel);
                    ApplicationUI.this.reportsPanel.clearInputs();
                });

                // Handling pie chart button click
                PieChartBtn.addActionListener(e -> {
                    ApplicationUI.this.changeScreen(ApplicationUI.this.pieChartPanel);
                    ApplicationUI.this.pieChartPanel.clearInputs();
                });

            }
        }
        public class CostPanel extends JPanel {
            // Components of the CostPanel
            private final JPanel headerPanel;
            private final JPanel centerPanel;
            private final JPanel southPanel;

            private final JPanel costFormPanel;
            private final JPanel tablePanel;
            private final JPanel btnPanel;

            private DefaultTableModel tableModel;
            private JTable table;
            private JScrollPane scroll;

            private final JLabel image;
            private final JLabel title;
            private final JLabel idLabel;
            private final JLabel dateLabel;
            private final JLabel categoryLabel;
            private final JLabel descriptionLabel;
            private final JLabel currencyLabel;
            private final JLabel totalPriceLabel;
            private final JLabel messageLabel;

            private final JButton addBtn;
            private final JButton updateBtn;
            private final JButton deleteBtn;
            private final JButton backBtn;

            private JComboBox categoryCB;
            private JComboBox currencyCB;

            private TextField dateTF;
            private TextField idTF;
            private TextField descriptionTF;
            private TextField totalPriceTF;
            private TextField messageTF;

            // Constructor, to initialize the components
            public CostPanel() {

                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of CostPanel
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                // Set the centerPanel as BorderLayout
                centerPanel = new JPanel(new BorderLayout());

                // Create costFormPanel as GridLayout
                costFormPanel = new JPanel(new GridLayout(6,2,10,10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the tablePanel as BorderLayout
                tablePanel = new JPanel(new BorderLayout());

                // Create table
                tableModel = new DefaultTableModel();
                table = new JTable(tableModel);

                // Set table properties
                table.getTableHeader().setReorderingAllowed(false);
                table.setBackground(JBColor.WHITE);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                table.setFocusable(false);

                // Clicking the row will move the row data into the text fields and to the combo's
                table.getSelectionModel().addListSelectionListener(event -> {
                    if (table.getSelectedRow() > -1) {
                        idTF.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                        dateTF.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                        categoryCB.getModel().setSelectedItem(table.getValueAt(table.getSelectedRow(), 2).toString());
                        descriptionTF.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                        currencyCB.getModel().setSelectedItem(table.getValueAt(table.getSelectedRow(), 4).toString());
                        totalPriceTF.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
                    }
                });

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                // Create southPanel
                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);

                // Add components for southPanel
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                // Add components for Date
                dateLabel = new JLabel("Date (YYYY-MM-DD)");
                dateTF = new TextField();

                // Add components for ID
                idLabel = new JLabel("ID");
                idTF = new TextField();
                idTF.setEnabled(false);

                // Add components for Category
                categoryLabel = new JLabel("Category");
                categoryCB = new JComboBox();
                categoryCB.setLightWeightPopupEnabled(false);

                // Add components for Description
                descriptionLabel = new JLabel("Description");
                descriptionTF = new TextField();

                // Add components for Currency
                currencyLabel = new JLabel("Currency");
                currencyCB = new JComboBox();

                // Add items to currencyCB
                currencyCB.addItem("ILS");
                currencyCB.addItem("USD");
                currencyCB.addItem("EURO");
                currencyCB.addItem("GPB");

                // Set light weight to currencyCB (to make sure that items not hidden)
                currencyCB.setLightWeightPopupEnabled(false);
                totalPriceLabel = new JLabel("Price");
                totalPriceTF = new TextField();

                // Add buttons for CRUD operations
                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                // Add each component to his specific panel
                costFormPanel.add(idLabel);
                costFormPanel.add(idTF);
                costFormPanel.add(dateLabel);
                costFormPanel.add(dateTF);
                costFormPanel.add(categoryLabel);
                costFormPanel.add(categoryCB);
                costFormPanel.add(descriptionLabel);
                costFormPanel.add(descriptionTF);
                costFormPanel.add(currencyLabel);
                costFormPanel.add(currencyCB);
                costFormPanel.add(totalPriceLabel);
                costFormPanel.add(totalPriceTF);

                // Add scroll bar to table panel
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPanel include table

                // Add buttons for crud operations
                btnPanel.add(addBtn);
                btnPanel.add(updateBtn);
                btnPanel.add(deleteBtn);

                // Add all panels to center panel (responsive)
                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(tablePanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                // Add components to south panel
                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to CostPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

                // Handle add button click
                addBtn.addActionListener(e -> {
                    try {
                        String description = descriptionTF.getText();
                        if(description == null || description.length() == 0) {
                            throw new CostManagerException("description cannot be empty");
                        }

                        String categoryName = categoryCB.getSelectedItem().toString();
                        if(categoryName == null || categoryName.length() == 0) {
                            throw new CostManagerException("category cannot be empty");
                        }

                        String date = dateTF.getText();
                        if(date == null || date.length() == 0) {
                            throw new CostManagerException("date cannot be empty");
                        }

                        double totalPrice = Double.parseDouble(totalPriceTF.getText());

                        String currencyStr = currencyCB.getSelectedItem().toString();
                        Currency currency = null;
                        switch (currencyStr) {
                            case "EURO":
                                currency = Currency.EURO;
                                break;
                            case "USD":
                                currency = Currency.USD;
                                break;
                            case "GBP":
                                currency = Currency.GBP;
                                break;
                            case "ILS":
                                currency = Currency.ILS;
                                break;
                            default:
                                currency = Currency.ILS;
                        }

                        Category category = new Category(categoryName);
                        CostItem item = new CostItem(Date.valueOf(date), category, description, currency, totalPrice);

                        View.this.vm.addCostItem(item);

                    } catch (NumberFormatException ex) {
                        View.this.showMessage("Problem with entered total price " + ex.getMessage());
                    } catch(CostManagerException | IllegalArgumentException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });

                // Handle update button click
                updateBtn.addActionListener(e -> {
                    try {
                        int id = Integer.parseInt(idTF.getText());

                        String description = descriptionTF.getText();
                        if(description == null || description.length() == 0) {
                            throw new CostManagerException("description cannot be empty");
                        }

                        String categoryName = categoryCB.getSelectedItem().toString();
                        if(categoryName == null || categoryName.length() == 0) {
                            throw new CostManagerException("category cannot be empty");
                        }

                        String date = dateTF.getText();
                        if(date == null || date.length() == 0) {
                            throw new CostManagerException("date cannot be empty");
                        }

                        double totalPrice = Double.parseDouble(totalPriceTF.getText());

                        String currencyStr = currencyCB.getSelectedItem().toString();
                        Currency currency = null;
                        switch (currencyStr) {
                            case "EURO":
                                currency = Currency.EURO;
                                break;
                            case "USD":
                                currency = Currency.USD;
                                break;
                            case "GBP":
                                currency = Currency.GBP;
                                break;
                            case "ILS":
                                currency = Currency.ILS;
                                break;
                            default:
                                currency = Currency.ILS;
                        }

                        Category category = new Category(categoryName);
                        CostItem item = new CostItem(id, Date.valueOf(date), category, description, currency, totalPrice);

                        View.this.vm.updateCostItem(item);

                    } catch (NumberFormatException ex) {
                        View.this.showMessage("Problem with entered total price " + ex.getMessage());
                    } catch(CostManagerException | IllegalArgumentException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });

                // Handle delete button click
                deleteBtn.addActionListener(e -> {
                    try{
                        int id = Integer.parseInt(idTF.getText());
                        View.this.vm.deleteCostItem(id);
                    } catch (NumberFormatException ex){
                        View.this.showMessage("Problem with entered total price " + ex.getMessage());
                    }
                });

            }

            // This function will clear the whole inputs inside the panel
            public void clearInputs() {
                categoryCB.setSelectedIndex(-1);
                currencyCB.setSelectedIndex(-1);
                idTF.setText("");
                dateTF.setText("");
                totalPriceTF.setText("");
                descriptionTF.setText("");
                messageTF.setText("");
            }

            // This function will handle the showing of the messages in the buttom of the panel
            public void showMessage(String text) {
                messageTF.setText(text);
            }

            // This function will handle the showing of the cost items inside the table
            public void showCostItems(List<CostItem> items) {
                // Clear Table
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);

                // Clear inputs
                clearInputs();

                // Add table columns
                tableModel.addColumn("ID");
                tableModel.addColumn("DATE");
                tableModel.addColumn("CATEGORY");
                tableModel.addColumn("DESCRIPTION");
                tableModel.addColumn("CURRENCY");
                tableModel.addColumn("PRICE");

                // Add table rows
                for (CostItem item : items)
                    tableModel.addRow(new Object[]{item.getId(), item.getDate(),item.getCategory().getCategoryName(), item.getDescription(), item.getCurrency(), item.getTotalPrice()});
            }

            // This function will handle the showing of the categories inside the combos
            public void showCategories(List<Category> categories) {
                // Clear Categories Combo box
                categoryCB.removeAllItems();

                // Add Categories
                for (Category category : categories)
                    categoryCB.addItem(category.getCategoryName());
            }
        }
        public class CategoryPanel extends JPanel {
            // Components of the CategoryPanel
            private final JPanel headerPanel;
            private final JPanel centerPanel;
            private final JPanel southPanel;

            private final JPanel costFormPanel;
            private final JPanel tablePanel;
            private final JPanel btnPanel;

            private DefaultTableModel tableModel;
            private JTable table;
            private JScrollPane scroll;

            private final JLabel image;
            private final JLabel title;
            private final JLabel idLabel;
            private final JLabel categoryLabel;
            private final JLabel messageLabel;

            private final JButton addBtn;
            private final JButton updateBtn;
            private final JButton deleteBtn;
            private final JButton backBtn;

            private TextField idTF;
            private TextField categoryTF;
            private TextField messageTF;

            // Constructor, to initialize the components
            public CategoryPanel() {
                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of CategoryPanel
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                // Set the centerPanel as BorderLayout
                centerPanel = new JPanel(new BorderLayout());

                // Create costFormPanel as GridLayout
                costFormPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the tablePanel as BorderLayout
                tablePanel = new JPanel(new BorderLayout());
                tablePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

                // Create table
                tableModel = new DefaultTableModel();
                table = new JTable(tableModel);

                // Set table properties
                table.getTableHeader().setReorderingAllowed(false);
                table.setBackground(JBColor.WHITE);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPanel include table
                table.setFocusable(false);

                // Clicking the remove will move the data into the text fields and combos
                table.getSelectionModel().addListSelectionListener(event -> {
                    if (table.getSelectedRow() > -1) {
                        idTF.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                        categoryTF.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    }
                });

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                // Create southPanel
                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);

                // Create components to south panel
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                // Create components for ID
                idLabel = new JLabel("ID");
                idTF = new TextField();
                idTF.setEnabled(false);

                // Create components for Category Name
                categoryLabel = new JLabel("Category Name");
                categoryTF = new TextField();

                // init crud buttons
                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                // Add each component to his specific panel
                costFormPanel.add(idLabel);
                costFormPanel.add(idTF);
                costFormPanel.add(categoryLabel);
                costFormPanel.add(categoryTF);

                // Add crud buttons to the panel
                btnPanel.add(addBtn);
                btnPanel.add(updateBtn);
                btnPanel.add(deleteBtn);

                // Add panels to center panel (responsive)
                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(tablePanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                // Add each component to his specific panel
                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to CategoryPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

                // Handle add button click
                addBtn.addActionListener(e -> {
                    try {
                        String categoryName = categoryTF.getText();
                        if(categoryName == null || categoryName.length() == 0) {
                            throw new CostManagerException("categoryName cannot be empty");
                        }
                        
                        Category category = new Category(categoryName);

                        View.this.vm.addCategory(category);

                    } catch(CostManagerException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });

                // Handle update button click
                updateBtn.addActionListener(e -> {
                    try {
                        int id = Integer.parseInt(idTF.getText());

                        String categoryName = categoryTF.getText();
                        if(categoryName == null || categoryName.length() == 0) {
                            throw new CostManagerException("categoryName cannot be empty");
                        }

                        Category category = new Category(id, categoryName);

                        View.this.vm.updateCategory(category);

                    } catch(CostManagerException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });

                // Handle delete button click
                deleteBtn.addActionListener(e -> {
                    try{
                        int id = Integer.parseInt(idTF.getText());
                        View.this.vm.deleteCategory(id);
                    } catch (NumberFormatException ex){
                        View.this.showMessage("Problem with entered total price " + ex.getMessage());
                    }
                });
            }

            // This function will clear all the inputs inside the panel
            public void clearInputs() {
                idTF.setText("");
                categoryTF.setText("");
                messageTF.setText("");
            }

            // This function will handle the showing of any message
            public void showMessage(String text) {
                messageTF.setText(text);
            }

            // This function will handle the showing of any categories inisde the table
            public void showCategories(List<Category> categories) {
                // Clear Table
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);

                // Clear inputs
                clearInputs();

                // Add table columns
                tableModel.addColumn("ID");
                tableModel.addColumn("CATEGORY NAME");

                // Add table rows
                for (Category category : categories)
                    tableModel.addRow(new Object[]{category.getId(), category.getCategoryName()});
            }
        }
        public class ReportsPanel extends JPanel {
            // Components of the ReportsPanel
            private final JPanel headerPanel;
            private final JPanel centerPanel;
            private final JPanel southPanel;
            private final JPanel costFormPanel;
            private final JPanel reportPanel;
            private final JPanel btnPanel;

            private final JScrollPane scroll;

            private final JLabel image;
            private final JLabel title;
            private final JLabel startDateLabel;
            private final JLabel endDateLabel;
            private final JLabel messageLabel;

            private final JButton showBtn;
            private final JButton backBtn;

            private TextField messageTF;
            private TextField startDateTF;
            private TextField endDateTF;

            private TextArea reportTA;

            // Constructor, to initialize the components
            public ReportsPanel() {
                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of ReportsPanel
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                // Set the centerPanel as BorderLayout
                centerPanel = new JPanel(new BorderLayout());

                // Create costFormPanel as GridLayout
                costFormPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the reportPanel as BorderLayout
                reportPanel = new JPanel(new BorderLayout());
                reportTA = new TextArea();
                reportTA.setBackground(Color.white);
                scroll = new JScrollPane(reportTA);
                reportPanel.add(scroll, BorderLayout.CENTER); // ScrollPane include list

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                showBtn = new JButton("Show Results");

                // Create southPanel
                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);

                // Create components for southPanel
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                // Create components for start date
                startDateLabel = new JLabel("From Date (YYYY-MM-DD)");
                startDateTF = new TextField();

                // Create components for end date
                endDateLabel = new JLabel("To Date (YYYY-MM-DD)");
                endDateTF = new TextField();

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                // Add each component to his specific panel
                costFormPanel.add(startDateLabel);
                costFormPanel.add(startDateTF);
                costFormPanel.add(endDateLabel);
                costFormPanel.add(endDateTF);

                // Add button to the panel, this button will show the report
                btnPanel.add(showBtn);

                // Add each component to his specific panel
                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(reportPanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                // Add each component to his specific panel
                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to ReportsPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

                // Handle show button click
                showBtn.addActionListener(e -> {
                    try {
                        String startDate = startDateTF.getText();
                        if(startDate == null || startDate.length() == 0) {
                            throw new CostManagerException("startDate cannot be empty");
                        }

                        String endDate = endDateTF.getText();
                        if(endDate == null || endDate.length() == 0) {
                            throw new CostManagerException("endDate cannot be empty");
                        }
                        View.this.vm.getReportSummary(CostItem.validDate(Date.valueOf(startDate)), CostItem.validDate(Date.valueOf(endDate)));

                    } catch(CostManagerException | IllegalArgumentException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });
            }

            // This function will clear the inputs inside the whole panel
            public void clearInputs() {
                startDateTF.setText("");
                endDateTF.setText("");
                reportTA.setText("");
                messageTF.setText("");
            }

            // This function will handle the showing of any message
            public void showMessage(String text) {
                messageTF.setText(text);
            }

            // This function will handle the showing of the cost items report
            public void showReportSummary(List<CostItem> items) {
              reportTA.setText("");
              StringBuilder sb = new StringBuilder();
              for(CostItem item : items) {
                sb.append(item.toString());
                sb.append("\n");
              }
              String text = sb.toString();
              reportTA.setText(text);
            }

        }
        public class PieChartPanel extends JPanel {
            // Components of the PieChartPanel
            private final JPanel headerPanel;
            private final JPanel centerPanel;
            private final JPanel southPanel;

            private final JPanel costFormPanel;
            private final JPanel btnPanel;

            private ChartPanel panel;
            private DefaultPieDataset dataset;
            JFreeChart chart;

            private final JLabel image;
            private final JLabel title;

            private final JLabel startDateLabel;
            private final JLabel endDateLabel;
            private final JLabel currencyLabel;
            private final JLabel messageLabel;
            private final TextField messageTF;
            private final TextField startDateTF;
            private final TextField endDateTF;
            private JComboBox currencyCB;

            private final JButton showBtn;
            private final JButton backBtn;

            // Constructor, to initialize the components
            public PieChartPanel() {
                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of PieChartPanel
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                // Set the centerPanel as BorderLayout
                centerPanel = new JPanel(new BorderLayout());

                // Create costFormPanel
                costFormPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Create Chart Panel
                dataset = new DefaultPieDataset();
                chart = ChartFactory.createPieChart3D("Pie Chart Diagram For Total Costs \nSplits To Categories ", dataset, true, true, false);
                panel = new ChartPanel(chart);

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                showBtn = new JButton("Show Results");

                // Create south Panel
                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);

                // Create components for south Panel
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                // Create components for start date
                startDateLabel = new JLabel("From Date (YYYY-MM-DD)");
                startDateTF = new TextField();

                // Create components for end date
                endDateLabel = new JLabel("To Date (YYYY-MM-DD)");
                endDateTF = new TextField();

                // Create components for Currency (will be used for the rates)
                currencyLabel = new JLabel("Currency Conversion");
                currencyCB = new JComboBox();

                // Add items to currencyCB
                currencyCB.addItem("ILS");
                currencyCB.addItem("USD");
                currencyCB.addItem("EURO");
                currencyCB.addItem("GPB");

                // Set light weight to currencyCB (to make sure that items not hidden)
                currencyCB.setLightWeightPopupEnabled(false);

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                // Add each component to his specific panel
                costFormPanel.add(startDateLabel);
                costFormPanel.add(startDateTF);
                costFormPanel.add(endDateLabel);
                costFormPanel.add(endDateTF);
                costFormPanel.add(currencyLabel);
                costFormPanel.add(currencyCB);

                // Add show button
                btnPanel.add(showBtn);

                // Add each component to his specific panel
                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(panel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                // Add each component to his specific panel
                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to PieChartPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

                // Handle show button click
                showBtn.addActionListener(e -> {
                    try {
                        String startDate = startDateTF.getText();
                        if(startDate == null || startDate.length() == 0) {
                            throw new CostManagerException("startDate cannot be empty");
                        }

                        String endDate = endDateTF.getText();
                        if(endDate == null || endDate.length() == 0) {
                            throw new CostManagerException("endDate cannot be empty");
                        }

                        String currencyStr = currencyCB.getSelectedItem().toString();
                        Currency currency = null;
                        switch (currencyStr) {
                            case "EURO":
                                currency = Currency.EURO;
                                break;
                            case "USD":
                                currency = Currency.USD;
                                break;
                            case "GBP":
                                currency = Currency.GBP;
                                break;
                            case "ILS":
                                currency = Currency.ILS;
                                break;
                            default:
                                currency = Currency.ILS;
                        }
                        View.this.vm.getPieChartSummary(CostItem.validDate(Date.valueOf(startDate)), CostItem.validDate(Date.valueOf(endDate)), currency);

                    } catch(CostManagerException | IllegalArgumentException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });
            }

            // This function will clear the inputs inside the panel
            public void clearInputs() {
                // Clear text fields
                startDateTF.setText("");
                endDateTF.setText("");
                messageTF.setText("");

                // Clear combos
                currencyCB.setSelectedIndex(-1);

                // clear chart
                dataset.clear();
            }

            // This function will display message at the text field
            public void showMessage(String text) {
                messageTF.setText(text);
            }

            // This function will display the pie chart summary
            public void showPieChartSummary(Map<Category, Double> pieChartSummary) {
                dataset.clear();
                pieChartSummary.forEach((k,v)->dataset.setValue(k.getCategoryName(), v));
            }
        }

        // Display the main panel
        public void displayMainPanel() {
            this.panel = mainPanel;
            frame.getContentPane().add(this.panel);
            frame.setVisible(true);
        }

        // Handle the change of panels
        public void changeScreen(JPanel nextPanel) {
            frame.remove(this.panel);
            frame.repaint();
            this.panel = nextPanel;
            frame.add(this.panel);
            frame.setVisible(true);
        }

        // Navigate the text message to the correct panel
        private void navigateMessage(String text) {
            String currentPanel = ApplicationUI.this.panel.getClass().getName();
            int index = currentPanel.lastIndexOf('$');
            if(index != -1)
            {
                index += 1;
                switch (currentPanel.substring(index)) {
                    case "CostPanel":
                        ApplicationUI.this.costPanel.showMessage(text);
                        break;
                    case "CategoryPanel":
                        ApplicationUI.this.categoryPanel.showMessage(text);
                        break;
                    case "ReportsPanel":
                        ApplicationUI.this.reportsPanel.showMessage(text);
                        break;
                    case "PieChartPanel":
                        ApplicationUI.this.pieChartPanel.showMessage(text);
                        break;
                }
            }
        }
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                navigateMessage(text);
            } else {
                SwingUtilities.invokeLater(() -> navigateMessage(text));
            }
        }

        //Handle the display of showing categories.
        private void navigateCategories(List<Category> categories) {
            String currentPanel = ApplicationUI.this.panel.getClass().getName();
            int index = currentPanel.lastIndexOf('$');
            if(index != -1)
            {
                index += 1;
                switch (currentPanel.substring(index)) {
                    case "CostPanel":
                        ApplicationUI.this.costPanel.showCategories(categories);
                        break;
                    case "CategoryPanel":
                        ApplicationUI.this.categoryPanel.showCategories(categories);
                        break;
                }
            }
        }
        public void showCategories(List<Category> categories) {
            if (SwingUtilities.isEventDispatchThread()) {
                navigateCategories(categories);
            } else {
                SwingUtilities.invokeLater(() -> {
                    navigateCategories(categories);
                });
            }
        }

        // Handle the display of showing cost items.
        public void showCostItems(List<CostItem> items) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.costPanel.showCostItems(items);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.costPanel.showCostItems(items);
                });
            }
        }

        // Handle the display of showing the report of cost items (between dates).
        public void showReportSummary(List<CostItem> items) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.reportsPanel.showReportSummary(items);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.reportsPanel.showReportSummary(items);
                });
            }
        }

        // Handle the display of showing the pie chart.
        public void showPieChartSummary(Map<Category, Double> pieChartSummary) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.pieChartPanel.showPieChartSummary(pieChartSummary);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.pieChartPanel.showPieChartSummary(pieChartSummary);

                });
            }
        }

        // Start (display the main panel)
        public void start() {
            displayMainPanel();
        }
    }
}