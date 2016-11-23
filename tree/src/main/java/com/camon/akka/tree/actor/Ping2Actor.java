package com.camon.akka.tree.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by camon on 2016-11-23.
 */
public class Ping2Actor extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof String) {
      String msg = (String) message;
      if("work".equals(msg)) {
        log.info("Ping2 received {}", msg);
        work();
        getSender().tell("done", getSelf());
      }
    }
  }

  private void work() throws Exception {
    Thread.sleep(1000);
    log.info("Ping2 working...");
  }
}
