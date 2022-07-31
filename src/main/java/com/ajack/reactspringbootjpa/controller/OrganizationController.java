package com.ajack.reactspringbootjpa.controller;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OrganizationController
{
    private final OrganizationService organizationService;

    public OrganizationController(
        OrganizationService organizationService)
    {
        this.organizationService = organizationService;
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationApi>> getOrganizations()
    {
        log.debug("Enter OrganizationController.getOrganizations;");

        return ResponseEntity.ok(organizationService.getOrganizations());
    }
}
