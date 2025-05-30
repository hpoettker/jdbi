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
package org.jdbi.v3.core.argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jdbi.v3.core.statement.StatementContext;

/**
 * An Argument which uses {@code setObject} to support
 * vendor specific types.
 */
public class ObjectArgument implements Argument {
    private final Object value;
    private final Integer sqlType;

    /**
     * Bind a vendor-supported object with the given SQL type.
     * @param value the value to call @link {@link PreparedStatement#setObject(int, Object)} with
     * @param sqlType the type to bind
     * @see java.sql.Types
     * @deprecated use {@link #of(Object, Integer)} factory method for more consistent {@code null} handling
     */
    @Deprecated(since = "3.11.0", forRemoval = true)
    public ObjectArgument(Object value, Integer sqlType) {
        this.sqlType = sqlType;
        this.value = value;

        if (sqlType == null && value == null) {
            throw new IllegalArgumentException("Null value provided without a type");
        }
    }

    /**
     * Bind a vendor-supported object with the given SQL type.
     * @param value the value to call @link {@link PreparedStatement#setObject(int, Object)} with
     * @return the Argument
     */
    public static Argument of(Object value) {
        return of(value, null);
    }

    /**
     * Bind a vendor-supported object with the given SQL type.
     * @param value the value to call @link {@link PreparedStatement#setObject(int, Object, int)} with
     * @param sqlType the type to bind
     * @return the Argument
     * @see java.sql.Types
     */
    public static Argument of(Object value, Integer sqlType) {
        if (value == null) {
            return new NullArgument(sqlType);
        }
        return new ObjectArgument(value, sqlType);
    }

    @Override
    public void apply(final int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        if (value == null) { // remove this clause when constructor is no longer public
            statement.setNull(position, sqlType); // lgtm[java/dereferenced-value-may-be-null] - guarded by constructor check
        } else {
            if (sqlType == null) {
                statement.setObject(position, value);
            } else {
                statement.setObject(position, value, sqlType);
            }
        }
    }

    @Override
    public String toString() {
        return (value == null ? "NULL" : String.valueOf(value))
            + (sqlType == null ? "" : " (type " + sqlType + ")");
    }
}
