package com.camon.akka.goodbad;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.goodbad.actor.PingActor;

/**
 * 아카의 Let it crash 철학을 보여주기 위한 메인 클래스
 */
public class GoodMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("TestSystem");
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("good", ActorRef.noSender());
    }
}
