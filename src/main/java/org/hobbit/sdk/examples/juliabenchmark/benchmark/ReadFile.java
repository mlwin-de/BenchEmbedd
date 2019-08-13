package org.hobbit.sdk.examples.juliabenchmark.benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class ReadFile {
	public static void main( String[] args ) throws IOException
	{
		
	    String filePath = "/home/xshaini/eclipse-workspace/test/src/test/read.txt";
	    String line;
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    while ((line = reader.readLine()) != null)
	    {
	    	String[] parts = line.split(" ");
	    	Triple<String, String, String> triple= new ImmutableTriple<>(parts[0], parts[1], parts[2]);

	            System.out.println("line: " + triple);
	        
	    }

	    reader.close();
	}
}
