package com.javatechie.feignClient;

import com.javatechie.entity.Laboratory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "labs-service", url = "${laboratory.service.url}")
public interface LaboratoryClient {
    @GetMapping("/labs/{id}")
    Laboratory getLaboratoryById(@PathVariable("id") Long id);
}