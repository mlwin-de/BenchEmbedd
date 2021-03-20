package org.hobbit.sdk.examples.mlwin.system;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			TransETest transe = new TransETest();
			String result = transe.test_triple(7315, 2328, 78);
			System.out.print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
