/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.jdbi.v3.core.cache.JdbiCache;
import org.jdbi.v3.core.cache.JdbiCacheLoader;

/**
 * Cache implementation using the caffeine cache library.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public final class CaffeineCache<K, V> implements JdbiCache<K, V> {

    private final Cache<K, V> cache;

    CaffeineCache(Caffeine<Object, Object> caffeine) {
        this.cache = caffeine.build();
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getWithLoader(K key, JdbiCacheLoader<K, V> loader) {
        return cache.get(key, loader::create);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CacheStats getStats() {
        return cache.stats();
    }
}
