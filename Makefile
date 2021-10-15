
build-api:
	@./mvnw -T 1C clean spring-boot:build-image -pl backend/api -am -DskipTests

build-auth:
	@./mvnw -T 1C clean spring-boot:build-image -pl backend/auth -am -DskipTests

build-admin:
	@./mvnw -T 1C clean spring-boot:build-image -pl backend/admin -am -DskipTests

build-backend:
	@./mvnw -T 1C clean spring-boot:build-image -pl backend/admin -pl backend/api -pl backend/auth -am -DskipTests

build-rest-tests:
	@docker build -f rest-tests.Dockerfile -t docker.io/donkeyhott/ekbtrees-rest-tests:latest .

start:
	@cd deployment && docker-compose up --build -d

stop:
	@cd deployment && docker-compose stop

clear:
	@cd deployment && docker-compose down

full-clear:
	@cd deployment && docker-compose down -v --remove-orphans

logs:
	@cd deployment && docker-compose logs --tail 10 -f $s

rest-tests:
	@cd deployment && docker-compose -f docker-compose-tests.yml up --build --abort-on-container-exit
