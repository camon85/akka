package com.camon.akka.state;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.state.actor.PingActor;

/**
 * 아카의 상태기계를 보여주기 위한 메인 클래스
 */
public class StateMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("StateSystem");
        ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
        ping.tell("work", ActorRef.noSender());
        ping.tell("reset", ActorRef.noSender());
    }
}
