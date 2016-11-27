package com.camon.akka.goodbad.actor;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.*;

/**
 * 자식 액터들을 감시하기 위한 전략을 선언하는 액터
 */
public class Ping1Actor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef child1;

    private ActorRef child2;


    public Ping1Actor() {
        this.child1 = context().actorOf(Props.create(Ping2Actor.class), "ping2Actor");
        this.child2 = context().actorOf(Props.create(Ping3Actor.class), "ping3Actor");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String msg = (String) message;
            if ("good".equals(msg) || "bad".equals(msg)) {
                log.info("Ping1Actor received {}", msg);
                child1.tell(msg, getSender());
                child2.tell(msg, getSender());
            }
        } else {
            unhandled(message);
        }
    }

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"), throwable -> {
        if (throwable instanceof ArithmeticException) {
            // Ping2Actor는 "bad" 메시지를 받으면 ArithmeticException을 발생
            return resume();
        } else if (throwable instanceof NullPointerException) {
            // Ping3Actor는 "bad" 메시지를 받으면 NullPoniterException을 발생
            return restart();
        } else if (throwable instanceof IllegalArgumentException) {
            return stop();
        } else {
            return escalate();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
