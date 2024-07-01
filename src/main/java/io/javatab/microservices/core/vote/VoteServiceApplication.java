package io.javatab.microservices.core.vote;

import io.javatab.microservices.api.core.vote.Vote;
import io.javatab.microservices.core.vote.persistence.RedisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class VoteServiceApplication implements CommandLineRunner {

    private final RedisRepository repository;

    public VoteServiceApplication(RedisRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Vote vote = new Vote(1, 1, 3, 9);
        repository.save(vote).subscribe(aLong -> 
            repository.getVote(aLong).subscribe(vote1 -> 
                System.out.println("Vote " + vote1.getCourseId())));
    }

    @Component
    public static class ServiceUtil {

        private final String serverPort;

        public ServiceUtil(@Value("${server.port:8080}") String serverPort) {
            this.serverPort = serverPort;
        }

        public String getServerPort() {
            return serverPort;
        }
    }
}
