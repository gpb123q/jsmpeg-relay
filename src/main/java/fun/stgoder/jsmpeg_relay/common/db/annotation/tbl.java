package fun.stgoder.jsmpeg_relay.common.db.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface tbl {

    public String value() default "";
}
