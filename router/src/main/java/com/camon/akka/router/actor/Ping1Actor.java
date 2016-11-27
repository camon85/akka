package com.camon.akka.router.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping1Actor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Integer) {
            Integer msg = (Integer) message;
            log.info("Ping1Actor({}) received {}", hashCode(), msg);
            work(msg);
        }
    }

    private void work(Integer n) throws Exception {
        log.info("Ping1Actor({}) working on {}", hashCode(), n);
        Thread.sleep(1000);
        log.info("Ping1Actor({}) completed", hashCode());
    }
}
