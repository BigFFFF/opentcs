// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.base.components.properties.type;

import java.util.List;
import org.opentcs.guing.base.model.ModelComponent;
import org.opentcs.guing.base.model.elements.PathModel.Type;

/**
 * Subclass for a {@link Type} selection property.
 */
public class LinerTypeProperty
    extends
      SelectionProperty<Type> {

  public LinerTypeProperty(
      ModelComponent model,
      List<Type> possibleValues,
      Object value
  ) {
    super(model, possibleValues, value);
  }
}