package com.hnv99.exam.controller;


import com.hnv99.exam.common.group.CertificateGroup;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Certificate;
import com.hnv99.exam.model.form.CertificateForm;
import com.hnv99.exam.model.vo.certificate.MyCertificateVO;
import com.hnv99.exam.service.CertificateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Resource
    private CertificateService iCertificateService;

    /**
     * Add certificate, only teachers and administrators can add certificates
     * @param certificateForm frontend parameters for adding certificates
     * @return response result
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addCertificate(@RequestBody @Validated(CertificateGroup.CertificateInsertGroup.class)
                                         CertificateForm certificateForm) {
        // Get user ID from token and set it as the creator ID
        return iCertificateService.addCertificate(certificateForm);
    }

    /**
     * Paginate certificates
     * @param pageNum page number
     * @param pageSize number of records per page
     * @param certificateName certificate name
     * @param certificationUnit certification unit
     * @param image certificate background image
     * @return response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<Certificate>> pagingCertificate(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "certificateName", required = false) String certificateName,
                                                        @RequestParam(value = "certificationUnit", required = false) String certificationUnit,
                                                        @RequestParam(value = "image", required = false) String image) {
        return iCertificateService.pagingCertificate(pageNum, pageSize, certificateName, certificationUnit, image);
    }

    /**
     * Update certificate
     * @param id id
     * @param certificateForm frontend parameters for modifying certificates
     * @return response result
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateCertificate(@PathVariable("id") Integer id,
                                            @RequestBody CertificateForm certificateForm) {
        certificateForm.setId(id);
        return iCertificateService.updateCertificate(certificateForm);
    }

    /**
     * Delete certificate
     * @param id id
     * @return response result
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteCertificate(@PathVariable("id") Integer id) {
        return iCertificateService.deleteCertificate(id);
    }

    /**
     * Paginate acquired certificates
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/paging/my")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<MyCertificateVO>>  getMyCertificate(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                                                            @RequestParam(value = "examName", required = false) String examName){
        return iCertificateService.getMyCertificatePaging(pageNum, pageSize,examName);
    }
}
