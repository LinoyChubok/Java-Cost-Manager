package CostManager.View;

import CostManager.Model.Category;
import CostManager.Model.CostItem;
import CostManager.Model.CostManagerException;
import CostManager.Model.Currency;
import CostManager.ViewModel.IViewModel;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;

public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    public View() {
        SwingUtilities.invokeLater(() -> {
            View.this.ui = new ApplicationUI();
            View.this.ui.start();
        });
    }

    @Override
    public void setViewModel(IViewModel vm) { this.vm = vm; }

    @Override
    public void showMessage(String text) { ui.showMessage(text); }

    @Override
    public void showCategories(ArrayList<Category> categories) { ui.showCategories(categories); }

    @Override
    public void showCostItems(ArrayList<CostItem> items, ArrayList<Category> categories) { ui.showCostItems(items, categories); }

    @Override
    public void showReportSummary(ArrayList<CostItem> items) { ui.showReportSummary(items); }

    public class ApplicationUI {
        // Frame component (for each page)
        private JFrame frame;
        private JPanel panel;
        private final MainPanel mainPanel;
        private final CostPanel costPanel;
        private final CategoryPanel categoryPanel;
        private final ReportsPanel reportsPanel;
        private final PieChartPanel pieChartPanel;

        public ApplicationUI() {
            // Create instances of panels
            mainPanel = new MainPanel();
            costPanel = new CostPanel();
            categoryPanel = new CategoryPanel();
            reportsPanel = new ReportsPanel();
            pieChartPanel = new PieChartPanel();

            // Create instance of JFrame with name "frame"
            frame = new JFrame("CostManager");
            // Set the frame as BorderLayout
            frame.setLayout(new BorderLayout());
            frame.setSize(800,650);
            frame.setResizable(false);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame = null;
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

            public MainPanel() {
                // Set the window layout manager as BorderLayout
                setLayout(new BorderLayout());

                // Create the components of MainPanel
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));

                // Create btnsPanel as GridLayout
                btnsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
                btnsPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));

                CostBtn = new JButton("Costs");
                CategoryBtn = new JButton("Categories");
                ReportsBtn = new JButton("Reports");
                PieChartBtn = new JButton("Pie Chart Diagram");

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
                    View.this.vm.getAllCostItems();
                    ApplicationUI.this.costPanel.clearInputs();
                    ApplicationUI.this.changeScreen(ApplicationUI.this.costPanel);
                });

                // Handling category button click
                CategoryBtn.addActionListener(e -> {
                    View.this.vm.getAllCategories();
                    ApplicationUI.this.costPanel.clearInputs();
                    ApplicationUI.this.changeScreen(ApplicationUI.this.categoryPanel);
                });

                // Handling reports button click
                ReportsBtn.addActionListener(e -> {
                    ApplicationUI.this.costPanel.clearInputs();
                    ApplicationUI.this.changeScreen(ApplicationUI.this.reportsPanel);
                });

                // Handling pie chart button click
                PieChartBtn.addActionListener(e -> {
                    ApplicationUI.this.costPanel.clearInputs();
                    ApplicationUI.this.changeScreen(ApplicationUI.this.pieChartPanel);
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

                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                dateLabel = new JLabel("Date (YYYY-MM-DD)");
                dateTF = new TextField();
                idLabel = new JLabel("ID");
                idTF = new TextField();
                idTF.setEnabled(false);
                categoryLabel = new JLabel("Category");
                categoryCB = new JComboBox();
                // Set light weight to currencyCB (to make sure that items not hidden)
                categoryCB.setLightWeightPopupEnabled(false);
                descriptionLabel = new JLabel("Description");
                descriptionTF = new TextField();
                currencyLabel = new JLabel("Currency");
                currencyCB = new JComboBox();
                // Add items to currencyCB
                currencyCB.addItem("ILS");
                currencyCB.addItem("USD");
                currencyCB.addItem("EURO");
                currencyCB.addItem("GPB");
                // Set light weight to currencyCB (to make sure that items not hidden)
                currencyCB.setLightWeightPopupEnabled(false);
                totalPriceLabel = new JLabel("Total Price");
                totalPriceTF = new TextField();

                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

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

                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPanel include table

                btnPanel.add(addBtn);
                btnPanel.add(updateBtn);
                btnPanel.add(deleteBtn);

                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(tablePanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

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
                    } catch(CostManagerException ex){
                        View.this.showMessage("Problem with entered data " + ex.getMessage());
                    }
                });

                deleteBtn.addActionListener(e -> {
                    try{
                        int id = Integer.parseInt(idTF.getText());
                        View.this.vm.deleteCostItem(id);
                    } catch (NumberFormatException ex){
                        View.this.showMessage("Problem with entered total price " + ex.getMessage());
                    }
                });

            }

            public void clearInputs() {
                categoryCB.setSelectedIndex(-1);
                currencyCB.setSelectedIndex(-1);
                idTF.setText("");
                dateTF.setText("");
                totalPriceTF.setText("");
                descriptionTF.setText("");
            }

            public void showMessage(String text) {
                messageTF.setText(text);
            }

            public void showCostItems(ArrayList<CostItem> items, ArrayList<Category> categories) {
                // Clear Table
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);

                // Clear Categories Combobox
                categoryCB.removeAllItems();

                // Add Categories
                for (Category category : categories)
                    categoryCB.addItem(category.getCategoryName());

                //Clear inputs
                clearInputs();

                // Add table columns
                tableModel.addColumn("ID");
                tableModel.addColumn("DATE");
                tableModel.addColumn("CATEGORY");
                tableModel.addColumn("DESCRIPTION");
                tableModel.addColumn("CURRENCY");
                tableModel.addColumn("TOTALPRICE");

                // Add table rows
                for (CostItem item : items)
                    tableModel.addRow(new Object[]{item.getId(), item.getDate(),item.getCategory().getCategoryName(), item.getDescription(), item.getCurrency(), item.getTotalPrice()});
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
                table.setBackground(Color.white);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPanel include table
                table.setFocusable(false);
                table.getSelectionModel().addListSelectionListener(event -> {
                    if (table.getSelectedRow() > -1) {
                        idTF.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                        categoryTF.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    }
                });

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                idLabel = new JLabel("ID");
                idTF = new TextField();
                idTF.setEnabled(false);
                categoryLabel = new JLabel("Category Name");
                categoryTF = new TextField();

                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                costFormPanel.add(idLabel);
                costFormPanel.add(idTF);
                costFormPanel.add(categoryLabel);
                costFormPanel.add(categoryTF);

                btnPanel.add(addBtn);
                btnPanel.add(updateBtn);
                btnPanel.add(deleteBtn);

                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(tablePanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

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
            }

            public void clearInputs() {
                categoryTF.setText("");
            }

            public void showMessage(String text) {
                messageTF.setText(text);
            }

            public void showCategories(ArrayList<Category> categories) {
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

                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
                startDateTF = new TextField();
                endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
                endDateTF = new TextField();

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                costFormPanel.add(startDateLabel);
                costFormPanel.add(startDateTF);
                costFormPanel.add(endDateLabel);
                costFormPanel.add(endDateTF);

                btnPanel.add(showBtn);

                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(reportPanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to ReportsPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

            }

            public void clearInputs() {
                startDateTF.setText("");
                endDateTF.setText("");
            }

            public void showMessage(String text) {
                messageTF.setText(text);
            }

            public void showReportSummary(ArrayList<CostItem> items) {
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
            private final JPanel panel;
            private final JPanel btnPanel;

            private final JLabel image;
            private final JLabel title;

            private final JLabel startDateLabel;
            private final JLabel endDateLabel;
            private final JLabel messageLabel;
            private final TextField messageTF;
            private final TextField startDateTF;
            private final TextField endDateTF;

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

                costFormPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the panel as BorderLayout
                panel = new JPanel(new BorderLayout());

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                showBtn = new JButton("Show Results");

                southPanel = new JPanel();
                southPanel.setBackground(JBColor.LIGHT_GRAY);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                startDateLabel = new JLabel("Start Date (YYYY-MM-DD)");
                startDateTF = new TextField();
                endDateLabel = new JLabel("End Date (YYYY-MM-DD)");
                endDateTF = new TextField();

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

                costFormPanel.add(startDateLabel);
                costFormPanel.add(startDateTF);
                costFormPanel.add(endDateLabel);
                costFormPanel.add(endDateTF);

                btnPanel.add(showBtn);

                centerPanel.add(costFormPanel, BorderLayout.NORTH);
                centerPanel.add(panel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);
                // Add panels to PieChartPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(e -> ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel));

            }
            // Clear inputs
            public void clearInputs() {
                startDateTF.setText("");
                endDateTF.setText("");
            }

        }

        public void displayMainMenu() {
            this.panel = mainPanel;
            frame.getContentPane().add(this.panel);
            frame.setVisible(true);
        }
        public void changeScreen(JPanel nextPanel) {
            frame.remove(this.panel);
            frame.repaint();
            this.panel = nextPanel;
            frame.add(this.panel);
            frame.setVisible(true);
        }

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
                        System.out.println("showMessage - need to be set PieChartPanel");
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

        public void showCategories(ArrayList<Category> categories) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.categoryPanel.showCategories(categories);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.categoryPanel.showCategories(categories);
                });
            }
        }
        public void showCostItems(ArrayList<CostItem> items, ArrayList<Category> categories) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.costPanel.showCostItems(items, categories);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.costPanel.showCostItems(items, categories);
                });
            }
        }
        public void showReportSummary(ArrayList<CostItem> items) {
            if (SwingUtilities.isEventDispatchThread()) {
                ApplicationUI.this.reportsPanel.showReportSummary(items);
            } else {
                SwingUtilities.invokeLater(() -> {
                    ApplicationUI.this.reportsPanel.showReportSummary(items);
                });
            }
        }

        public void start() {
            displayMainMenu();
        }
    }
}