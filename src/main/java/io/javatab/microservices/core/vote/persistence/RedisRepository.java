package io.javatab.microservices.core.vote.persistence;

import io.javatab.microservices.api.core.vote.Vote;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private static final String KEY = "VOTE";

    private final ReactiveRedisOperations<String, Vote> redisOperations;
    private final ReactiveHashOperations<String, String, Vote> hashOperations;

    public RedisRepositoryImpl(ReactiveRedisOperations<String, Vote> redisOperations) {
        this.redisOperations = redisOperations;
        this.hashOperations = redisOperations.opsForHash();
    }

    @Override
    public Mono<Long> save(Vote vote){
        return this.redisOperations.opsForList().rightPush(KEY, vote);
    }

    @Override
    public Mono<Vote> getVote(Long id) {
        return this.redisOperations.opsForList().rightPop(KEY);
    }
}
