package com.camon.akka.blocknonblock;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.camon.akka.blocknonblock.actor.BlockingActor;

/**
 * 아카의 Future를 이용해서 blocking 동작을 보여주는 메인 클래스
 */
public class BlockingMain {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("BlockingSystem");
        ActorRef blockingActor = actorSystem.actorOf(Props.create(BlockingActor.class), "blockingActor");
        blockingActor.tell(10, ActorRef.noSender());
        blockingActor.tell("hello", ActorRef.noSender());
    }
}
