package org.acme;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class RaceConditionReproducerReconcilerIntegrationTest {

    public static final String RESOURCE_NAME = "test1";
    public static final String INITIAL_VALUE = "initial value";

    static KubernetesClient client = new KubernetesClientBuilder().build();

    @RepeatedTest(value = 1000, failureThreshold = 1)
    void test(RepetitionInfo info) throws InterruptedException {
        assertThat(getConfigMap()).isNull();
        var cr = client.resource(testResource()).inNamespace("default").create(testResource());

        Thread.sleep(5);

        client.resource(cr)
                .withTimeout(5, TimeUnit.SECONDS)
                .delete();

//        await().untilAsserted(()->{
//            assertThat(client.resources(ReaceConditionReproducerCustomResource.class).inNamespace("default")
//                    .withName(RESOURCE_NAME).get())
//                    .isNull();
//        });
        assertThat(getConfigMap()).isNull();
    }

    ConfigMap getConfigMap() {
        return client.configMaps().inNamespace("default").withName(RESOURCE_NAME).get();
    }

    RaceConditionReproducerCustomResource testResource() {
        var resource = new RaceConditionReproducerCustomResource();
        resource.setMetadata(new ObjectMetaBuilder()
                .withName(RESOURCE_NAME)
                        .withNamespace("default")
                .build());
        resource.setSpec(new RaceConditionReproducerSpec());
        resource.getSpec().setValue(INITIAL_VALUE);
        return resource;
    }
}
