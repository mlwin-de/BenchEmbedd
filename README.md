## Introduction
This repository contains a benchmark for link prediction using the [HOBBIT platform](https://project-hobbit.eu/outcomes/hobbit-platform/). The HOBBIT platform is a distributed FAIR benchmarking platform designed for the evaluation of a linked data lifecycle. 

This repository also contains the 'TransE' model as an example system for link prediction. 
Follow the instructions of this ReadMe to install the benchmark locally and write your own link prediction system to benchmark it and compare it with other link prediction systems.

Note: This repository is based on the [Hobbit Java SDK Example](https://github.com/hobbit-project/java-sdk-example)

## Installation 

1) Make sure that Oracle 1.8 (or higher) is installed (`java -version`). Or install it by the `sudo add-apt-repository ppa:webupd8team/java && sudo apt-get update && sudo apt-get install oracle-java8-installer -y`.
2) Make sure that docker (v17 and later) is installed (or install it by `sudo curl -sSL https://get.docker.com/ | sh`)
3) Make sure that maven (v3 and later) is installed (or install it by `sudo apt-get install maven`)
4) Add the `127.0.0.1 rabbit` line to `/etc/hosts` (Linux) or `C:\Windows\System32\drivers\etc\hosts` (Windows)
5) Clone this repository
6) Install SDK dependency into your local maven repository (`mvn validate`)


