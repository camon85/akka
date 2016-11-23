package com.camon.akka.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.pingpong.actor.PingActor;

public class PingPongMain {
  public static void main(String[] args) {
    ActorSystem actorSystem = ActorSystem.create("TestSystem");
    ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
    ping.tell("start", ActorRef.noSender());
  }
}
