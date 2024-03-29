package org.acme;

import io.javaoperatorsdk.operator.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Runner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        Operator operator = new Operator();
        operator.register(new RaceConditionReproducerReconciler());
        operator.start();
        log.info("Operator started.");
    }
}
