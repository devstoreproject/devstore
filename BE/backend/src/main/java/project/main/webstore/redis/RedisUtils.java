package project.main.webstore.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    public void set(String key, Object o, int minutes) {
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object findByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Object patch(String key, Object newData) {
        Object newSavedData = redisTemplate.opsForValue().getAndSet(key, newData);
        return newSavedData;

    }
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

}
