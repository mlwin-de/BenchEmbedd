package org.hobbit.sdk.examples.juliabenchmark.benchmark;

import org.hobbit.core.components.AbstractDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import static org.hobbit.sdk.examples.juliabenchmark.Constants.BENCHMARK_URI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator extends AbstractDataGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DataGenerator.class);
    String fileName;
    String line;
    Map<Integer, String> entity2id= new HashMap<>();
    BufferedReader reader;
    String fileName2;
    Triple<String, String, String> triple = null;
    Triple<Integer, Integer, Integer> tripleID = null;
    String line2;
    BufferedReader reader2;
    String fileName3;
    String line3;
    Map<Integer, String> relation2id= new HashMap<>();
    BufferedReader reader3;
    String data;

    @Override
    public void init() throws Exception {
        // Always init the super class first!
        super.init();
        logger.debug("Init()");
        // Your initialization code comes here...
        fileName = "entity2id.txt";	  
        fileName2 = "test.txt";
        fileName3 = "relation2id.txt";
        InputStream entity2Id = getClass().getResourceAsStream(fileName);       
        InputStream test = getClass().getResourceAsStream(fileName2); 
        InputStream realtion2Id = getClass().getResourceAsStream(fileName3); 
	    reader = new BufferedReader(new InputStreamReader(entity2Id));	         
	    reader2 = new BufferedReader(new InputStreamReader(test));	    	    
	    reader3 = new BufferedReader(new InputStreamReader(realtion2Id));

    }

    @Override
    protected void generateData() throws Exception {
        // Create your data inside this method. You might want to use the
        // id of this data generator and the number of all data generators
        // running in parallel.
        int dataGeneratorId = getGeneratorId();
        int numberOfGenerators = getNumberOfGenerators();

        while ((line = reader.readLine()) != null)
	    {
	    	String[] parts = line.split("\t");
	    	entity2id.put(Integer.parseInt(parts[1]), parts[0]);    
	    }
        reader.close();
        while ((line = reader3.readLine()) != null)
	    {
	    	String[] parts = line.split("\t");
	    	relation2id.put(Integer.parseInt(parts[1]), parts[0]);    
	    }
        reader3.close();
        logger.debug("generateData()");
        while ((line2 = reader2.readLine()) != null)
	    {
	    	String[] parts = line2.split("\t");
	    	triple = new ImmutableTriple<>(parts[0], parts[1], parts[2]);
	        //convert triples to id-s
	    	int headId = getId(entity2id,parts[0]);
		    int tailId = getId(entity2id, parts[2]);
		    int relId = getId(relation2id, parts[1]);
            tripleID = new ImmutableTriple<>(headId, tailId, relId);
		    data = tripleID.toString("%s %s %s");
		    sendDataToTaskGenerator(data.getBytes()); 
		    // the data can be sent to the task generator(s) ...      
	        }
	    reader2.close();       
        // an to system adapter   
        logger.debug("sendDataToTaskGenerator()->{}", data.getBytes());
       //logger.debug("sendDataToSystemAdapter()->{}",data);
       //sendDataToSystemAdapter(data.getBytes());        
    }

    @Override
    public void close() throws IOException {
        // Free the resources you requested here
        logger.debug("close()");
        // Always close the super class after yours!
        super.close();
    }
    
    public static int getId( Map<Integer,String> map, String triple ) {
		for ( Map.Entry<Integer,String> entry : map.entrySet()) {
	    	if(entry.getValue().equals(triple)) 
	    		return entry.getKey();
	    }
		return 0;
	}

}