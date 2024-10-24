// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.rmi;

import com.google.inject.multibindings.Multibinder;
import jakarta.inject.Singleton;
import org.opentcs.access.rmi.factories.NullSocketFactoryProvider;
import org.opentcs.access.rmi.factories.SecureSocketFactoryProvider;
import org.opentcs.access.rmi.factories.SocketFactoryProvider;
import org.opentcs.customizations.kernel.KernelInjectionModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configures the RMI services extension.
 */
public class RmiServicesModule
    extends
      KernelInjectionModule {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RmiServicesModule.class);

  /**
   * Creates a new instance.
   */
  public RmiServicesModule() {
  }

  @Override
  protected void configure() {
    RmiKernelInterfaceConfiguration configuration
        = getConfigBindingProvider().get(
            RmiKernelInterfaceConfiguration.PREFIX,
            RmiKernelInterfaceConfiguration.class
        );

    if (!configuration.enable()) {
      LOG.info("RMI services disabled by configuration.");
      return;
    }

    bind(RmiKernelInterfaceConfiguration.class)
        .toInstance(configuration);
    bind(RegistryProvider.class)
        .in(Singleton.class);
    bind(UserManager.class)
        .in(Singleton.class);
    bind(UserAccountProvider.class)
        .to(DefaultUserAccountProvider.class);

    if (configuration.useSsl()) {
      bind(SocketFactoryProvider.class)
          .to(SecureSocketFactoryProvider.class)
          .in(Singleton.class);
    }
    else {
      LOG.warn("SSL encryption disabled, connections will not be secured!");
      bind(SocketFactoryProvider.class)
          .to(NullSocketFactoryProvider.class)
          .in(Singleton.class);
    }

    Multibinder<KernelRemoteService> remoteServices
        = Multibinder.newSetBinder(binder(), KernelRemoteService.class);
    remoteServices.addBinding().to(StandardRemotePlantModelService.class);
    remoteServices.addBinding().to(StandardRemoteTransportOrderService.class);
    remoteServices.addBinding().to(StandardRemoteVehicleService.class);
    remoteServices.addBinding().to(StandardRemoteNotificationService.class);
    remoteServices.addBinding().to(StandardRemoteRouterService.class);
    remoteServices.addBinding().to(StandardRemoteDispatcherService.class);
    remoteServices.addBinding().to(StandardRemoteQueryService.class);
    remoteServices.addBinding().to(StandardRemotePeripheralService.class);
    remoteServices.addBinding().to(StandardRemotePeripheralJobService.class);
    remoteServices.addBinding().to(StandardRemotePeripheralDispatcherService.class);

    extensionsBinderAllModes().addBinding()
        .to(StandardRemoteKernelClientPortal.class)
        .in(Singleton.class);
  }
}
