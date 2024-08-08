
lint:
	cd frontend && npm run lint
	cd frontend && npm run prettier

tests:
	cd frontend && npm run test-ci
	cd backend && ./gradlew clean test

build-image:
	cd frontend && npm run build
	cd backend && ./gradlew bootBuildImage

pre-commit:
	make lint tests