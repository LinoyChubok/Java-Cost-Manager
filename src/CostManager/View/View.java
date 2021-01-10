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

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    public View() {

        SwingUtilities.invokeLater(() -> {
            View.this.ui = new ApplicationUI();
            View.this.ui.start();
        });

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
            frame.setSize(800,600);
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
                title = new JLabel("<html><h1><strong><font color=white>Cost Manager - Java Course Final Project</font></strong></h1><br></html>");
                headerPanel = new JPanel();
                headerPanel.setBackground(new Color(38, 112, 226));

                btnsPanel = new JPanel();
                btnsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
                btnsPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

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
            private JPanel costFormPanel;
            private JPanel btnPanel;
            private JLabel title;
            private JLabel categoryLabel;
            private JLabel currencyLabel;
            private JLabel amountLabel;
            private JLabel descriptionLabel;
            private JComboBox categoryCB;
            private JComboBox currencyCB;
            private TextField amountTF;
            private TextField descriptionTF;
            private JButton saveBtn;

            public CostPanel() {
                setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
                setLayout(new GridLayout(3,1,20,20));
                headerPanel = new JPanel();
                costFormPanel = new JPanel();
                costFormPanel.setLayout(new GridLayout(4,2,10,10));
                btnPanel = new JPanel();
                title = new JLabel("<html><h1><strong><font color=blue>Add a New Cost</font></strong></h1><hr></html>");
                categoryLabel = new JLabel("Category");
                categoryCB = new JComboBox();
                currencyLabel = new JLabel("Currency");
                currencyCB = new JComboBox();
                amountLabel = new JLabel("Amount");
                amountTF = new TextField();
                descriptionLabel = new JLabel("Description");
                descriptionTF = new TextField();
                saveBtn = new JButton("Save");

                headerPanel.add(title);
                costFormPanel.add(categoryLabel);
                costFormPanel.add(categoryCB);
                costFormPanel.add(currencyLabel);
                costFormPanel.add(currencyCB);
                costFormPanel.add(amountLabel);
                costFormPanel.add(amountTF);
                costFormPanel.add(descriptionLabel);
                costFormPanel.add(descriptionTF);
                btnPanel.add(saveBtn);

                add(headerPanel);
                add(costFormPanel);
                add(btnPanel);

            }

             public void clearInputs() {
                categoryCB.setSelectedIndex(-1);
                currencyCB.setSelectedIndex(-1);
                amountTF.setText("");
                descriptionTF.setText("");
             }

        }


        public class CategoryPanel extends JPanel {
            private JPanel headerPanel;
            private JPanel categoryFormPanel;
            private JPanel btnPanel;
            private JLabel title;
            private JLabel categoryLabel;
            private JTextField categoryTF;
            private JButton addBtn;

            public CategoryPanel() {
                setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
                setLayout(new GridLayout(3,1,20,20));
                headerPanel = new JPanel();
                categoryFormPanel = new JPanel();
                categoryFormPanel.setLayout(new GridLayout(1,2));
                btnPanel = new JPanel();
                title = new JLabel("<html><h1><strong><font color=blue>Add a New Category</font></strong></h1><hr></html>");
                categoryLabel = new JLabel("Category");
                categoryTF = new JTextField();

                addBtn = new JButton("Add");

                headerPanel.add(title);
                categoryFormPanel.add(categoryLabel);
                categoryFormPanel.add(categoryTF);
                btnPanel.add(addBtn);

                add(headerPanel);
                add(categoryFormPanel);
                add(btnPanel);

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