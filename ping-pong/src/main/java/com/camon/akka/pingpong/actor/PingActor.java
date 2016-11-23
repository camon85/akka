package com.camon.akka.pingpong.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by camon on 2016-11-23.
 */
public class PingActor extends UntypedActor {

  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  private ActorRef pong;

  @Override
  public void preStart() throws Exception {
    this.pong = context().actorOf(Props.create(PongActor.class, getSelf()), "pongActor");
  }

  @Override
  public void onReceive(Object message) throws Throwable {
    if (message instanceof String) {
      String msg = (String) message;
      log.info("Ping received {}", msg);
      pong.tell("ping", getSelf());
    }
  }
}
