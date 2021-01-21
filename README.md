# Evaluation of ML methods on KG using HOBBIT 

The HOBBIT platform is a distributed FAIR benchmarking platform designed for the evaluation of Linked Data lifecycle. With the help of this powerful tool we aim to test the accuracy of the applications of Machine Learning methods on Knowledge Graph models. 


## Motivation

 Machine Learning methods are highly dependent on the data that are fed to the model. Meaningful and representative data means better ML models. On the other side, Knowledge graphs represent well connected set of data, which unfortunately in most of the cases are incomplete, for that reason task such a link prediction are necessary to complete the knowledge. This way researches came up with the idea of combining these two tools in order to provide better performance of both ML methods as well as KG models. The aim of my work is to efficiently test ML models in KG using Hobbit, a platform for standardized benchmarking. 

### Application of ML methods on Kg using hobbit 
- #### Installation & Development 
Following libraries need to be installed:
* Java 1.8 
* Apache Maven 3.0.5 
* Node.js v6.9.1 
* Npm 4.0.2
* Docker 17.12.1-ce
* Docker Compose 1.17.0 
(more recent versions of the libraries are not tested but might work as well)

This repository is based on: https://github.com/hobbit-project/java-sdk-example
For installing instructions please refer first to the java-sdk-example repo and then return here.

This repository features java implementations of TransE and DistMult. 
The Benchmark reads the trained parameters from the previously mentioned models and does the prediction for the test data that it receives from the Task Generator. The Benchmark Controller, the Data Generator, the Task Generator, the Evaluation Storage and the Evaluation Module are implemented in Java. Some other classes necessary for the completeness of the experiment have been implemented. After having both implemented Benchmark files and System files, their respective meta-data files should be created, Benchmark.ttl and System.ttl. These files contain the needed information by the Hobbit platform and they are written as RDF triples in the Turtle format. 

 - #### Metrics
 For this experiment the metrics used are:  
* Hit@1  
* Hit@3  
* Hit@10  
* Mean Reciprocal Rank 


 - #### Using Hobbit with KG models 


This Benchmark is created based on HOBBIT, to learn how to deploy an instance of the Hobbit platform first check here : https://hobbit-project.github.io/quick_guide.html
and this video: https://www.youtube.com/watch?v=ktAtwU55M6s


Next step is to have the implementations of Both Benchmark Components and the Hobbit platform components. 


These components should be uploaded in the platform as Docker images. To do this the following steps should be completed: 

install-deps: 

    $ mvn validate  

test-benchmark: 

    $ mvn -Dtest=BenchmarkTest#checkHealth test 

package: 

    $ mvn -DskipTests -DincludeDeps=true package 

build-images: 

    $ mvn -Dtest=BenchmarkTest#buildImages surefire:test 

test-dockerized-benchmark: 

    $  mvn -Dtest=BenchmarkTest#checkHealthDockerized test 

push-images: 
```sh
$ docker push git.project-hobbit.eu:4567/username/benchmark_name/benchmark-controller:latest 

$ docker push git.project-hobbit.eu:4567/username/benchmark_name/datagen:latest 

$ docker push git.project-hobbit.eu:4567/ username/benchmark_name/taskgen:latest 
$ docker push git.project-hobbit.eu:4567/username/benchmark_name/eval-storage:latest 

$ docker push git.project-hobbit.eu:4567/username/benchmark_name/system-adapter:latest 

$ docker push git.project-hobbit.eu:4567/username/benchmark_name/eval-module:latest 
```
*username – your username for the GitLab [6]*
*benchmark_name – the name of your benchmark that will appear in the Hobbit Platform*

The meta-data files for both Benchmark and System should also be uploaded in git instance of the Hobbit platform.

### Testing

To be able to upload a private benchmark and system in the HOBBIT platform is necessary to register on HOBBIT GitLab [6]. First, all the docker images should be uploaded there, then the benchmark created and the system will appear on the list of public benchmarks and systems in the HOBBIT platform. 

After starting the platform, the following interfaces will be available: [2] 
* localhost:8080(GUI, default credentials are: challenge-organiser:hobbit,system-provider:hobbit and guest: hobbit) 
* localhost:8081(RabbitMQ) 
* localhost:8890(Virtuoso, default credentials are: Hobbit Platform: Password and dba: Password) 
* localhost:8181 (Keycloak, admin credentials are: admin:H16obbit) 
* localhost:5601(Kibana, available if deployed together with the ELK stack) 

Next step is to run the benchmark, select the respective system and give the parameters for the model (if implemented). 

An URL with the running experiment’s results will be provided by the platform.  The status of the experiment can be seen by selecting *Experiment* Status under the *Experiments* button. When the Experiment is done, the results can be seen by going directly to the URL that the platform provided or by selecting *Experiment* Results under the *Experiments* button, which will show a list of all ran experiments. 

The result of an experiment is a KPI table which shows the parameters of the model and the accuracy metrics. The logs of the experiment are available in JSON, CSV and TXT format for both the benchmark and system. 

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [1]: <https://project-hobbit.eu/>
   [2]: <https://hobbit-project.github.io/index.html>
   [3]: <https://project-hobbit.eu/wp-content/uploads/2017/04/D2.2.1.pdf>
   [4]: <https://docs.riak.com/riak/latest/>
   [5]: <https://project-hobbit.eu/wpcontent/uploads/2018/03/D2.2.2_Second_Version_of_the_HOBBIT_Platform.pdft>
   [6]: <git.project-hobbit.eu>
   
 
 ![alt text][logo]

[logo]: https://mlwin.de/images/mlwin_logo.png "MLwin"
