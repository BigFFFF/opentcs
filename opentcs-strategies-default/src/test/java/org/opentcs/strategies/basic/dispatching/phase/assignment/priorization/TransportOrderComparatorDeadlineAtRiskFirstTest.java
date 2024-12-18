// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.phase.assignment.priorization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration;
import org.opentcs.strategies.basic.dispatching.priorization.transportorder.TransportOrderComparatorDeadlineAtRiskFirst;

/**
 */
class TransportOrderComparatorDeadlineAtRiskFirstTest {

  private TransportOrderComparatorDeadlineAtRiskFirst comparator;

  private DefaultDispatcherConfiguration configuration;

  @BeforeEach
  void setUp() {
    configuration = Mockito.mock(DefaultDispatcherConfiguration.class);
    when(configuration.deadlineAtRiskPeriod()).thenReturn(Long.valueOf(60 * 60 * 1000));

    comparator = new TransportOrderComparatorDeadlineAtRiskFirst(configuration);
  }

  @Test
  void sortCriticalDeadlinesUp() {
    Instant deadline = Instant.now();
    TransportOrder plainOrder = new TransportOrder("Some order ", new ArrayList<>());
    TransportOrder order1 = plainOrder.withDeadline(deadline.plus(150, ChronoUnit.MINUTES));
    TransportOrder order2 = plainOrder.withDeadline(deadline.plus(5, ChronoUnit.MINUTES));
    TransportOrder order3 = plainOrder.withDeadline(deadline.plus(170, ChronoUnit.MINUTES));

    List<TransportOrder> list = new ArrayList<>();
    list.add(order1);
    list.add(order2);
    list.add(order3);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(order2)));
  }

}
