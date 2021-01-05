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

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }
}