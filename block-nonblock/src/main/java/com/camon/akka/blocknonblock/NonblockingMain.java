package com.camon.akka.blocknonblock;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.blocknonblock.actor.NonblockingActor;

/**
 * 아카의 Future를 이용해서 non-blocking 동작을 보여주는 메인 클래스
 */
public class NonblockingMain {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("NonBlockingSystem");
        ActorRef nonblockingActor = actorSystem.actorOf(Props.create(NonblockingActor.class), "nonblockingActor");
        nonblockingActor.tell(10, ActorRef.noSender());
        nonblockingActor.tell("hello", ActorRef.noSender());
    }
}
