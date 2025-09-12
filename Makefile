# List your service directories here
SERVICES = admin-service auth-service appointment-service patient-service schedule-service

.PHONY: run-all

run-all:
	tmux new-session -d -s springboot_session 'cd $(word 1, $(SERVICES)) && mvn spring-boot:run'
	$(eval REST_SERVICES := $(wordlist 2, $(words $(SERVICES)), $(SERVICES)))
	@for service in $(REST_SERVICES); do \
		tmux split-window -t springboot_session -v "cd $$service && mvn spring-boot:run"; \
		tmux select-layout -t springboot_session tiled; \
	done
	tmux attach-session -t springboot_session
