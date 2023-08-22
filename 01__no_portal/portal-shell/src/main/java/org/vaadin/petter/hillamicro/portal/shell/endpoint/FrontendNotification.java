package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import dev.hilla.Nonnull;

public record FrontendNotification(
        @Nonnull String frontendId,
        @Nonnull String url,
        @Nonnull NotificationPriority priority,
        @Nonnull String message
) {
}
