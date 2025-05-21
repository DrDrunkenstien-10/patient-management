package com.adminservice.systemadmin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.systemadmin.model.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, UUID> {}

