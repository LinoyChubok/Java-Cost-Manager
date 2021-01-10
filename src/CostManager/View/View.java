package CostManager.View;

import CostManager.ViewModel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


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
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    public static class ApplicationUI {

        // Frame component (for each page)
        private JFrame frame;
        private JPanel panel;
        private MainPanel mainPanel;
        private CostPanel costPanel;
        private CategoryPanel categoryPanel;
        private ReportsPanel reportsPanel;
        private PieChartPanel pieChartPanel;

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
            private JLabel title;
            private JPanel headerPanel;
            private JPanel btnsPanel;
            private JButton CostBtn;
            private JButton CategoryBtn;
            private JButton ReportsBtn;
            private JButton PieChartBtn;
            private JLabel image;

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
                CostBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.costPanel);
                    }
                });

                // Handling category button click
                CategoryBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.categoryPanel);
                    }
                });

                // Handling reports button click
                ReportsBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.reportsPanel);
                    }
                });

                // Handling pie chart button click
                PieChartBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.pieChartPanel);
                    }
                });
            }
        }
        public class CostPanel extends JPanel {
            // Components of the CostPanel
            private JPanel headerPanel;
            private JPanel centerPanel;
            private JPanel southPanel;

            private JPanel costFormPanel;
            private JPanel tablePanel;
            private JPanel btnPanel;

            private JTable table;
            private JScrollPane scroll;

            private JLabel image;
            private JLabel title;

            private JLabel dateLabel;
            private JLabel categoryLabel;
            private JLabel descriptionLabel;
            private JLabel currencyLabel;
            private JLabel totalPriceLabel;
            private JLabel messageLabel;
            private JComboBox categoryCB;
            private JComboBox currencyCB;

            private TextField dateTF;
            private TextField descriptionTF;
            private TextField totalPriceTF;
            private TextField messageTF;

            private JButton addBtn;
            private JButton updateBtn;
            private JButton deleteBtn;
            private JButton backBtn;

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
                costFormPanel = new JPanel(new GridLayout(5,2,10,10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the tablePanel as BorderLayout
                tablePanel = new JPanel(new BorderLayout());
                String[] columnNames = { "ID", "DATE", "CATEGORY", "DESCRIPTION", "CURRENCY", "TOTALPRICE" };
                String[][] data = {
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" },
                        { "Kundan Kumar Jha", "4031", "CSE", "Kundan Kumar Jha", "4031", "CSE" },
                        { "Anand Jha", "6014", "IT", "Anand Jha", "6014", "IT" }
                };
                // Create table with costs data
                table = new JTable(data, columnNames);
                table.setBackground(Color.white);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPane include table

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                southPanel = new JPanel();
                southPanel.setBackground(Color.lightGray);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                dateLabel = new JLabel("Date (YYYY-MM-DD)");
                dateTF = new TextField();
                categoryLabel = new JLabel("Category");
                categoryCB = new JComboBox();
                descriptionLabel = new JLabel("Description");
                descriptionTF = new TextField();
                currencyLabel = new JLabel("Currency");
                currencyCB = new JComboBox();
                totalPriceLabel = new JLabel("Total Price");
                totalPriceTF = new TextField();

                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

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
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

            }
            // Clear inputs
             public void clearInputs() {
                categoryCB.setSelectedIndex(-1);
                currencyCB.setSelectedIndex(-1);
                dateTF.setText("");
                totalPriceTF.setText("");
                descriptionTF.setText("");
             }
        }
        public class CategoryPanel extends JPanel {
            // Components of the CategoryPanel
            private JPanel headerPanel;
            private JPanel centerPanel;
            private JPanel southPanel;

            private JPanel costFormPanel;
            private JPanel tablePanel;
            private JPanel btnPanel;

            private JTable table;
            private JScrollPane scroll;

            private JLabel image;
            private JLabel title;

            private JLabel categoryLabel;
            private JLabel messageLabel;
            private TextField messageTF;
            private TextField categoryTF;

            private JButton addBtn;
            private JButton updateBtn;
            private JButton deleteBtn;
            private JButton backBtn;

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
                costFormPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

                // Set the tablePanel as BorderLayout
                tablePanel = new JPanel(new BorderLayout());
                tablePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
                String[] columnNames = {"ID", "CATEGORY"};
                String[][] data = {
                        {"1", "Shopping"},
                        {"2", "Movies"},
                        {"3", "Food"},
                        {"4", "TV"},
                        {"5", "Water"},
                };
                // Create table with categories data
                table = new JTable(data, columnNames);
                table.setBackground(Color.white);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPane include table

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                southPanel = new JPanel();
                southPanel.setBackground(Color.lightGray);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Dashboard");

                categoryLabel = new JLabel("Category Name");
                categoryTF = new TextField();

                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

                // Add each component to his specific panel
                headerPanel.add(image);
                headerPanel.add(title);

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
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

            }
            // Clear inputs
            public void clearInputs() {
                categoryTF.setText("");
            }

        }
        public class ReportsPanel extends JPanel {
            // Components of the ReportsPanel
            private JPanel headerPanel;
            private JPanel centerPanel;
            private JPanel southPanel;

            private JPanel costFormPanel;
            private JPanel listPanel;
            private JPanel btnPanel;

            private JList list;
            private JScrollPane scroll;

            private JLabel image;
            private JLabel title;

            private JLabel startDateLabel;
            private JLabel endDateLabel;
            private JLabel messageLabel;
            private TextField messageTF;
            private TextField startDateTF;
            private TextField endDateTF;

            private JButton showBtn;
            private JButton backBtn;

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

                // Set the listPanel as BorderLayout
                listPanel = new JPanel(new BorderLayout());
                list = new JList();
                list.setBackground(Color.white);
                scroll = new JScrollPane(list);
                listPanel.add(scroll, BorderLayout.CENTER); // ScrollPane include list

                // Create btnPanel as FlowLayout
                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                showBtn = new JButton("Show Results");

                southPanel = new JPanel();
                southPanel.setBackground(Color.lightGray);
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
                centerPanel.add(listPanel, BorderLayout.CENTER);
                centerPanel.add(btnPanel, BorderLayout.SOUTH);

                southPanel.add(messageLabel);
                southPanel.add(messageTF);
                southPanel.add(backBtn);

                // Add panels to ReportsPanel using BorderLayout
                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                // Back to Dashboard (mainPanel)
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

            }
            // Clear inputs
            public void clearInputs() {
                startDateTF.setText("");
                endDateTF.setText("");
             }

        }
        public class PieChartPanel extends JPanel {
            // Components of the PieChartPanel
            private JPanel headerPanel;
            private JPanel centerPanel;
            private JPanel southPanel;

            private JPanel costFormPanel;
            private JPanel panel;
            private JPanel btnPanel;

            private JLabel image;
            private JLabel title;

            private JLabel startDateLabel;
            private JLabel endDateLabel;
            private JLabel messageLabel;
            private TextField messageTF;
            private TextField startDateTF;
            private TextField endDateTF;

            private JButton showBtn;
            private JButton backBtn;

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
                southPanel.setBackground(Color.lightGray);
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
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

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

        public void start() {
            displayMainMenu();
        }
    }
}