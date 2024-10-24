// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.transport;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.opentcs.guing.base.model.ModelComponent;
import org.opentcs.guing.base.model.elements.PointModel;
import org.opentcs.guing.common.components.dialogs.DialogContent;
import org.opentcs.guing.common.components.dialogs.InputValidationListener;

/**
 * A UI to select a location or a point as a vehicle destination.
 */
public class PointPanel
    extends
      DialogContent {

  /**
   * Available locations.
   */
  protected List<PointModel> fItems;
  /**
   * List of Listeners to be notified about the validity of user input.
   */
  private final List<InputValidationListener> validationListeners = new ArrayList<>();

  /**
   * Creates new instance.
   *
   * @param items possible destination points
   */
  @SuppressWarnings("this-escape")
  public PointPanel(List<PointModel> items) {
    initComponents();
    fItems = items;

    Collections.sort(fItems, getComparator());
    List<String> names = new ArrayList<>();

    for (PointModel pointModel : fItems) {
      names.add(pointModel.getName());
    }

    itemsComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(names)));
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
   * Returns the selected element.
   *
   * @return The selected model component.
   */
  public ModelComponent getSelectedItem() {
    int index = itemsComboBox.getSelectedIndex();

    if (index == -1) {
      return null;
    }
    return fItems.get(index);
  }

  @Override
  public void update() {
  }

  @Override
  public void initFields() {
  }

  protected final Comparator<ModelComponent> getComparator() {
    return new Comparator<ModelComponent>() {

      @Override
      public int compare(ModelComponent item1, ModelComponent item2) {
        String s1 = item1.getName();
        String s2 = item2.getName();
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        return s1.compareTo(s2);
      }
    };
  }

  private void verify() {
    JTextField textField = (JTextField) (itemsComboBox.getEditor().getEditorComponent());
    String inputText = textField.getText();
    for (PointModel pointModel : fItems) {
      if (pointModel.getName().equals(inputText)) {
        inputValidationSuccessful(true);
        return;
      }
    }
    inputValidationSuccessful(false);
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
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/org/opentcs/plantoverview/operating/dialogs/vehiclePopup"); // NOI18N
    itemsLabel.setText(bundle.getString("pointPanel.label_points.text")); // NOI18N
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
