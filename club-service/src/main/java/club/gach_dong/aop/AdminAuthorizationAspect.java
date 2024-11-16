package club.gach_dong.aop;

import club.gach_dong.annotation.AdminAuthorizationCheck;
import club.gach_dong.domain.ClubAdminRole;
import club.gach_dong.service.ClubService;
import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
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

    @Around(value = "@annotation(adminAuthCheck) && args(userReferenceId, ..)", argNames = "joinPoint,adminAuthCheck,userReferenceId")
    public Object checkAdminAuthorization(
            ProceedingJoinPoint joinPoint,
            AdminAuthorizationCheck adminAuthCheck,
            String userReferenceId) throws Throwable {

        Object[] args = joinPoint.getArgs();


        Object requestDto = args[1];
        Long clubId = extractClubIdFromDto(requestDto);

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

        throw new AccessDeniedException("사용자가 클럽에 필요한 역할을 가지고 있지 않습니다.");

    }

    private Long extractClubIdFromDto(Object requestDto) throws NoSuchFieldException, IllegalAccessException {
        Field clubIdField = requestDto.getClass().getDeclaredField("clubId");
        return (Long) clubIdField.get(requestDto);
    }
}