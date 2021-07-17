package org.example.security.secured;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.dto.Error;
import org.example.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@ResponseBody
@Slf4j
public class PermissionAspect {


    private final String permissionPrefix = "PERMISSION_";
    private final String permissionAll = permissionPrefix + "_%s_ALL";
    private final String messageError = "Недостачно прав на данную операцию. Для совершения данную операцию Вам необходимо получить права %s";


    @Around(value = "@annotation(permission) && args(@RequestBody request)")
    public ResponseEntity<?> permissionCheck(ProceedingJoinPoint joinPoint, Permission permission, Object request) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Проверка прав пользователя '{}'", authentication.getName());
        log.info("Получение разрешений пользователя '{}'", authentication.getName());
        Set<String> permissionUser = authentication.getAuthorities().stream().map(s -> s.getAuthority()).collect(Collectors.toSet());
        log.info("Разрешения пользователя '{}' : {}", authentication.getName(), permissionUser);

        List<Error> errors = new ArrayList<>();

        if(
            !permissionUser.contains(String.format(permissionAll, permission.entity().toUpperCase()))
        ){
            for(PermissionEnum perm : permission.permission()){
                String permissionCode = permissionPrefix + permission.entity().toUpperCase() + "_" + perm.name();
                if(!permissionUser.contains(permissionCode))
                {
                    errors.add(new Error(String.format(messageError, permissionCode)));
                }
            }
        }

        if(errors.size() > 0){
            Response<Object, ?> response = new Response<>();
            response.setRequest(request);
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return (ResponseEntity<?>) joinPoint.proceed();
    }

}
