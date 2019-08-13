package org.hobbit.sdk.examples.juliabenchmark.benchmark;
/*
 * forwards the data to the system
 * forwards an id (meaning the type of score) to the storage
 */

import org.hobbit.core.components.AbstractTaskGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class TaskGenerator extends AbstractTaskGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TaskGenerator.class);

    @Override
    public void init() throws Exception {
        // Always init the super class first!
        super.init();
        logger.debug("Init()");
        // Your initialization code comes here...
    }

    @Override
    protected void generateTask(byte[] data) throws Exception {
        String dataString = new String(data);
        // Create tasks based on the incoming data inside this method.
        // You might want to use the id of this task generator and the
        // number of all task generators running in parallel.
        //logger.debug("generateTask()");
        int dataGeneratorId = getGeneratorId();
        int numberOfGenerators = getNumberOfGenerators();

        // Create an ID for the task
        String taskId = getNextTaskId();

        //change to expected answer
        String expectedAnswerDataStr = "result_"+taskId;

        // Send the task to the system (and store the timestamp)
        long timestamp = System.currentTimeMillis();
        sendTaskToSystemAdapter(taskId, data);

        // Send the expected answer to the evaluation store
        sendTaskToEvalStorage(taskId, timestamp, expectedAnswerDataStr.getBytes());
    }

    @Override
    public void close() throws IOException {
        // Free the resources you requested here
        logger.debug("close()");
        // Always close the super class after yours!
        super.close();
    }

}