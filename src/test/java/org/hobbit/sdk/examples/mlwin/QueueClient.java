package org.hobbit.sdk.examples.mlwin;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.hobbit.controller.data.ExperimentConfiguration;
import org.hobbit.controller.queue.ExperimentQueueImpl;
import org.hobbit.core.Constants;
import org.hobbit.core.rabbit.RabbitMQUtils;
import org.hobbit.vocab.HOBBIT;

import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Pavel Smirnov. (psmirnov@agtinternational.com / smirnp@gmail.com)
 */
public class QueueClient {
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();
    private static final Logger logger = LoggerFactory.getLogger(QueueClient.class);
    ExperimentQueueImpl queue;
    String username;

    public QueueClient(String username){
        queue = new ExperimentQueueImpl();
        this.username = username;
    }

    public QueueClient(String host, String username){
        environmentVariables.set("HOBBIT_REDIS_HOST", host);

        queue = new ExperimentQueueImpl();
        this.username = username;
    }

    public void flushQueue(){
        int deleted=0;
        for(ExperimentConfiguration configuration :  queue.listAll()){
            queue.remove(configuration);
            deleted++;
        }
        logger.info(String.valueOf(deleted)+" experiments deleted");
    }


    public void submitToQueue(String benchmarkUri, String systemUri, Model model) throws Exception {

        ExperimentConfiguration cfg = new ExperimentConfiguration();
        String id = String.valueOf(String.valueOf(new Date().getTime()));
        cfg.id = id;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cfg.executionDate = cal;
        cfg.benchmarkUri = benchmarkUri;
        cfg.systemUri = systemUri;
        cfg.userName = username;

        String benchmarkInstanceId = Constants.NEW_EXPERIMENT_URI;
        Resource benchmarkInstanceResource = model.createResource(benchmarkInstanceId);
        model.add(benchmarkInstanceResource, RDF.type, HOBBIT.Experiment);
        model.add(benchmarkInstanceResource, HOBBIT.involvesBenchmark, model.createResource(benchmarkUri));
        model.add(benchmarkInstanceResource, HOBBIT.involvesSystemInstance, model.createResource(systemUri));

        cfg.serializedBenchParams = RabbitMQUtils.writeModel2String(model);

        queue.add(cfg);

        logger.info(benchmarkUri+" submitted with "+systemUri);
    }

}
