install-deps:
	mvn validate

test-benchmark:
	mvn -Dtest=BenchmarkTest#checkHealth test

package:
	mvn -DskipTests -DincludeDeps=true package

build-images:
	mvn -Dtest=BenchmarkTest#buildImages surefire:test

test-dockerized-benchmark:
	mvn -Dtest=BenchmarkTest#checkHealthDockerized test


push-images:
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/benchmark-controller:latest
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/datagen:latest
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/taskgen:latest
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/eval-storage:latest
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/system-adapter:latest
	docker push git.project-hobbit.eu:4567/xhulja_shahini/juliabenchmark/eval-module:latest
