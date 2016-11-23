package com.camon.akka.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.pingpong.actor.PingActor;

/**
 * 핑퐁 액터 데모를 위한 메인 클래스
 */
public class PingPongMain {
  public static void main(String[] args) {
    ActorSystem actorSystem = ActorSystem.create("TestSystem");
    ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
    ping.tell("start", ActorRef.noSender());
  }
}
