package com.healthpartners.atlas.plugins.manager;

import com.atlassian.crowd.service.license.LicenseService;
import com.atlassian.extras.api.crowd.CrowdLicense;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import io.prometheus.client.Collector;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ExportAsService ({MetricCollector.class})
@Named("metricCollector")
public class MetricCollectorImpl extends Collector implements MetricCollector {
    private static final Logger log = LoggerFactory.getLogger(MetricCollectorImpl.class);

    private final LicenseService licenseService;
    private final ScheduledMetricEvaluator scheduledMetricEvaluator;

    @Autowired
    public MetricCollectorImpl(
            @ComponentImport LicenseService licenseService, ScheduledMetricEvaluator scheduledMetricEvaluator) {
        this.licenseService = licenseService;
        this.scheduledMetricEvaluator = scheduledMetricEvaluator;
    }

    private final Gauge maintenanceExpiryDaysGauge = Gauge.build()
            .name("crowd_maintenance_expiry_days_gauge")
            .help("Maintenance Expiry Days Gauge")
            .create();

//    private final Gauge allUsersGauge = Gauge.build()
//            .name("crowd_all_users_gauge")
//            .help("All Users Gauge")
//            .create();

    private final Gauge activeUsersGauge = Gauge.build()
            .name("crowd_active_users_gauge")
            .help("Active Users Gauge")
            .create();

    private final Counter allPasswordsExpiredCounter = Counter.build()
            .name("crowd_all_passwords_expired_count")
            .help("All Passwords Expired Count")
            .labelNames("directory")
            .create();

    @Override
    public void allPasswordsExpireCounter(String directory) {
        allPasswordsExpiredCounter.labels(directory).inc();
    }

    private final Counter directoryCreateCounter = Counter.build()
            .name("crowd_directory_created_count")
            .help("Directory Created Count")
            .labelNames("directory")
            .create();

    @Override
    public void directoryCreateCounter(String directory) {
        directoryCreateCounter.labels(directory).inc();
    }

    private final Counter directoryDeleteCounter = Counter.build()
            .name("crowd_directory_deleted_count")
            .help("Directory Deleted Count")
            .labelNames("directory")
            .create();

    @Override
    public void directoryDeleteCounter(String directory) {
        directoryDeleteCounter.labels(directory).inc();
    }

    private final Counter directoryUpdateCounter = Counter.build()
            .name("crowd_directory_updated_count")
            .help("Directory Updated Count")
            .labelNames("directory")
            .create();

    @Override
    public void directoryUpdateCounter(String directory) {
        directoryUpdateCounter.labels(directory).inc();
    }

    private final Counter groupCreateCounter = Counter.build()
            .name("crowd_group_created_count")
            .help("Group Created Count")
            .labelNames("directory","group")
            .create();

    @Override
    public void groupCreateCounter(String directory, String group) {
        groupCreateCounter.labels(directory,group).inc();
    }

    private final Counter groupDeleteCounter = Counter.build()
            .name("crowd_group_deleted_count")
            .help("Group Deleted Count")
            .labelNames("directory","group")
            .create();

    @Override
    public void groupDeleteCounter(String directory, String group) {
        groupDeleteCounter.labels(directory,group).inc();
    }

    private final Counter groupUpdateCounter = Counter.build()
            .name("crowd_group_updated_count")
            .help("Group Updated Count")
            .labelNames("directory","group")
            .create();

    @Override
    public void groupUpdateCounter(String directory, String group) {
        groupUpdateCounter.labels(directory,group).inc();
    }

    private final Counter groupMembershipCreateCounter = Counter.build()
            .name("crowd_group_membership_created_count")
            .help("Group Membership Created Count")
            .labelNames("directory","group","entity")
            .create();

    @Override
    public void groupMembershipCreateCounter(String directory, String group, String entity) {
        groupMembershipCreateCounter.labels(directory,group,entity).inc();
    }

    private final Counter groupMembershipDeleteCounter = Counter.build()
            .name("crowd_group_membership_deleted_count")
            .help("Group Membership Deleted Count")
            .labelNames("directory","group","entity")
            .create();

