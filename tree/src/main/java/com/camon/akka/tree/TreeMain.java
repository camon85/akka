package com.camon.akka.tree;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.tree.actor.PingActor;

/**
 * 아카의 계층구조를 보여주기 위한 메인 클래스
 */
public class TreeMain {
  public static void main(String[] args) {
    ActorSystem actorSystem = ActorSystem.create("TreeSystem");
    ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
    ping.tell("work", ActorRef.noSender());
  }
}
