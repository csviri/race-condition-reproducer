package org.acme;

import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;

@ControllerConfiguration(dependents = {@Dependent(type = ConfigMapDependentResource.class)})
public class RaceConditionReproducerReconciler implements Reconciler<RaceConditionReproducerCustomResource> {

    public UpdateControl<RaceConditionReproducerCustomResource> reconcile(RaceConditionReproducerCustomResource primary,
                                                                          Context<RaceConditionReproducerCustomResource> context) {

        return UpdateControl.noUpdate();
    }
}
