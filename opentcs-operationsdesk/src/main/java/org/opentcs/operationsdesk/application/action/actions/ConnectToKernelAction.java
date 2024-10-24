// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.application.action.actions;

import static java.util.Objects.requireNonNull;
import static org.opentcs.operationsdesk.util.I18nPlantOverviewOperating.MENU_PATH;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.opentcs.common.KernelClientApplication;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * An action to connect to a kernel.
 */
public class ConnectToKernelAction
    extends
      AbstractAction {

  /**
   * This action's ID.
   */
  public static final String ID = "file.connectToKernel";

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(MENU_PATH);

  private final KernelClientApplication application;

  /**
   * Creates a new instance.
   *
   * @param application The kernel client application.
   */
  @SuppressWarnings("this-escape")
  public ConnectToKernelAction(KernelClientApplication application) {
    this.application = requireNonNull(application, "application");

    putValue(NAME, BUNDLE.getString("connectToKernelAction.name"));
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    application.online(false);
  }
}
