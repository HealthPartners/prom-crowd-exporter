package com.healthpartners.atlas.plugins.listener;

import com.atlassian.crowd.event.directory.DirectoryCreatedEvent;
import com.atlassian.crowd.event.directory.DirectoryDeletedEvent;
import com.atlassian.crowd.event.directory.DirectoryUpdatedEvent;
import com.atlassian.crowd.event.group.GroupCreatedEvent;
import com.atlassian.crowd.event.group.GroupDeletedEvent;
import com.atlassian.crowd.event.group.GroupMembershipDeletedEvent;
import com.atlassian.crowd.event.group.GroupMembershipsCreatedEvent;
import com.atlassian.crowd.event.group.GroupUpdatedEvent;
import com.atlassian.crowd.event.login.AllPasswordsExpiredEvent;
import com.atlassian.crowd.event.user.UserAuthenticatedEvent;
import com.atlassian.crowd.event.user.UserAuthenticationFailedInvalidAuthenticationEvent;
import com.atlassian.crowd.event.user.UserCreatedEvent;
import com.atlassian.crowd.event.user.UserCredentialUpdatedEvent;
import com.atlassian.crowd.event.user.UserCredentialValidationFailed;
import com.atlassian.crowd.event.user.UserDeletedEvent;
import com.atlassian.crowd.event.user.UserUpdatedEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.healthpartners.atlas.plugins.manager.MetricCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsListener {
    private static final Logger log = LoggerFactory.getLogger(MetricsListener.class);

    @Autowired
    private MetricCollector metricCollector;

    @Autowired
    public MetricsListener(@ComponentImport EventPublisher eventPublisher) {
        eventPublisher.register(this);    // Demonstration only -- don't do this in real code!
    }

    @EventListener
    public void onAllPasswordsExpiredEvent(AllPasswordsExpiredEvent allPasswordsExpiredEvent) {
        metricCollector.allPasswordsExpireCounter(
                allPasswordsExpiredEvent.getDirectory().getName()
        );
    }

    @EventListener
    public void onDirectoryCreatedEvent(DirectoryCreatedEvent directoryCreatedEvent) {
        metricCollector.directoryCreateCounter(
                directoryCreatedEvent.getDirectory().getName()
        );
    }

    @EventListener
    public void onDirectoryDeletedEvent(DirectoryDeletedEvent directoryDeletedEvent) {
        metricCollector.directoryDeleteCounter(
                directoryDeletedEvent.getDirectory().getName()
        );
    }

    @EventListener
    public void onDirectoryUpdatedEvent(DirectoryUpdatedEvent directoryUpdatedEvent) {
        metricCollector.directoryUpdateCounter(
                directoryUpdatedEvent.getDirectory().getName()
        );
    }

    @EventListener
    public void onGroupCreatedEvent(GroupCreatedEvent groupCreatedEvent) {
        metricCollector.groupCreateCounter(
                groupCreatedEvent.getDirectory().getName(),
                groupCreatedEvent.getGroup().getName()
        );
    }

    @EventListener
    public void onGroupDeletedEvent(GroupDeletedEvent groupDeletedEvent) {
        metricCollector.groupDeleteCounter(
                groupDeletedEvent.getDirectory().getName(),
                groupDeletedEvent.getGroupName()
        );
    }

    @EventListener
    public void onGroupUpdatedEvent(GroupUpdatedEvent groupUpdatedEvent) {
        metricCollector.groupUpdateCounter(
                groupUpdatedEvent.getDirectory().getName(),
                groupUpdatedEvent.getGroup().getName()
        );
    }

    @EventListener
    public void onGroupMembershipCreatedEvent(GroupMembershipsCreatedEvent groupMembershipsCreatedEvent) {
        String groupName = groupMembershipsCreatedEvent.getGroupName();
        String directory = groupMembershipsCreatedEvent.getDirectory().getName();

        groupMembershipsCreatedEvent.getEntityNames().forEach(entity -> metricCollector.groupMembershipCreateCounter(
                directory,
                groupName,
                entity
        ));
    }

    @EventListener
    public void onGroupMembershipDeletedEvent(GroupMembershipDeletedEvent groupMembershipDeletedEvent) {
        metricCollector.groupMembershipDeleteCounter(
                groupMembershipDeletedEvent.getDirectory().getName(),
                groupMembershipDeletedEvent.getGroupName(),
                groupMembershipDeletedEvent.getEntityName()
        );
    }

    @EventListener
    public void onAuthSuccessEvent(UserAuthenticatedEvent userAuthenticatedEvent) {
        metricCollector.successAuthCounter(
                userAuthenticatedEvent.getApplication().getName(),
                userAuthenticatedEvent.getDirectory().getName(),
                userAuthenticatedEvent.getUser().getName()
        );
    }

    @EventListener
    public void onAuthFailureEvent(UserAuthenticationFailedInvalidAuthenticationEvent userAuthenticationFailedEvent) {
        metricCollector.failedAuthCounter(
                userAuthenticationFailedEvent.getDirectory().getName(),
                userAuthenticationFailedEvent.getUsername()
        );
    }

    @EventListener
    public void onUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        metricCollector.userCreateCounter(
                userCreatedEvent.getDirectory().getName(),
                userCreatedEvent.getUser().getName()
        );
    }

    @EventListener
    public void onUserDeletedEvent(UserDeletedEvent userDeletedEvent) {
        metricCollector.userDeleteCounter(
                userDeletedEvent.getDirectory().getName(),
                userDeletedEvent.getUsername()
        );
    }

    @EventListener
    public void onUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        metricCollector.userUpdateCounter(
                userUpdatedEvent.getDirectory().getName(),
                userUpdatedEvent.getUser().getName()
        );
    }

    @EventListener
    public void onUserCredentialUpdatedEvent(UserCredentialUpdatedEvent userCredentialUpdatedEvent) {
        metricCollector.userCredentialUpdateCounter(
                userCredentialUpdatedEvent.getDirectory().getName(),
                userCredentialUpdatedEvent.getUsername()
        );
    }

    @EventListener
    public void onUserCredentialValidationFailedEvent(UserCredentialValidationFailed userCredentialValidationFailed) {
        metricCollector.userCredentialValidationFailCounter(
                userCredentialValidationFailed.getDirectory().getName(),
                userCredentialValidationFailed.failedReason()
        );
    }

}
