package com.camon.akka.blocknonblock.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * 아카의 퓨처를 이용해서 non-blocking 동작을 보여주는 액터
 */
public class NonblockingActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef child;
    private Timeout timeout = new Timeout(Duration.create(5, "seconds"));
    private final ExecutionContext ec;

    public NonblockingActor() {
        child = context().actorOf(Props.create(CalculationActor.class), "calculationActor");
        this.ec = context().system().dispatcher();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Integer) {
            Future<Object> future = Patterns.ask(child, message, timeout);

            // onSuccess, onComplete, onFailure 등은 blocking 동작이 아니다.
            future.onSuccess(new SaySuccess<>(), ec);
            future.onComplete(new SayComplete<>(), ec);
            future.onFailure(new SayFailure<>(), ec);
        } else if (message instanceof String) {
            log.info("NonblockingActor received a message: " + message);
        }
    }

    public final class SaySuccess<T> extends OnSuccess<T> {
        @Override
        public void onSuccess(T result) throws Throwable {
            log.info("Succeeded with " + result);
        }
    }

    public final class SayComplete<T> extends OnComplete<T> {
        @Override
        public void onComplete(Throwable throwable, T result) throws Throwable {
            log.info("Completed.");
        }
    }

    public final class SayFailure<T> extends OnFailure {
        @Override
        public void onFailure(Throwable throwable) throws Throwable {
            log.info("Failed with " + throwable);
        }
    }
}
