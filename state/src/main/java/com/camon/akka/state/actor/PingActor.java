package com.camon.akka.state.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

/**
 * 아카의 상태기계를 구현한 액터
 */
public class PingActor extends UntypedActorWithStash {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef child;

    public PingActor() {
        child = context().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
        getContext().become(initial);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        // Procedure의 동작이 onReceive메서드의 동작을 대신한다.
        log.info("수행되지 않는다.");
    }

    // 맨 처음 실행 상태에서 "work" 메시지를 받으면 zeroDone 상태가 된다.
    Procedure<Object> initial = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            if ("work".equals(message)) {
                child.tell(message, getSelf());
                getContext().become(zeroDone);
            } else {
                stash();
            }
        }
    };

    // zeroDone 상태에서 "done" 메시지를 받으면 oneDone 상태가 된다.
    Procedure<Object> zeroDone = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            if ("done".equals(message)) {
                log.info("Received the first done");
                getContext().become(oneDone);
            } else {
                stash();
            }
        }
    };

    // oneDone 상태에서 "done" 메시지를 받으면 allDone 상태가 된다.
    Procedure<Object> oneDone = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            if ("done".equals(message)) {
                log.info("Received the second done");
                unstashAll();
                getContext().become(allDone);
            } else {
                stash();
            }
        }
    };

    // allDone 상태에서 "reset" 메시지를 받으면 다시 initial 상태가 된다.
    Procedure<Object> allDone = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            if ("reset".equals(message)) {
                log.info("Received a reset");
                getContext().become(initial);
            }
        }
    };
}