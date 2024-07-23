package org.acme;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.ReactivePubSubCommands;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Reproducer implements IReproducer {

    private static final Logger LOG = Logger.getLogger(Reproducer.class);

    private final ReactivePubSubCommands<byte[]> redisPubSub;

    @Inject
    @SuppressWarnings("CdiInjectionPointsInspection")
    public Reproducer(@NotNull RedisDataSource redisDataSource) {
        this.redisPubSub = redisDataSource.getReactive().pubsub(byte[].class);
    }

    public void subscribeToChannelsNonWorking() {
        LOG.info("Subscribing to channels");
        redisPubSub.subscribeAsMessages("redis:channel:example:here")
                .onItem().invoke(message -> processMessage(message.getPayload()))
                .subscribe().with(
                        item -> LOG.infof("Subscribed to channel: item %s", item),
                        failure -> LOG.errorf("Failed to subscribe to channel: %s, due to: %s", failure.getMessage())
                );
    }

    public void subscribeToChannelsWorks() {
        LOG.info("Subscribing to channels");
        redisPubSub.subscribe("redis:channel:example:here")
                .onItem().invoke(message -> processMessage(message))
                .subscribe().with(
                        item -> LOG.infof("Subscribed to channel: item %s", item),
                        failure -> LOG.errorf("Failed to subscribe to channel: %s, due to: %s", failure.getMessage())
                );
    }

    private void processMessage(byte[] eventInput) {
        LOG.infof("Received message %s from channel", eventInput);
    }
}
