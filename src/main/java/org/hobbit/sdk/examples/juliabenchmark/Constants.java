package org.hobbit.sdk.examples.juliabenchmark;

public class Constants {

    //change here
    public static String GIT_SYSTEM_USERNAME = "schmitz.kessenich";
    public static String SYSTEM_NAME = "sample-system";
    public static String GIT_SYSTEM_PATH = "git.project-hobbit.eu:4567/"+GIT_SYSTEM_USERNAME+"/";

    // names for the benchmark (don't change this)
    public static String GIT_USERNAME = "schmitz.kessenich";
    public static String GIT_REPO_PATH = "git.project-hobbit.eu:4567/"+GIT_USERNAME+"/";
    public static String PROJECT_NAME = "martin_benchmark";

    //use these constants within BenchmarkController
    public static final String BENCHMARK_IMAGE_NAME = GIT_REPO_PATH+PROJECT_NAME +"/benchmark-controller";
    public static final String DATAGEN_IMAGE_NAME = GIT_REPO_PATH+PROJECT_NAME +"/datagen";
    public static final String TASKGEN_IMAGE_NAME = GIT_REPO_PATH+PROJECT_NAME +"/taskgen";
    public static final String EVAL_STORAGE_IMAGE_NAME = GIT_REPO_PATH+PROJECT_NAME +"/eval-storage";
    public static final String EVALMODULE_IMAGE_NAME = GIT_REPO_PATH+PROJECT_NAME +"/eval-module";


    public static final String SYSTEM_IMAGE_NAME = GIT_SYSTEM_PATH+SYSTEM_NAME +"/system-adapter";

    public static final String BENCHMARK_URI = "http://project-hobbit.eu/"+PROJECT_NAME;
    public static final String SYSTEM_URI = "http://project-hobbit.eu/"+SYSTEM_NAME+"/system";

    public static final String SDK_BUILD_DIR_PATH = ".";  //build directory, temp docker file will be created there
    public static final String SDK_WORK_DIR_PATH = "/usr/src/"+PROJECT_NAME;    
    public static final String KPI_HIT_AT_1 = BENCHMARK_URI+"/hitAt1";
    public static final String KPI_HIT_AT_3 = BENCHMARK_URI+"/hitAt3";
    public static final String KPI_HIT_AT_10 = BENCHMARK_URI+"/hitAt10";
    public static final String KPI_MRR = BENCHMARK_URI+"/mrr";


}
