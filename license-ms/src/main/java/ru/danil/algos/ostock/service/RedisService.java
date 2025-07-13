package ru.danil.algos.ostock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.danil.algos.ostock.model.Organization;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final RedisTemplate<String, Organization> redisTemplate;

    public void setValue(Organization value) {
        log.debug("Setting organization with id {} ...", value.getId());
        redisTemplate.opsForValue().set(value.getId(), value);
    }

    public Organization getValue(String id) {
        log.debug("Getting organization with id {} ...", id);
        return redisTemplate.opsForValue().get(id);
    }

    public void deleteValue(String id) {
        redisTemplate.delete(id);
    }
}
