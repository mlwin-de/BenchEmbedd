package org.hobbit.sdk.examples.juliabenchmark.system;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.memory.SystemDictionary;

public class TransETest {
    private static final Logger logger = LoggerFactory.getLogger(TransETest.class);

	public boolean flag = true;
	HashMap <String, Integer> relation2id;
	HashMap <String, Integer> entity2id;
	HashMap <Integer, String> id2entity;
	HashMap <Integer, String> id2relation;

    public int relation_num, entity_num;   
    Double[][] relation_vec, entity_vec;
    int n = 50;
    
	public TransETest() throws IOException {
		this.relation2id = new HashMap<String, Integer>();
		this.entity2id = new HashMap<String, Integer>();
		this.id2entity = new HashMap<Integer, String>();
		this.id2relation = new HashMap<Integer, String>();
		this.relation_num = 0;
		this.entity_num = 0;
		prepare();

	}
	
	public void prepare() throws IOException {
		String fileName = "entities.dict";
        String fileName3 = "relations.dict";
        String fileName5 = "relation2vec.txt";
        String fileName6 = "entity2vec.txt";

        InputStream entity2Id = getClass().getClassLoader().getResourceAsStream(fileName);
        InputStream realtion2Id = getClass().getClassLoader().getResourceAsStream(fileName3);
        InputStream r2v = getClass().getClassLoader().getResourceAsStream(fileName5);
        InputStream e2v = getClass().getClassLoader().getResourceAsStream(fileName6);
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity2Id));
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(realtion2Id));
        BufferedReader reader5 = new BufferedReader(new InputStreamReader(r2v));
        BufferedReader reader6 = new BufferedReader(new InputStreamReader(e2v));
        String line;
        while ((line = reader.readLine()) != null)
	    {
	    	String[] parts = line.split("\t");
	    	entity2id.put(parts[1], Integer.parseInt(parts[0]));
	    	id2entity.put(Integer.parseInt(parts[0]), parts[1]);
	    	entity_num++;
	    }
        reader.close();
        while ((line = reader3.readLine()) != null)
	    {
	    	String[] parts = line.split("\t");
	    	relation2id.put(parts[1], Integer.parseInt(parts[0]));
	    	id2relation.put(Integer.parseInt(parts[0]), parts[1]);
	    	relation_num++;
	    }
        reader3.close();

        relation_vec = new Double[relation_num][n];
        entity_vec = new Double[entity_num][n];
        int i = 0;
        while ((line = reader5.readLine()) != null)
	    {        	
	    	String[] parts = line.split("\t");
	    	relation_vec[i] = new Double[n];
	    	for(int ii = 0; ii < n; ii++) {
	    		relation_vec[i][ii] = Double.parseDouble(parts[ii]);
	    	}
	    	i++;
	    }
        reader5.close();
        i = 0;
        while ((line = reader6.readLine()) != null)
	    {
	    	String[] parts = line.split("\t");
	    	entity_vec[i] = new Double[n];
	    	for(int ii = 0; ii < n; ii++) {
	    		entity_vec[i][ii] = Double.parseDouble(parts[ii]);
	    	}
	    	i++;
	    }
        reader6.close();
	}
	
	public String test_triple(int head, int tail, int rel) {
		HashMap<Integer, Double> hcorrupt = new HashMap<Integer, Double>();
		HashMap<Integer, Double> lcorrupt = new HashMap<Integer, Double>();
		double tmp = calc_sum(head, tail, rel);
		for(int i = 0; i < entity_num ; i++) {
			double hcorruptsum = calc_sum(i, tail ,rel);
			hcorrupt.put(i, hcorruptsum);			
			double tcorruptsum = calc_sum(head, i ,rel);
			lcorrupt.put(i, tcorruptsum);
		}
		hcorrupt.put(head, tmp);
		lcorrupt.put(tail, tmp);		
		LinkedHashMap<Integer, Double> sortedHC = hcorrupt.entrySet().stream()
			.sorted(Entry.comparingByValue())
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		LinkedHashMap<Integer, Double> sortedLC = lcorrupt.entrySet().stream()
				.sorted(Entry.comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		int posHead = new ArrayList<Integer>(sortedHC.keySet()).indexOf(head);
		int posTail = new ArrayList<Integer>(sortedLC.keySet()).indexOf(tail);
    	return (posHead+1)+" "+(posTail+1);
	}
	
	public double calc_sum(int e1, int e2, int rel) {
		double sum =0;
		for(int i =0; i<n;i++) {
			sum += -Math.sqrt(Math.pow(entity_vec[e2][i] - entity_vec[e1][i] - relation_vec[rel][i],2));
		}
		return sum;
	}
}
