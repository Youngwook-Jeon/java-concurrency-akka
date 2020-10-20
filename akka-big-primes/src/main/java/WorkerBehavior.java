import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class WorkerBehavior extends AbstractBehavior<WorkerBehavior.Command> {

    public static class Command implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;
        private ActorRef<ManageBehavior.Command> sender;

        public Command(String message, ActorRef<ManageBehavior.Command> sender) {
            this.message = message;
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public ActorRef<ManageBehavior.Command> getSender() {
            return sender;
        }
    }

    private WorkerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(WorkerBehavior::new);
    }

    private BigInteger prime;

    @Override
    public Receive<Command> createReceive() {
        return handleMessagesWhenWeDontYetHaveAPrimeNumber();
    }

    public Receive<Command> handleMessagesWhenWeDontYetHaveAPrimeNumber() {
        return newReceiveBuilder()
                .onAnyMessage(command -> {
                    BigInteger bigInteger = new BigInteger(2000, new Random());
                    BigInteger prime = bigInteger.nextProbablePrime();
                    command.getSender().tell(new ManageBehavior.ResultCommand(prime));
                    return handleMessagesWhenWeAlreadyHaveAPrimeNumber(prime);
                })
                .build();
    }

    public Receive<Command> handleMessagesWhenWeAlreadyHaveAPrimeNumber(BigInteger prime) {
        return newReceiveBuilder()
                .onAnyMessage(command -> {
                    command.getSender().tell(new ManageBehavior.ResultCommand(prime));
                    return Behaviors.same();
                })
                .build();
    }
}
