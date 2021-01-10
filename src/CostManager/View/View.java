package CostManager.View;

import CostManager.ViewModel.IViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
        private ResultsPanel resultsPanel;

        public ApplicationUI() {

            mainPanel = new MainPanel();
            costPanel = new CostPanel();
            categoryPanel = new CategoryPanel();
            resultsPanel = new ResultsPanel();

            frame = new JFrame("CostManager");
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
            private JLabel title;
            private JPanel headerPanel;
            private JPanel btnsPanel;
            private JButton CostBtn;
            private JButton CategoryBtn;
            private JButton ReportsBtn;
            private JButton PieChartBtn;
            private JLabel image;

            public MainPanel() {
                setLayout(new BorderLayout());

                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));

                btnsPanel = new JPanel();
                btnsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
                btnsPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));

                CostBtn = new JButton("Costs");
                CategoryBtn = new JButton("Categories");
                ReportsBtn = new JButton("Reports");
                PieChartBtn = new JButton("Pie Chart Diagram");

                headerPanel.add(image);
                headerPanel.add(title);
                btnsPanel.add(CostBtn);
                btnsPanel.add(CategoryBtn);
                btnsPanel.add(ReportsBtn);
                btnsPanel.add(PieChartBtn);

                add(headerPanel, BorderLayout.NORTH);
                add(btnsPanel, BorderLayout.CENTER);

                CostBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.costPanel);
                    }
                });

                CategoryBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.categoryPanel);
                    }
                });

                ReportsBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.resultsPanel);
                    }
                });
            }
        }

        public class CostPanel extends JPanel {
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

            public CostPanel() {
                setLayout(new BorderLayout());

                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                centerPanel = new JPanel(new BorderLayout());

                costFormPanel = new JPanel(new GridLayout(5,2,10,10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

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
                table = new JTable(data, columnNames);
                table.setBackground(Color.white);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPane include table

                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                southPanel = new JPanel();
                southPanel.setBackground(Color.lightGray);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Main Page");

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

                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

            }

             public void clearInputs() {
                categoryCB.setSelectedIndex(-1);
                currencyCB.setSelectedIndex(-1);
                dateTF.setText("");
                totalPriceTF.setText("");
                descriptionTF.setText("");
             }
        }


        public class CategoryPanel extends JPanel {
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


            public CategoryPanel() {
                setLayout(new BorderLayout());

                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));
                image = new JLabel(new ImageIcon(getClass().getResource("/resources/images/logo.png")));
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Track Your Costs!</font></strong></h1></html>");

                centerPanel = new JPanel(new BorderLayout());

                costFormPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                costFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

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
                table = new JTable(data, columnNames);
                table.setBackground(Color.white);
                scroll = new JScrollPane(table);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                tablePanel.add(scroll, BorderLayout.CENTER); // ScrollPane include table

                btnPanel = new JPanel(new FlowLayout());
                btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                southPanel = new JPanel();
                southPanel.setBackground(Color.lightGray);
                messageLabel = new JLabel("Message");
                messageTF = new TextField("", 40);
                messageTF.setEnabled(false);
                backBtn = new JButton("Back to Main Page");

                categoryLabel = new JLabel("Category Name");
                categoryTF = new TextField();

                addBtn = new JButton("Add");
                updateBtn = new JButton("Update");
                deleteBtn = new JButton("Delete");

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

                add(headerPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                add(southPanel, BorderLayout.SOUTH);

                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.changeScreen(ApplicationUI.this.mainPanel);
                    }
                });

            }

            public void clearInputs() {
                categoryTF.setText("");
            }

        }


        public class ResultsPanel extends JPanel {
            private JPanel headerPanel;
            private JPanel resultsFormPanel;
            private JPanel radioPanel;
            private JPanel datesPanel;

            private JPanel showResultsPanel;
            private JPanel btnPanel;

            private ButtonGroup bg;

            private JLabel title;
            private JLabel chooseResultsLabel;
            private JLabel startDateLabel;
            private JLabel endDateLabel;

            private JRadioButton reportRBtb;
            private JRadioButton pieRBtb;

            private JTextField startDateTF;
            private JTextField endDateTF;

            private JButton showResultsBtn;

            public ResultsPanel() {
                setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
                setLayout(new GridLayout(4,1,20,20));
                headerPanel = new JPanel();
                resultsFormPanel = new JPanel();
                resultsFormPanel.setLayout(new GridLayout(2,1,10,10));
                btnPanel = new JPanel();
                title = new JLabel("<html><h1><strong><font color=blue>View Results</font></strong></h1><hr></html>");

                datesPanel = new JPanel();
                datesPanel.setLayout(new GridLayout(2,2,10,10));
                startDateLabel = new JLabel("Start date");
                startDateTF = new JTextField();
                endDateLabel = new JLabel("End date");
                endDateTF = new JTextField();

                radioPanel = new JPanel();
                radioPanel.setLayout(new GridLayout(1,1,0,0));
                chooseResultsLabel = new JLabel("Choose Results Type");
                bg = new ButtonGroup();
                reportRBtb = new JRadioButton("View detailed report");
                reportRBtb.setSelected(true);
                pieRBtb = new JRadioButton("View pie chart diagram");

                showResultsPanel = new JPanel();

                showResultsBtn = new JButton("Show Results");

                headerPanel.add(title);

                datesPanel.add(startDateLabel);
                datesPanel.add(startDateTF);
                datesPanel.add(endDateLabel);
                datesPanel.add(endDateTF);
                resultsFormPanel.add(datesPanel);

                radioPanel.add(chooseResultsLabel);
                radioPanel.add(reportRBtb);
                radioPanel.add(pieRBtb);
                bg.add(reportRBtb);
                bg.add(pieRBtb);
                resultsFormPanel.add(radioPanel);

                btnPanel.add(showResultsBtn);

                add(headerPanel);
                add(resultsFormPanel);
                add(showResultsPanel);
                add(btnPanel);

            }

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