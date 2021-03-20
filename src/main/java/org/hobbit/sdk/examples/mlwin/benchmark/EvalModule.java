package org.hobbit.sdk.examples.mlwin.benchmark;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.hobbit.core.components.AbstractEvaluationModule;
import org.hobbit.core.rabbit.RabbitMQUtils;
import org.hobbit.sdk.examples.mlwin.Constants;
import org.hobbit.vocab.HOBBIT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class EvalModule extends AbstractEvaluationModule {
    private static final Logger logger = LoggerFactory.getLogger(EvalModule.class);
    public int nrData = 0 ;
    public int hitForHeaD;
    public int hitForTail;
    HitAtVal h1 = new HitAtVal(1); 
    HitAtVal h3 = new HitAtVal(3);
    HitAtVal h10 = new HitAtVal(10);
    double hitHead = 0;
    double hitTail = 0;
    double MRRhead = 0;
    double MRRtail = 0;

//map 
    @Override
    protected void evaluateResponse(byte[] expectedData, byte[] receivedData, long taskSentTimestamp, long responseReceivedTimestamp) throws Exception {
        // evaluate the given response and store the result, e.g., increment internal counters
    	nrData ++;
    	convertData(receivedData);
    	MRRhead += (double) 1/hitForHeaD;
    	MRRtail += (double) 1/hitForTail;
    	h1.increasetHitHead(hitForHeaD);
    	h3.increasetHitHead(hitForHeaD);
    	h10.increasetHitHead(hitForHeaD);
    	h1.increasetHitTail(hitForTail);
    	h3.increasetHitTail(hitForTail);
    	h10.increasetHitTail(hitForTail);
    }

    @Override
    protected Model summarizeEvaluation() throws Exception {
        // All tasks/responsens have been evaluated. Summarize the results,
        // write them into a Jena model and send it to the benchmark controller.
        Model model = createModel(experimentUri);
        Resource experimentResource = model.getResource(experimentUri);
        model.add(experimentResource , RDF.type, HOBBIT.Experiment);
        model.add(experimentResource, model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), model.getResource("http://w3id.org/hobbit/vocab#Experiment"));
        logger.error("Hit head:" + h1.hitForHeaD + "tail: "+ h1.hitForTail);
        hitHead = h1.getAccuracyHead(nrData);
        hitTail = h1.getAccuracyTail(nrData);
        MRRhead = (double) MRRhead/nrData;
        MRRtail = (double) MRRtail/nrData;
        logger.error("Hit at 1 head:" + hitHead + "tail: "+ hitTail+ " nr of data" +nrData);
        logger.error("---------MRR for hit at 1 head:" + MRRhead + "MRR for tail: "+ MRRtail);
        
        model.add(experimentResource, model.getProperty(Constants.KPI_HIT_AT_1), model.createTypedLiteral(hitHead+ " " +hitTail));       
        hitHead = h3.getAccuracyHead(nrData);
        hitTail = h3.getAccuracyTail(nrData);
        logger.error("Hit at 3 head:" + hitHead + "tail: "+ hitTail);
        model.add(experimentResource, model.getProperty(Constants.KPI_HIT_AT_3), model.createTypedLiteral(hitHead+ " " +hitTail));       
        hitHead = h10.getAccuracyHead(nrData);
        hitTail = h10.getAccuracyTail(nrData);
        logger.error("Hit at 10 head:" + hitHead + "tail: "+ hitTail);
        model.add(experimentResource, model.getProperty(Constants.KPI_HIT_AT_10), model.createTypedLiteral(hitHead+ " " +hitTail));       
        logger.error("MRR head:" + MRRhead + "MRR tail: "+ MRRtail);
        model.add(experimentResource, model.getProperty(Constants.KPI_MRR), model.createTypedLiteral(MRRhead+ " " +MRRtail));             
  
        logger.debug(model.toString());
        logger.debug("Sending result model: {}", RabbitMQUtils.writeModel2String(model));

        return model;
    }
    
    public Model createModel(String experiment_uri) {
        Model resultModel = ModelFactory.createDefaultModel();
        resultModel.add(resultModel.createResource(experiment_uri), RDF.type, HOBBIT.Experiment);
        return resultModel;
    }

    @Override
    public void close(){
        // Free the resources you requested here
        logger.debug("close()");
        // Always close the super class after yours!
        try {
            super.close();
        }
        catch (Exception e){

        }
    }
    
    public void convertData(byte[] data) {
    	String rData[]= new String(data).split(" ");
    	hitForHeaD = Integer.parseInt(rData[0]);
    	hitForTail = Integer.parseInt(rData[1]);
    	
    }
    
}
