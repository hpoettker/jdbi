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
package org.jdbi.v3.sqlobject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to identify SQL operation annotations. Use this to annotate
 * an annotation.
 *
 * @deprecated Use {@link org.jdbi.v3.core.extension.annotation.UseExtensionHandler} and {@link SqlObjectFactory#EXTENSION_ID} as the id marker.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Deprecated(since = "3.38.0", forRemoval = true)
public @interface SqlOperation {
    /**
     * Handler class for methods annotated with the associated annotation.
     * Must have a public no-arg, {@code (Method method)}, or
     * {@code (Class<?> sqlObjectType, Method method)} constructor.
     * @return a handler class
     */
    Class<? extends Handler> value();
}
