/**
 * This class is necesary for keeping state of the actions during game play
 *
 * @author DKM
 * @version 3.0
 *
 */
package Model;

import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActionModel extends Observable
{

    private ObservableList<String> actions = FXCollections.observableArrayList();
    private static ActionModel actionModel;

    /**
     * This method is necessary for adding game action to observableList
     *
     * @param action The name of action
     */
    public void addAction(String action)
    {
        actions.add(action);
    }

    /**
     * This method is necessary for clearing observableList
     */
    public void clearAction()
    {
        actions.clear();
    }

    /**
     * This method returns the observableList of actions
     *
     * @return actions The observableList of actions
     */
    public ObservableList<String> getActions()
    {
        return actions;
    }

    /**
     * singleton pattern for returning only once instance of object
     *
     * @return actionModel object
     */
    public static ActionModel getActionModel()
    {
        if (actionModel == null)
        {
            actionModel = new ActionModel();
        }
        return actionModel;
    }

}
