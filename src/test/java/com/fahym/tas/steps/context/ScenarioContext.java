package com.fahym.tas.steps.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ScenarioContext {
    private final Map<String, Object> map = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object v = map.get(key);
        if (v == null) return null;
        return type.cast(v);
    }

    public void clear() {
        map.clear();
    }
}
