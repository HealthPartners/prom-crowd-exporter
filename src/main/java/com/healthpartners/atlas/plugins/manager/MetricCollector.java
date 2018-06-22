package com.healthpartners.atlas.plugins.manager;

import io.prometheus.client.Collector;

public interface MetricCollector {
    Collector getCollector();
    void allPasswordsExpireCounter(String directory);
    void directoryCreateCounter(String directory);
    void directoryDeleteCounter(String directory);
    void directoryUpdateCounter(String directory);
    void groupCreateCounter(String directory, String group);
    void groupDeleteCounter(String directory, String group);
    void groupUpdateCounter(String directory, String group);
    void groupMembershipCreateCounter(String directory, String group, String entity);
    void groupMembershipDeleteCounter(String directory, String group, String entity);
    void successAuthCounter(String application, String directory, String username);
    void failedAuthCounter(String directory, String username);
    void userCreateCounter(String directory, String username);
    void userDeleteCounter(String directory, String username);
    void userUpdateCounter(String directory, String username);
    void userCredentialUpdateCounter(String directory, String username);
    void userCredentialValidationFailCounter(String directory, String failedReason);
}