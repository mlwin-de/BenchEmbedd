package org.hobbit.sdk.examples.juliabenchmark;


import static org.hobbit.sdk.examples.juliabenchmark.Constants.*;

import org.hobbit.core.run.ComponentStarter;
import org.hobbit.sdk.docker.builders.DynamicDockerFileBuilder;

/**
 * @author Pavel Smirnov
 */

public class ExampleDockersBuilder extends DynamicDockerFileBuilder {


    public ExampleDockersBuilder(Class runnerClass, String imageName) throws Exception {
        super("ExampleDockersBuilder");
        imageName(imageName);
        //name for searching in logs
        containerName(runnerClass.getSimpleName());
        //temp docker file will be created there
        buildDirectory(SDK_BUILD_DIR_PATH);
        //should be packaged will all dependencies (via 'mvn package -DskipTests=true' command)
        jarFilePath(System.getProperty("sdkJarFilePath"));
        //will be placed in temp dockerFile
        dockerWorkDir(SDK_WORK_DIR_PATH);
        //will be placed in temp dockerFile
        runnerClass(ComponentStarter.class, runnerClass);
    }

}
