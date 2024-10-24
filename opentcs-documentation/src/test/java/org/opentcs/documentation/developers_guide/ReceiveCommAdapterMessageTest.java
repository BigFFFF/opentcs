// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.documentation.developers_guide;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.TransportOrder;

/**
 * Tests showing how to receive messages from communication adapters.
 */
class ReceiveCommAdapterMessageTest {

  @Test
  void receiveInformationViaVehicleProperty() {
    // tag::documentation_receiveMessageFromVehicle[]
    Vehicle vehicle = getSomeVehicle();
    String value = vehicle.getProperty("someKey");
    processPropertyValue(value);
    // end::documentation_receiveMessageFromVehicle[]
  }

  @Test
  void receiveInformationViaTransportOrderProperty() {
    // tag::documentation_receiveMessageFromTransportOrder[]
    TransportOrder transportOrder = getSomeTransportOrder();
    String value = transportOrder.getProperty("someKey");
    processPropertyValue(value);
    // end::documentation_receiveMessageFromTransportOrder[]
  }

  private Vehicle getSomeVehicle() {
    return new Vehicle("some-vehicle");
  }

  private TransportOrder getSomeTransportOrder() {
    return new TransportOrder("some-order", List.of());
  }

  private void processPropertyValue(String propertyValue) {
  }
}
