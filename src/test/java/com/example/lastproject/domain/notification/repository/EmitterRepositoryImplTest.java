package com.example.lastproject.domain.notification.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmitterRepositoryImplTest {

    @InjectMocks
    private EmitterRepositoryImpl emitterRepository;

    @Mock
    private SseEmitter sseEmitter;

    private String userId = "user1";
    private String emitterId = "user1-emitter1";
    private Object event = "testEvent";

    @Test
    public void Emitter를_정상적으로_저장한다() {
        // when
        SseEmitter result = emitterRepository.save(emitterId, sseEmitter);

        // then
        assertNotNull(result);
        assertEquals(sseEmitter, result);
    }

    @Test
    public void 이벤트를_정상적으로_저장한다() {
        // when
        emitterRepository.saveEventCache(emitterId, event);

        // then
        Map<String, Object> eventCache = emitterRepository.findAllEventCacheStartWithByUserId(userId);
        assertEquals(1, eventCache.size());
        assertEquals(event, eventCache.get(emitterId));
    }

    @Test
    public void 해당회원과_관련된_모든_Emitter를_찾는다() {
        // given
        emitterRepository.save(emitterId, sseEmitter);

        // when
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);

        // then
        assertEquals(1, emitters.size());
        assertEquals(sseEmitter, emitters.get(emitterId));
    }

    @Test
    public void Emitter를_정상적으로_삭제한다() {
        // given
        emitterRepository.save(emitterId, sseEmitter);

        // when
        emitterRepository.deleteById(emitterId);

        // then
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
        assertTrue(emitters.isEmpty());
    }

    @Test
    public void 해당회원과_관련된_모든_Emitter를_삭제한다() {
        // given
        emitterRepository.save(emitterId, sseEmitter);
        String anotherEmitterId = "user1-emitter2";
        emitterRepository.save(anotherEmitterId, sseEmitter);

        // when
        emitterRepository.deleteAllEmitterStartWithUserId(userId);

        // then
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
        assertTrue(emitters.isEmpty());
    }

    @Test
    public void 해당회원과_관련된_모든_이벤트를_삭제한다() {
        // given
        emitterRepository.saveEventCache(emitterId, event);
        String anotherEmitterId = "user1-emitter2";
        emitterRepository.saveEventCache(anotherEmitterId, event);

        // when
        emitterRepository.deleteAllEventCacheStartWithId(userId);

        // then
        Map<String, Object> eventCache = emitterRepository.findAllEventCacheStartWithByUserId(userId);
        assertTrue(eventCache.isEmpty());
    }
}