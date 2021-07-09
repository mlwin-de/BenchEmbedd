package org.hobbit.sdk.examples.mlwin.system;
//written by H.
import java.io.InputStreamReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;

public class JavaRunCommand {

    public void runCommand(String command, String path) {

        String s = null;

        try {

            // using the Runtime exec method:
            //execute the provided command in provided dir
            Process p = Runtime.getRuntime().exec(command, null, new File(path));

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("running command : " +command+ "\n");
            while ((s = stdInput.readLine()) != null)
            {
                System.out.println(s);
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null)
            {
                System.out.println(s);
            }

            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}