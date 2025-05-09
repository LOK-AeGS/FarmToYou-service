package com.Dankook.FarmToYou.controller;

import com.Dankook.FarmToYou.data.dto.SeedEditInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Dankook.FarmToYou.data.dto.request.CompanyAllSeedRequest;
import com.Dankook.FarmToYou.data.dto.request.CompanyProfileRequest;
import com.Dankook.FarmToYou.data.dto.request.CompanySeedRegister;
import com.Dankook.FarmToYou.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/profile")
    public ResponseEntity<Object> companyProfile(@RequestBody CompanyProfileRequest request) {
        return companyService.companyProfile(request);
    }

    @PostMapping("/seed/register")
    public ResponseEntity<Object> companySeedRegister(@RequestBody CompanySeedRegister request) {
        return companyService.companySeedRegister(request);
    }

    @PostMapping("/seed/all")
    public ResponseEntity<Object> companyAllSeed(@RequestBody CompanyAllSeedRequest request) {
        return companyService.companyAllSeed(request);
    }

    @PostMapping("/seed/add")
    public ResponseEntity<Object> addNewSeed(@RequestBody CompanySeedRegister request) {
        return companyService.addNewSeed(request);
    }

    @PostMapping("/seed/prev/list")
    public ResponseEntity<Object> getCompanyPrevRegSeedList(@RequestBody CompanyProfileRequest request) {
        return companyService.getCompanyPrevRegSeedList(request);
    }

    @GetMapping("/seed/prev/detail/{seedId}")
    public ResponseEntity<Object> getCompanyPrevRegSeedDetail(@PathVariable String seedId) {
        return companyService.getCompanyPrevRegSeedDetail(seedId);
    }

    @GetMapping("/seed/edit/{seedId}")
    public ResponseEntity<Object> editSeed(@PathVariable String seedId) {
        return companyService.editSeed(seedId);
    }

    @PostMapping("/seed/edit/detail/{seedId}")
    public ResponseEntity<Object> editDetail(@PathVariable String seedId,
                                             @RequestBody SeedEditInfo seedEditInfo){
        return companyService.editDetailSeedInfo(seedId, seedEditInfo);
    }

    @GetMapping("/seed/list/{companyId}")
    public ResponseEntity<Object> getList(@PathVariable String companyId) {
        return companyService.getList(companyId);
    }
    @GetMapping("/seed/transaction/{companyId}")
    public ResponseEntity<Object> getTransaction(@PathVariable String companyId) {
        return companyService.getTransaction(companyId);
    }
    @GetMapping("/seed/transaction/list/{companyId}")
    public ResponseEntity<Object> getTransactionList(@PathVariable String companyId) {
        return companyService.getTransactionList(companyId);
    }
    @GetMapping("/seed/sale/dashboard/{companyId}")
    public ResponseEntity<Object> getSaleDashBoard(@PathVariable String companyId) {
        return companyService.getSaleDashBoard(companyId);
    }
    @GetMapping("/seed/category/dashboard/{companyId}")
    public ResponseEntity<Object> getCategoryDashBoard(@PathVariable String companyId) {
        return companyService.getCategoryDashBoard(companyId);
    }

} 