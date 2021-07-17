package org.example.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dto.RequestFilter;
import org.example.dto.Response;
import org.example.models.entity.Module;
import org.example.models.repos.ModuleRepo;
import org.example.security.secured.Permission;
import org.example.security.secured.PermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ModuleController {

    private final ModuleRepo moduleRepo;

    @Autowired
    public ModuleController(ModuleRepo moduleRepo) {
        this.moduleRepo = moduleRepo;
    }

    @Permission(permission = { PermissionEnum.READ }, entity = "MODULE")
    @PostMapping("/module/query")
    public ResponseEntity<Response<Object, Module>> queryModule(@RequestBody RequestFilter request){
        Response<Object, Module> response = new Response<>();
        response.setRequest(request);
        response.setData(moduleRepo.findAll());
        return ResponseEntity.ok(response);
    }



}
