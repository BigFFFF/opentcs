// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.panel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.opentcs.guing.base.components.properties.type.AbstractQuantity;
import org.opentcs.guing.base.components.properties.type.Property;
import org.opentcs.guing.common.components.dialogs.DetailsDialogContent;
import org.opentcs.guing.common.util.I18nPlantOverview;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A panel that can edit a quantity property.
 */
public class QuantityEditorPanel
    extends
      JPanel
    implements
      DetailsDialogContent {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(QuantityEditorPanel.class);
  /**
   * The property that is being edited.
   */
  private AbstractQuantity<?> fProperty;

  /**
   * Creates new form QuantityEditorPanel
   */
  @SuppressWarnings("this-escape")
  public QuantityEditorPanel() {
    initComponents();
  }

  @Override
  public String getTitle() {
    return ResourceBundleUtil.getBundle(I18nPlantOverview.PROPERTIES_PATH)
        .getString("quantityEditorPanel.title");
  }

  /**
   * Initialises the dialog elements.
   */
  public void initFields() {
    unitComboBox.setSelectedItem(fProperty.getUnit());

    String value;

    if (fProperty.isInteger()) {
      value = Integer.toString((int) fProperty.getValue());
    }
    else if (fProperty.getValue() instanceof Double) {
      value = Double.toString((double) fProperty.getValue());
    }
    else {
      value = fProperty.getValue().toString();
    }

    numberTextField.setText(value);
  }

  @Override
  public void updateValues() {
    try {
      double value = Double.parseDouble(numberTextField.getText());
      String unit = unitComboBox.getSelectedItem().toString();
      fProperty.setValueAndUnit(value, unit);
    }
    catch (NumberFormatException nfe) {
      // Don't parse String "<different values>"
    }
    catch (IllegalArgumentException e) {
      LOG.error("Exception", e);
    }
  }

  @Override
  public void setProperty(Property property) {
    fProperty = (AbstractQuantity<?>) property;
    unitComboBox.setModel(new DefaultComboBoxModel<>(fProperty.getPossibleUnits().toArray()));
    initFields();
  }

  @Override
  public AbstractQuantity<?> getProperty() {
    return fProperty;
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
    java.awt.GridBagConstraints gridBagConstraints;

    numberTextField = new javax.swing.JTextField();
    unitComboBox = new javax.swing.JComboBox<>();

    setLayout(new java.awt.GridBagLayout());

    numberTextField.setColumns(10);
    numberTextField.setFont(numberTextField.getFont());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(numberTextField, gridBagConstraints);

    unitComboBox.setFont(unitComboBox.getFont());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(unitComboBox, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents
  // CHECKSTYLE:ON
  // FORMATTER:ON

  // FORMATTER:OFF
  // CHECKSTYLE:OFF
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField numberTextField;
  private javax.swing.JComboBox<Object> unitComboBox;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
  // FORMATTER:ON
}
