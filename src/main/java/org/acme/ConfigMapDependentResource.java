package org.acme;

import java.util.Map;
import java.util.Set;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDNoGCKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import io.javaoperatorsdk.operator.processing.event.ResourceID;
import io.javaoperatorsdk.operator.processing.event.source.SecondaryToPrimaryMapper;
import io.javaoperatorsdk.operator.processing.event.source.informer.Mappers;

@KubernetesDependent
public class ConfigMapDependentResource
        extends CRUDNoGCKubernetesDependentResource<ConfigMap, RaceConditionReproducerCustomResource>
        implements SecondaryToPrimaryMapper<ConfigMap> {

    public static final String KEY = "key";

    public ConfigMapDependentResource() {
        super(ConfigMap.class);
    }

    @Override
    protected ConfigMap desired(RaceConditionReproducerCustomResource primary,
                                Context<RaceConditionReproducerCustomResource> context) {
        return new ConfigMapBuilder()
                .withMetadata(
                        new ObjectMetaBuilder()
                                .withName(primary.getMetadata().getName())
                                .withNamespace(primary.getMetadata().getNamespace())
                                .build())
                .withData(Map.of(KEY, primary.getSpec().getValue()))
                .build();
    }

    @Override
    public Set<ResourceID> toPrimaryResourceIDs(ConfigMap configMap) {
        return Mappers.fromDefaultAnnotations().toPrimaryResourceIDs(configMap);
    }
}