package org.vaadin.petter.hillamicro.portal.shell.endpoint;

import dev.hilla.Nonnull;
import dev.hilla.Nullable;

public record Frontend(
        @Nonnull String frontendId,
        @Nonnull String title,
        @Nonnull String url,
        @Nullable String iconUrl
) {
}
