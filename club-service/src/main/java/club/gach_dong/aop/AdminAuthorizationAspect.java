package club.gach_dong.aop;

import club.gach_dong.annotation.AdminAuthorizationCheck;
import club.gach_dong.domain.ClubAdminRole;
import club.gach_dong.dto.request.ClubIdentifiable;
import club.gach_dong.exception.ClubException.ClubAdminAccessDeniedException;
import club.gach_dong.service.ClubService;
import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminAuthorizationAspect {

    private final ClubService clubService;

    @Around(value = "@annotation(adminAuthCheck) && args(userReferenceId, clubIdHolder, ..)", argNames = "joinPoint,adminAuthCheck,userReferenceId,clubIdHolder")
    public Object checkAdminAuthorization(
            ProceedingJoinPoint joinPoint,
            AdminAuthorizationCheck adminAuthCheck,
            String userReferenceId,
            ClubIdentifiable clubIdHolder) throws Throwable {

        Long clubId = clubIdHolder.clubId();

        ClubAdminRole[] requiredRoles = adminAuthCheck.role();
        boolean hasRequiredRole = false;

        for (ClubAdminRole role : requiredRoles) {
            if (clubService.hasRoleForClub(userReferenceId, clubId, role)) {
                hasRequiredRole = true;
                break;
            }
        }

        if (hasRequiredRole) {
            return joinPoint.proceed();
        }

        throw new ClubAdminAccessDeniedException();
    }
}