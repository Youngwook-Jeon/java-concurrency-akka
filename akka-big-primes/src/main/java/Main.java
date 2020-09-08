import akka.actor.typed.ActorSystem;

public class Main {

//    public static void main(String[] args) {
//        ActorSystem<String> actorSystem = ActorSystem.create(FirstSimpleBehavior.create(), "FirstActorSystem");
//        actorSystem.tell("Hello are you there?");
//        actorSystem.tell("who are you");
//        actorSystem.tell("say hello");
//        actorSystem.tell("create a child");
//        actorSystem.tell("This is the second message");
//    }

    public static void main(String[] args) {
        ActorSystem<ManageBehavior.Command> bigPrimes = ActorSystem.create(ManageBehavior.create(), "BigPrimes");
        bigPrimes.tell(new ManageBehavior.InstructionCommand("start"));
    }
}
