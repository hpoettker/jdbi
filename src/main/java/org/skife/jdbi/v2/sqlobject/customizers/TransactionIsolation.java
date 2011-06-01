package org.skife.jdbi.v2.sqlobject.customizers;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.SQLStatementCustomizer;
import org.skife.jdbi.v2.sqlobject.SQLStatementCustomizerFactory;
import org.skife.jdbi.v2.sqlobject.SQLStatementCustomizingAnnotation;
import org.skife.jdbi.v2.tweak.StatementCustomizer;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Used to specify the transaction isolation level for an object or method (via annotating the method
 * or passing it in as an annotated param). If used on a parameter, the parameter type must be a
 * {@link org.skife.jdbi.v2.TransactionIsolationLevel}
 */
@SQLStatementCustomizingAnnotation(TransactionIsolation.Factory.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionIsolation
{

    TransactionIsolationLevel value() default TransactionIsolationLevel.INVALID_LEVEL;

    static class Factory implements SQLStatementCustomizerFactory {

        public SQLStatementCustomizer createForMethod(Annotation annotation, Class sqlObjectType, Method method)
        {
            return new MyCustomizer(((TransactionIsolation) annotation).value());
        }

        public SQLStatementCustomizer createForType(Annotation annotation, Class sqlObjectType)
        {
            return new MyCustomizer(((TransactionIsolation) annotation).value());
        }

        public SQLStatementCustomizer createForParameter(Annotation annotation, Class sqlObjectType, Method method, Object arg)
        {
            assert arg instanceof TransactionIsolationLevel;
            return new MyCustomizer((TransactionIsolationLevel) arg);
        }
    }

    static class MyCustomizer implements SQLStatementCustomizer {

        private final TransactionIsolationLevel level;

        public MyCustomizer(TransactionIsolationLevel level) {this.level = level;}

        public void apply(SQLStatement q) throws SQLException
        {
            final int initial_level = q.getContext().getConnection().getTransactionIsolation();

            q.addStatementCustomizer(new StatementCustomizer()
            {
                public void beforeExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                    ctx.getConnection().setTransactionIsolation(level.intValue());
                }

                public void afterExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                    ctx.getConnection().setTransactionIsolation(initial_level);
                }
            });
        }
    }
}
