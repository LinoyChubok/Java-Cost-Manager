package il.ac.shenkar.costmanager;

import il.ac.shenkar.costmanager.model.*;
import il.ac.shenkar.costmanager.view.*;
import il.ac.shenkar.costmanager.viewmodel.*;

public class Application {
    public static void main(String []args){

        // Creating the application components
        try {
            IModel model = new DerbyDBModel();
            IView view = new View();
            IViewModel vm = new ViewModel();

            // Connecting the components with each other
            view.setViewModel(vm);
            vm.setModel(model);
            vm.setView(view);

        } catch (CostManagerException e) {
            e.printStackTrace();
        }

    }
}