    @Override
    public void groupMembershipDeleteCounter(String directory, String group, String entity) {
        groupMembershipDeleteCounter.labels(directory,group,entity).inc();
    }

    private final Counter successAuthCounter = Counter.build()
            .name("crowd_success_auth_count")
            .help("User Success Auth Count")
            .labelNames("application", "directory", "username")
            .create();

    @Override
    public void successAuthCounter(String application, String directory, String username) {
        successAuthCounter.labels(application, directory, username).inc();
    }

    private final Counter failedAuthCounter = Counter.build()
            .name("crowd_failed_auth_count")
            .help("User Failed Auth Count")
            .labelNames("directory", "username")
            .create();

    @Override
    public void failedAuthCounter(String directory, String username) {
        failedAuthCounter.labels(directory, username).inc();
    }

    private final Counter userCreateCounter = Counter.build()
            .name("crowd_user_created_count")
            .help("User Created Count")
            .labelNames("directory", "username")
            .create();

    @Override
    public void userCreateCounter(String directory, String username) {
        userCreateCounter.labels(directory, username).inc();
    }

    private final Counter userDeleteCounter = Counter.build()
            .name("crowd_user_deleted_count")
            .help("User Deleted Count")
            .labelNames("directory", "username")
            .create();

    @Override
    public void userDeleteCounter(String directory, String username) {
        userDeleteCounter.labels(directory, username).inc();
    }

    private final Counter userUpdateCounter = Counter.build()
            .name("crowd_user_updated_count")
            .help("User Updated Count")
            .labelNames("directory", "username")
            .create();

    @Override
    public void userUpdateCounter(String directory, String username) {
        userUpdateCounter.labels(directory, username).inc();
    }

    private final Counter userCredentialUpdateCounter = Counter.build()
            .name("crowd_user_credential_updated_count")
            .help("User Credential Updated Count")
            .labelNames("directory", "username")
            .create();

    @Override
    public void userCredentialUpdateCounter(String directory, String username) {
        userCredentialUpdateCounter.labels(directory, username).inc();
    }

    private final Counter userCredentialValidationFailCounter = Counter.build()
            .name("crowd_user_credential_validation_failed_count")
            .help("User Credential Validation Failed Count")
            .labelNames("directory", "failedReason")
            .create();

    @Override
    public void userCredentialValidationFailCounter(String directory, String failedReason) {
        userCredentialValidationFailCounter.labels(directory, failedReason).inc();
    }

    @Override
    public Collector getCollector() {
        return this;
    }

    @Override
    public List<Collector.MetricFamilySamples> collect() {
        CrowdLicense crowdLicense = licenseService.getLicense();
        if (crowdLicense != null) {
            log.debug("License info: {}", crowdLicense);
            maintenanceExpiryDaysGauge.set(crowdLicense.getNumberOfDaysBeforeMaintenanceExpiry());
            activeUsersGauge.set(crowdLicense.getMaximumNumberOfUsers());
//            allUsersGauge.set(licenseService.);
        }

        List<Collector.MetricFamilySamples> result = new ArrayList<>();
        result.addAll(maintenanceExpiryDaysGauge.collect());
//        result.addAll(allUsersGauge.collect());
        result.addAll(activeUsersGauge.collect());
        result.addAll(allPasswordsExpiredCounter.collect());
        result.addAll(directoryCreateCounter.collect());
        result.addAll(directoryDeleteCounter.collect());
        result.addAll(directoryUpdateCounter.collect());
        result.addAll(groupCreateCounter.collect());
        result.addAll(groupDeleteCounter.collect());
        result.addAll(groupUpdateCounter.collect());
        result.addAll(groupMembershipCreateCounter.collect());
        result.addAll(groupMembershipDeleteCounter.collect());
        result.addAll(successAuthCounter.collect());
        result.addAll(failedAuthCounter.collect());
        result.addAll(userCreateCounter.collect());
        result.addAll(userDeleteCounter.collect());
        result.addAll(userUpdateCounter.collect());
        result.addAll(userCredentialUpdateCounter.collect());
        result.addAll(userCredentialValidationFailCounter.collect());
        return result;
    }
}
