package shop.app.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OptimizedSchedulerCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.parseBoolean(context.getEnvironment().getProperty("app.scheduling.enable"))
                && Boolean.parseBoolean(context.getEnvironment().getProperty("app.scheduling.optimization"));
    }
}
