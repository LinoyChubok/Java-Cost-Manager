package CostManager.View;

import CostManager.ViewModel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// [X] Main Page
// [X] Cost Page
// [ ] Category Page
// [ ] View Page

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

        public ApplicationUI() {

            mainPanel = new MainPanel();
            costPanel = new CostPanel();
            categoryPanel = new CategoryPanel();

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
            private JPanel btnsPanel;
            private JButton addCostBtn;
            private JButton addCategoryBtn;
            private JButton viewBtn;

            public MainPanel() {
                btnsPanel = new JPanel(new GridLayout(2, 2, 50, 50));
                btnsPanel.setSize(300, 150);
                title = new JLabel("<html><h1><strong>Cost Manager - Java Course Final Project</strong></h1><hr><br></html>");
                addCostBtn = new JButton("Add a new Cost");
                addCategoryBtn = new JButton("Add a new Category");
                viewBtn = new JButton("View detailed report & pie chart diagram");

                add(title);
                btnsPanel.add(addCostBtn);
                btnsPanel.add(addCategoryBtn);
                btnsPanel.add(viewBtn);
                add(btnsPanel);

                addCostBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.costPanel);
                    }
                });

                addCategoryBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ApplicationUI.this.costPanel.clearInputs();
                        ApplicationUI.this.changeScreen(ApplicationUI.this.categoryPanel);
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
                title = new JLabel("<html><h1><strong>Add a New Cost</strong></h1><hr></html>");
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
                title = new JLabel("<html><h1><strong>Add a New Categoty</strong></h1><hr></html>");
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