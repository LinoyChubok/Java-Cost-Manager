//package CostManager;
//
//import CostManager.Model.*;
//import CostManager.View.*;
//import CostManager.ViewModel.*;
//
//public class Application {
//    public static void main(String []args){
//
//        // Creating the application components
//        try {
//            IModel model = new DerbyDBModel();
//            IView view = new View();
//            IViewModel vm = new ViewModel();
//
//            // Connecting the components with each other
//            view.setViewModel(vm);
//            vm.setModel(model);
//            vm.setView(view);
//
//        } catch (CostManagerException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
package CostManager;

import CostManager.Model.*;
import CostManager.View.*;
import CostManager.ViewModel.*;

public class Application {
    public static void main(String []args){


            IView view = new View();


    }
}