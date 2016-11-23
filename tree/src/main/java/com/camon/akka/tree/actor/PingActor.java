package com.camon.akka.tree.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 계층구조 트리의 루트에 해당하는 최상위 부모 액터
 */
public class PingActor extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private ActorRef child;
  private int count = 0;

  public PingActor() {
    child = context().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
  }

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof String) {
      String msg = (String) message;
      if ("work".equals(msg)) {
        child.tell(msg, getSelf());
      } else if ("done".equals(msg)) {
        if (count == 0) {
          count++;
        } else {
          log.info("all works are completed");
          count = 0;
        }
      }
    }

  }
}
