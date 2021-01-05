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

        public ApplicationUI() {

            mainPanel = new MainPanel();

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
            private JButton addCostBtn;
            private JButton addCategoryBtn;
            private JButton viewBtn;

            public MainPanel() {
                add(new JLabel("<html><h1><strong>Cost Manager - Java Course Final Project</strong></h1><hr><br></html>"));
                JPanel btnPanel = new JPanel(new GridLayout(2, 2, 50, 50));
                btnPanel.setSize(300, 150);

                btnPanel.add(new JButton("Add a new Cost"));
                btnPanel.add(new JButton("Add a new Category"));
                btnPanel.add(new JButton("View detailed report & pie chart diagram"));
                add(btnPanel);
            }
        }

        public void displayMainMenu() {
            this.panel = mainPanel;
            frame.getContentPane().add(this.panel);
            frame.setVisible(true);
        }

        public void start() {
            displayMainMenu();
        }
    }
}