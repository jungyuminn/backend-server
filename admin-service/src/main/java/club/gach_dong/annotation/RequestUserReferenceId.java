package club.gach_dong.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.Parameter;

@Parameter(hidden = true)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
public @interface RequestUserReferenceId {
}
