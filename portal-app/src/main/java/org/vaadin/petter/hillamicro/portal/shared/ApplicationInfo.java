package org.vaadin.petter.hillamicro.portal.shared;

import static java.util.Objects.requireNonNull;

import dev.hilla.Nonnull;
import dev.hilla.Nullable;

public record ApplicationInfo(
        @Nonnull String applicationId,
        @Nullable String title,
        @Nonnull String path,
        @Nullable String icon,
        @Nonnull String importPath,
        @Nonnull String tag,
        @Nonnull String moduleName,
        boolean requiresAuthentication) {

    public ApplicationInfo {
        requireNonNull(applicationId, "applicationId must not be null");
        requireNonNull(path, "path must not be null");
        requireNonNull(importPath, "importPath must not be null");
        requireNonNull(tag, "tag must not be null");
        requireNonNull(moduleName, "moduleName must not be null");
        // TODO Validate values!
    }
}
