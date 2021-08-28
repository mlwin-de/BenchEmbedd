package org.hobbit.sdk.examples.mlwin.system;

import org.apache.jena.base.Sys;

import java.io.File;
import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			JavaRunCommand jR = new JavaRunCommand();
			//home dir for the user
			String homeDirectory = System.getProperty("user.home");
			//thesis directory where all the datasets/trained models are
			String dir = homeDirectory +"/thesis_work/grail_MDE_AUC_Bash/kge/";
			//run command
			//place your command here. Replace dataset name with your intended one
			jR.runCommand("python -u run.py --do_test --data_path ../data/FB15K_H/Transductive/Symmetry/People --model TransE -init=" + dir + "/models/transE_FB15k_0", dir);

			//TransETest transe = new TransETest();
			//String result = transe.test_triple(7315, 2328, 78);
			//System.out.print(result);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
