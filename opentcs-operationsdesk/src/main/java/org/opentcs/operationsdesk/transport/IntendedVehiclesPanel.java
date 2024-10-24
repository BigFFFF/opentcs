// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.transport;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.opentcs.data.model.Vehicle;
import org.opentcs.guing.common.components.dialogs.DialogContent;
import org.opentcs.guing.common.components.dialogs.InputValidationListener;
import org.opentcs.operationsdesk.util.I18nPlantOverviewOperating;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * A UI to select an intended vehicle for a transport order.
 */
public class IntendedVehiclesPanel
    extends
      DialogContent {

  /**
   * Entry for the automatic intended vehicle.
   */
  private final String automaticEntry;
  /**
   * Available vehicles.
   */
  private final List<Vehicle> vehicles;
  /**
   * Listeners to be notified about the validity of user input.
   */
  private final List<InputValidationListener> validationListeners = new ArrayList<>();

  /**
   * Creates new instance.
   *
   * @param items possible vehicles
   */
  @SuppressWarnings("this-escape")
  public IntendedVehiclesPanel(Set<Vehicle> items) {
    requireNonNull(items, "items");

    initComponents();
    automaticEntry = ResourceBundleUtil.getBundle(I18nPlantOverviewOperating.TRANSPORTORDER_PATH)
        .getString("intendedVehiclesPanel.automatic_entry.text");

    vehicles = items.stream()
        .sorted(Comparator.comparing(Vehicle::getName, String.CASE_INSENSITIVE_ORDER))
        .collect(Collectors.toList());

    Vector<String> names = new Vector<>();
    names.add(automaticEntry);
    for (Vehicle vehicle : vehicles) {
      names.add(vehicle.getName());
    }

    itemsComboBox.setModel(new DefaultComboBoxModel<>(names));
    JTextField textField = (JTextField) (itemsComboBox.getEditor().getEditorComponent());
    textField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        verify();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        verify();

      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        verify();
      }
    });
  }

  public void addInputValidationListener(InputValidationListener listener) {
    requireNonNull(listener, "listener");

    this.validationListeners.add(listener);
    verify();
  }

  /**
   * Returns the selected vehicle.
   *
   * @return The selected vehicle.
   */
  public Optional<Vehicle> getSelectedVehicle() {
    int index = itemsComboBox.getSelectedIndex();

    // The automatic entry exists on position 0 for which case we want to return empty aswell.
    if (index <= 0 || index > vehicles.size()) {
      return Optional.empty();
    }
    return Optional.of(vehicles.get(index - 1));
  }

  @Override
  public void update() {
  }

  @Override
  public void initFields() {
  }

  private void verify() {
    JTextField textField = (JTextField) (itemsComboBox.getEditor().getEditorComponent());
    String inputText = textField.getText();

    inputValidationSuccessful(
        automaticEntry.equals(inputText)
            || vehicles.stream().anyMatch(v -> v.getName().equals(inputText))
    );
  }

  private void inputValidationSuccessful(boolean success) {
    for (InputValidationListener valListener : validationListeners) {
      valListener.inputValidationSuccessful(success);
    }
  }

  // FORMATTER:OFF
  // CHECKSTYLE:OFF
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    itemsLabel = new javax.swing.JLabel();
    itemsComboBox = new javax.swing.JComboBox<>();

    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

    itemsLabel.setFont(itemsLabel.getFont());
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/org/opentcs/plantoverview/operating/panels/transportOrders"); // NOI18N
    itemsLabel.setText(bundle.getString("intendedVehiclesPanel.items_label.text")); // NOI18N
    add(itemsLabel);

    itemsComboBox.setEditable(true);
    itemsComboBox.setFont(itemsComboBox.getFont());
    add(itemsComboBox);
  }// </editor-fold>//GEN-END:initComponents
  // CHECKSTYLE:ON
  // FORMATTER:ON

  // FORMATTER:OFF
  // CHECKSTYLE:OFF
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> itemsComboBox;
  private javax.swing.JLabel itemsLabel;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
  // FORMATTER:ON
}
