package com.camon.akka.goodbad.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class Ping3Actor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Ping3Actor() {
        log.info("Ping3Actor constructor");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("Ping3Actor preRestart..");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("Ping3Actor postRestart..");
    }


    @Override
    public void postStop() throws Exception {
        log.info("Ping3Actor postStop..");
    }


    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String msg = (String) message;
            if ("good".equals(msg)) {
                goodWork();
                getSender().tell("done", getSelf());
            } else if ("bad".equals(msg)) {
                badWork();
            } else {
                unhandled(message);
            }
        }
    }

    private void goodWork() {
        log.info("Ping3Actor is good.");
    }

    // 일부러 NullPointerException을 발생시킨다.
    private void badWork() {
        throw new NullPointerException();
    }
}
