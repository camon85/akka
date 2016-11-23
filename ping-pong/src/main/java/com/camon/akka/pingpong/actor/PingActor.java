package com.camon.akka.pingpong.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 임의의 문자열 혹은 "pong" 메시지를 받으면 "ping" 메시지를 보내는 핑 액터
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
