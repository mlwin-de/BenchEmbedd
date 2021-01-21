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
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/benchmark-controller:latest
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/datagen:latest
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/taskgen:latest
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/eval-storage:latest
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/system-adapter:latest
	docker push git.project-hobbit.eu:4567/schmitz.kessenich/martin_benchmark/eval-module:latest
