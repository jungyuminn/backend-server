package club.gach_dong.annotation;

import club.gach_dong.domain.ClubAdminRole;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdminAuthorizationCheck {
    ClubAdminRole[] role();
}
