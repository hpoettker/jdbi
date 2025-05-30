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
package org.jdbi.v3.postgres;

import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;

import org.jdbi.v3.core.argument.AbstractArgumentFactory;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.argument.ObjectArgument;
import org.jdbi.v3.core.config.ConfigRegistry;

/**
 * Postgres version of argument factory for {@code InetAddress}.
 */
public class InetArgumentFactory extends AbstractArgumentFactory<InetAddress> {
    public InetArgumentFactory() {
        super(Types.OTHER);
    }

    @Override
    protected Argument build(InetAddress value, ConfigRegistry config) {
        return ObjectArgument.of(value.getHostAddress(), Types.OTHER);
    }

    /**
     * @deprecated no longer used
     */
    @Override
    @Deprecated(since = "3.39.0", forRemoval = true)
    public Collection<Type> prePreparedTypes() {
        return Arrays.asList(InetAddress.class, Inet4Address.class, Inet6Address.class);
    }
}
