package com.camon.akka.router;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.router.actor.PingActor;

/**
 * 아카의 라우터를 보여주기 위한 메인 클래스
 */
public class RouterMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("RouterSystem");
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("start", ActorRef.noSender());
    }
}
