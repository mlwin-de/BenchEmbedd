## Introduction
This repository contains a Benchmark for link prediction using the [HOBBIT platform](https://project-hobbit.eu/outcomes/hobbit-platform/). The HOBBIT platform is a distributed FAIR benchmarking platform designed for the evaluation of a Linked Data lifecycle. 

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


## The BenchEmbed Benchmark for Link Prediction

The provided Benchmark is a link prediction benchmark that you can run locally from pure java code, locally using docker containers and eventually online using the HOBBIT plattform. The benchmarks consist of a benchmark controller, a data generator, a task generator, an evaluation storage and an evaluation module. All those program parts can be encapsulated in distinct docker images. You can test them locally from pure code or as docker images. Additionally, the benchmark components are also uploaded on the HOBBIT server, s.t. you can use them for fair online benchmarking of the available link prediction systems.

The benchmark computes the following Metrics on WN18rr: 
- #### Metrics
* Hit@1  
* Hit@3  
* Hit@10  
* Mean Reciprocal Rank 


## Develop your own System
- #### Modify the meta-data file system.ttl
- #### Modify the Constants file
- #### Write your system
- #### Debug your System locally

- #### Benchmark the System online
In order to benchmark your system online, you need to create a Hobbit GitLab account and push your system as a docker image and a meta data file to a repository.

To be able to upload a system in the HOBBIT platform it is necessary to register on [HOBBIT GitLab](git.project-hobbit.eu). You need to upload your System as a docker image and a meta-data file to the repository. To upload the System as a docker image you need to create the docker image, test it locally and push it to the repository using the following commands:

*note need to change the code s.t. there is a system-test and system-build-image method to only check and build the system not the whole benchmark. I also need to check if pushing the system to another repo really works.

build docker images: 

    $ mvn -Dtest=BenchmarkTest#buildImages surefire:test 

test-dockerized-system: 

    $  mvn -Dtest=BenchmarkTest#checkHealthDockerized test 
    
push-image:

```sh
$ docker push git.project-hobbit.eu:4567/username/benchmark_name/system-adapter:latest 
```
*username – your username for the GitLab [6]*
*system_name – the name of your system that will appear in the Hobbit Platform*

Afterwards, push the meta-data file system.ttl to your HOBBIT GitLab repository.
Login to [HOBBIT](https://master.project-hobbit.eu/) using your GirLab ceredentials and click 'Benchmarks' on the upper bar.
Then select 'BenchEmbed' from the list of possible Benchmarks. If your system was loaded up successfully the system will appear on the list of possible Systems of the BenchEmbed benchmark. (If you can't find your system there, there is most likely an error inside the System.ttl meta data file.)
Choose your system and press the 'submit button'. Now you can click the link from the popup-window to get to the Experiment-details page to see the result once it is done. The status of the experiment can be seen by selecting *Experiment* Status under the *Experiments* button. When the Experiment is done, the results can be seen by going directly to the URL that the platform provided or by selecting *Experiment* Results under the *Experiments* button, which will show a list of all ran experiments. 

The result of an experiment is a KPI table which shows the parameters of the model and the accuracy metrics. The logs of the experiment are available in JSON, CSV and TXT format. 

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [1]: <https://project-hobbit.eu/>
   [2]: <https://hobbit-project.github.io/index.html>
   [3]: <https://project-hobbit.eu/wp-content/uploads/2017/04/D2.2.1.pdf>
   [4]: <https://docs.riak.com/riak/latest/>
   [5]: <https://project-hobbit.eu/wpcontent/uploads/2018/03/D2.2.2_Second_Version_of_the_HOBBIT_Platform.pdft>
   [6]: <git.project-hobbit.eu>
   
 
 ![alt text][logo]

[logo]: https://mlwin.de/images/mlwin_logo.png "MLwin"
