package org.hobbit.sdk.examples.mlwin.benchmark;

import org.hobbit.core.components.AbstractEvaluationStorage;
import org.hobbit.core.components.test.InMemoryEvaluationStore;
import org.hobbit.core.data.ResultPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Simple in-memory implementation of an evaluation storage that can be used for
 * testing purposes.
*/

public class EvalStorage extends AbstractEvaluationStorage {
    private static final Logger logger = LoggerFactory.getLogger(EvalStorage.class);
    /**
     * Map containing a mapping from task Ids to result pairs.
     */
    private Map<String, ResultPair> results = Collections.synchronizedMap(new HashMap<String, ResultPair>());

    @Override
    public void init() throws Exception {
        super.init();
        logger.debug("Init()");
    }

    @Override
    public void receiveResponseData(String taskId, long timestamp, byte[] data) {
    putResult(false, taskId, timestamp, data);
    }

    @Override
    public void receiveExpectedResponseData(String taskId, long timestamp, byte[] data) {
        putResult(true, taskId, timestamp, data);
    }

    /**
     * Adds the given result to the map of results.
     *
     * @param isExpectedResult
     *            true if the result has been received from a task generator,
     *            i.e., is the expected result for a task
     * @param taskId
     *            id of the task
     * @param timestamp
     *            time stamp for the task result
     * @param data
     *            the result
     */
    public synchronized void putResult(boolean isExpectedResult, String taskId, long timestamp, byte[] data) {
        InMemoryEvaluationStore.ResultPairImpl pair;
        if (results.containsKey(taskId)) {
            pair = (InMemoryEvaluationStore.ResultPairImpl) results.get(taskId);
        } else {
            pair = new InMemoryEvaluationStore.ResultPairImpl();
            results.put(taskId, pair);
        }
        if (isExpectedResult) {
            pair.setExpected(new InMemoryEvaluationStore.ResultImpl(timestamp, data));
        } else {
            pair.setActual(new InMemoryEvaluationStore.ResultImpl(timestamp, data));
        }
    }

    @Override
    protected Iterator<ResultPair> createIterator() {
        Iterator<ResultPair> ret = results.values().iterator();
        return ret;
    }

}

