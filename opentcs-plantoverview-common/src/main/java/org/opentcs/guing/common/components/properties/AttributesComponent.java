// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties;

import static java.util.Objects.requireNonNull;

import jakarta.inject.Inject;
import java.awt.Component;
import javax.swing.JPanel;
import org.opentcs.guing.base.model.ModelComponent;
import org.opentcs.guing.common.model.ComponentSelectionListener;
import org.opentcs.thirdparty.guing.common.jhotdraw.application.action.edit.UndoRedoManager;

/**
 * A component for viewing and editing of properties.
 * Shows a table with two columns. The left column contains the name of the property.
 * The right column contains the value of the property and can be clicked to edit the value.
 */
public class AttributesComponent
    extends
      JPanel
    implements
      ComponentSelectionListener {

  /**
   * The table with the properties.
   */
  private AttributesContent fPropertiesContent;
  private Component fPropertiesComponent;
  /**
   * The undo manager.
   */
  private final UndoRedoManager fUndoRedoManager;

  /**
   * Creates a new instance.
   *
   * @param undoManager The undo manager to use.
   */
  @Inject
  @SuppressWarnings("this-escape")
  public AttributesComponent(UndoRedoManager undoManager) {
    fUndoRedoManager = requireNonNull(undoManager, "undoManager");
    initComponents();
  }

  @Override
  public void componentSelected(ModelComponent model) {
    setModel(model);
  }

  /**
   * Set the model component.
   *
   * @param model the model component to show properties for.
   */
  public void setModel(ModelComponent model) {
    fPropertiesContent.setModel(model);
    fPropertiesComponent.setVisible(!model.getProperties().isEmpty());

    setDescription();
  }

  /**
   * Resets the display when no model component should be shown.
   */
  public void reset() {
    if (fPropertiesContent != null) {
      fPropertiesContent.reset();
    }

    descriptionLabel.setText("");
  }

  /**
   * Set the text that describes the current model component.
   */
  protected void setDescription() {
    descriptionLabel.setText(fPropertiesContent.getDescription());
  }

  /**
   * Set the properties content.
   *
   * @param content the properties content.
   */
  public void setPropertiesContent(AttributesContent content) {
    fPropertiesContent = content;
    fPropertiesContent.setup(fUndoRedoManager);
    fPropertiesComponent = add(content.getComponent());
    fPropertiesComponent.setVisible(false);
  }

  public AttributesContent getPropertiesContent() {
    return fPropertiesContent;
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

    descriptionLabel = new javax.swing.JLabel();

    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

    descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(descriptionLabel.getFont().getSize()+1f));
    descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    descriptionLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(descriptionLabel);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  protected javax.swing.JLabel descriptionLabel;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
  // FORMATTER:ON
}