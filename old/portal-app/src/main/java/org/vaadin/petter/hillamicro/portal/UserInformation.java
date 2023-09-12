package org.vaadin.petter.hillamicro.portal;

import dev.hilla.Nonnull;

public record UserInformation(
    @Nonnull String username,
    @Nonnull String displayName
    // TODO Icon and other stuff
) {
    
}
