import akka.actor.testkit.typed.CapturedLogEvent;
import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.actor.testkit.typed.javadsl.TestInbox;
import blockchain.WorkerBehavior;
import model.Block;
import model.HashResult;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import utils.BlocksData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MiningTests {

    @Test
    void testMiningFailsIfNonceNotInRange() {
        BehaviorTestKit<WorkerBehavior.Command> testActor = BehaviorTestKit.create(WorkerBehavior.create());
        Block block = BlocksData.getNextBlock(0, "0");
        TestInbox<HashResult> testInbox = TestInbox.create();
        WorkerBehavior.Command message = new WorkerBehavior.Command(block, 0, 5, testInbox.getRef());
        testActor.run(message);
        List<CapturedLogEvent> logMessages = testActor.getAllLogEntries();
        assertEquals(logMessages.size(), 1);
        assertEquals(logMessages.get(0).message(), "null");
        assertEquals(logMessages.get(0).level(), Level.DEBUG);
    }

//    @Test
//    void testMiningPassesIfNonceIsInRange() {
//        BehaviorTestKit<WorkerBehavior.Command> testActor = BehaviorTestKit.create(WorkerBehavior.create());
//        Block block = BlocksData.getNextBlock(0, "0");
//        WorkerBehavior.Command message = new WorkerBehavior.Command(block, 2000, 3);
//        testActor.run(message);
//        List<CapturedLogEvent> logMessages = testActor.getAllLogEntries();
//        assertEquals(logMessages.size(), 1);
//        String expectedResult = "2147 : d";
//        assertEquals(logMessages.get(0).message(), expectedResult);
//        assertEquals(logMessages.get(0).level(), Level.DEBUG);
//    }
}
