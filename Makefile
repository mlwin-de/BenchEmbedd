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

push-system-image: # replace the account and repo name to push the image to your system
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/sample-system/system-adapter:latest

push-images: # only the benchmark provider can do this
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/mlwin/benchmark-controller:latest
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/mlwin/datagen:latest
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/mlwin/taskgen:latest
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/mlwin/eval-storage:latest
	docker push git.project-hobbit.eu:4567/sadeghi.afshin/mlwin/eval-module:latest