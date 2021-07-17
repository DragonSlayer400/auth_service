package org.example.models.repos;

import org.example.models.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepo extends JpaRepository<Module, Long> {
}
