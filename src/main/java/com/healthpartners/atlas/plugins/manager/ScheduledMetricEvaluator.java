package com.healthpartners.atlas.plugins.manager;

public interface ScheduledMetricEvaluator {
    long getLastExecutionTimestamp();
    void restartScraping(int newDelay);
    int getDelay();
    void setDelay(int delay);
}