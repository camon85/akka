package com.camon.akka.blocknonblock.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * 아카의 퓨처를 이용해서 블로킹 동작을 보여주는 액터
 */
public class BlockingActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef child;
    private Timeout timeout = new Timeout(Duration.create(5, "seconds"));
    private final ExecutionContext ec;

    public BlockingActor() {
        child = context().actorOf(Props.create(CalculationActor.class), "calculationActor");
        this.ec = context().system().dispatcher();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Integer) {
            Future<Object> future = Patterns.ask(child, message, timeout);
            Integer result = (Integer) Await.result(future, timeout.duration()); // 블로킹!!
            log.info("Final result is " + result);
        } else if (message instanceof String) {
            log.info("BlockingActor received a message: " + message);
        }
    }
}