## Test if the benchmark is installed properly: 
The benchmark is a java [Maven](https://maven.apache.org/) project. Run following commands to test the version from pure code and the dockerized version of the benchmark locally.

test-benchmark: 

    $ mvn -Dtest=BenchmarkTest#checkHealth test 

package: 

    $ mvn -DskipTests -DincludeDeps=true package 

build docker images: 

    $ mvn -Dtest=BenchmarkTest#buildImages surefire:test 

test-dockerized-benchmark: 

    $  mvn -Dtest=BenchmarkTest#checkHealthDockerized test 
    
    
Note: If problems with HOBBIT occur you can check here: https://hobbit-project.github.io/quick_guide.html
and this video: https://www.youtube.com/watch?v=ktAtwU55M6s

You dont need a running Hobbit instance to run this code!


## The MLWin Benchmark for Link Prediction

The provided benchmark is a link prediction benchmark that you can run locally from pure java code, locally using docker containers and eventually online using the HOBBIT plattform. The benchmarks consist of a benchmark controller, a data generator, a task generator, an evaluation storage and an evaluation module. All those program parts can be encapsulated in distinct docker images. You can test them locally from pure code or as docker images. Additionally, the benchmark components are also uploaded on the HOBBIT server, s.t. you can use them for fair online benchmarking of the available link prediction systems.

The benchmark computes the following Metrics on WN18rr: 
#### Metrics
* Hit@1  
* Hit@3  
* Hit@10  
* Mean Reciprocal Rank 


## Develop your own System

The purpose of this repository is that people can test their own link prediction system on the benchmark. To write your own compatible system following steps need to be done.

#### Write your system
The file TransEtest.java provides an example of how a system should look like. You can use this file as a base to implement other link prediction systems. The method 'test_triple' contains the most important part of the link prediction model.

#### Modify the meta-data file system.ttl
In order to run the System at the HOBBIT platform you need to provide a meta data file. Use the example file system.ttl and adjust the following lines. You can also change the rdfs:label and rdfs:comment in the file. Create a new repository at your HOBBIT gitlab account and push the meta-data file there. The file needs to be named 'system.ttl'.

Note: instead of pushing through git you can also create the metadata file directly in the repository tab in the browser.

Replace in line 8 'sample-system' through your system repository name.
      
    <http://project-hobbit.eu/sample-system/system>	a	hobbit:SystemInstance;
      
change the path in line 12. Replace 'sadeghi.afshin' through your user name and 'sample-system' through your repository name.

	hobbit:imageName "git.project-hobbit.eu:4567/schmitz.kessenich/sample-system/system-adapter" .
     
 Afterwards, push the meta-data file system.ttl to your HOBBIT GitLab repository.
 
#### Modify the Constants file

Change the variable GIT_SYSTEM_USERNAME to your username on HOBBIT GitLab and the variable SYSTEM_NAME to the name of your new GitLab System repository.

#### Debug your System locally
   Run checkHealth() to test your system. Note that the code in SystemAdapter.java decides which system will be used.
   
      $ mvn -Dtest=BenchmarkTest#checkHealth test 

## Benchmark the System online

In order to benchmark your system online, you need to create a Hobbit GitLab account and push your system as a docker image and a meta data file to a repository.

To be able to upload a system in the HOBBIT platform it is necessary to register on [HOBBIT GitLab](https://git.project-hobbit.eu/). After creating the account, go to User Settings (click on your user picture in the upper right corner at your HOBBIT gitlab instance –> Settings) -> Access Token and generate a personal access token. Now login into your hobbit docker instance to be able to upload a docker image. 

	$ sudo docker login git.project-hobbit.eu:4567

Use your email adress as username and the generated access-token as password. Now you are able to push docker images to repositories from your HOBBIT GitLab instance. Later you need to upload your System as a docker image and a meta-data file to a repository there.
To upload the system as a docker image you need to create the docker image, test it locally and push it to the repository using the following commands:

build docker system image: 

    $  mvn -Dtest=BenchmarkTest#buildSystemImage surefire:test

test-dockerized-system: 

    $  mvn -Dtest=BenchmarkTest#checkHealthDockerized test 
    
push-image:

    $ docker push git.project-hobbit.eu:4567/sadeghi.afshin/sample-system/system-adapter:latest 
    
Again, replace 'sadeghi.afshin' through your user name and 'sample-system' through your repository name.


Login to [HOBBIT](https://master.project-hobbit.eu/) using your GitLab ceredentials and click 'Benchmarks' on the upper bar.
Then select 'BenchEmbed' from the list of possible Benchmarks. If your system was loaded up successfully the system will appear on the list of possible Systems of the BenchEmbed benchmark. (If you can't find your system there, there is most likely an error inside the system.ttl meta data file.)
Choose your system and press the 'submit button'. Now you can click the link from the popup-window to get to the Experiment-details page to see the result once it is done. The status of the experiment can be seen by selecting *Experiment* Status under the *Experiments* button. When the Experiment is done, the results can be seen by going directly to the URL that the platform provided or by selecting *Experiment* Results under the *Experiments* button, which will show a list of all ran experiments. 

The result of an experiment is a KPI table which shows the parameters of the model and the accuracy metrics. The logs of the experiment are available in JSON, CSV and TXT format. 


## Datasets

Per default the repository contains the WN18RR data set. You can find the data set under src/test/resorurces. In this folder there are also 2 files 'entity2vec.txt' and 'relations2vec.txt'. Those files contain the trained embeddings of entities and relations. They are trained by a TransE model from [Knowledge Graph Embedding](https://github.com/DeepGraphLearning/KnowledgeGraphEmbedding). After training the embeddings, the output of the TransE model is converted from an npy file to a txt file using src/kge_output_to_data.py.

If you write your own system you need to provide additionally to the system (that can do your link prediction computation) the trained embedding files 'entity2vec.txt' and 'relations2vec.txt' files. Replace the already contained files at the same location before you build the SystemAdapter Docker image and they will be loaded up automatically.

Note: This repository is not designed to train those embedding models and only performs evaluations.


### Setting the path to trained models and datasets

To set the path to trained models, and also to the datasets, their path must be given as variable in file: `src/main/java/org/hobbit/sdk/examples/mlwin/system/main.java`   

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
 
 ![alt text][logo]

[logo]: https://mlwin.de/images/mlwin_logo.png "MLwin"
