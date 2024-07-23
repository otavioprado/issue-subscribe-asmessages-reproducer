package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
class MainService {

    private static final Logger LOG = Logger.getLogger(MainService.class);

    @Inject
    Reproducer reproducer;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("MainService is starting");
        //reproducer.subscribeToChannelsNonWorking();
        reproducer.subscribeToChannelsWorks();
    }
}
