package org.hobbit.sdk.examples.mlwin.system;

import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.vocabulary.RDF;
import org.hobbit.core.components.AbstractSystemAdapter;
import org.hobbit.vocab.HOBBIT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SystemAdapter extends AbstractSystemAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SystemAdapter.class);
    private Map<String, String> parameters = new HashMap<>();
    TransETest transe;
    @Override
    public void init() throws Exception {
        super.init();
        logger.debug("Init()");

        //Getting default values from system.ttl
        Property parameter;
        NodeIterator objIterator;
        ResIterator iterator = systemParamModel.listResourcesWithProperty(RDF.type, HOBBIT.Parameter);
        Property defaultValProperty = systemParamModel.getProperty("http://w3id.org/hobbit/vocab#defaultValue");
        while (iterator.hasNext()) {
            parameter = systemParamModel.getProperty(iterator.next().getURI());
            objIterator = systemParamModel.listObjectsOfProperty(parameter, defaultValProperty);
            while (objIterator.hasNext()) {
                String value = objIterator.next().asLiteral().getString();
                parameters.put(parameter.getLocalName(), value);
            }
        }
        transe = new TransETest();
    }

    @Override
    public void receiveGeneratedData(byte[] data) {
        // handle the incoming data as described in the benchmark description
        String dataStr = new String(data);
    }

    @Override
    public void receiveGeneratedTask(String taskId, byte[] data) {
        // handle the incoming task and create a result
    	String dataStr = new String(data);
    	String[] triple = dataStr.split(" ");

    	int head = Integer.parseInt(triple[0]);
    	int tail = Integer.parseInt(triple[1]);
    	int rel = Integer.parseInt(triple[2]);

        String result = transe.test_triple(head, tail, rel);
        

        // Send the result to the evaluation storage
        try {
            sendResultToEvalStorage(taskId, result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        // Free the resources you requested here
        logger.debug("close()");

        // Always close the super class after yours!
        super.close();
    }

}