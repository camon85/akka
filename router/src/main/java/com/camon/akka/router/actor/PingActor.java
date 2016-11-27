package com.camon.akka.router.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;

/**
 * 아카 라우터를 이용해서 자식 액터를 만들고 1부터 10까지의 정수를 보내는 액터
 */
public class PingActor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef childRouter;

    public PingActor() {
        childRouter = getContext().actorOf(new RoundRobinPool(5).props(Props.create(Ping1Actor.class)), "ping1Acotr");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            for (int i = 0; i < 10; i++) {
                childRouter.tell(i, getSelf());
            }
            log.info("PingActor sent 10 messages to the router");
        } else {
            unhandled(message);
        }
    }
}