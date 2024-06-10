package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Certificate;
import com.hnv99.exam.model.form.CertificateForm;
import com.hnv99.exam.model.vo.certificate.MyCertificateVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Service interface for handling certificates
 * </p>
 */
public interface CertificateService extends IService<Certificate> {

    /**
     * Add a certificate
     * @param certificateForm Parameters for adding a certificate
     * @return Response result, usually containing operation status and possible information
     */
    Result<String> addCertificate(CertificateForm certificateForm);

    /**
     * Page query certificates
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param certificateName Certificate name
     * @param certificationUnit Certification unit
     * @param image Certificate background image
     * @return Response containing the paginated certificate list information
     */
    Result<IPage<Certificate>> pagingCertificate(Integer pageNum, Integer pageSize, String certificateName, String certificationUnit, String image);

    /**
     * Update a certificate
     * @param certificateForm Parameters for updating a certificate
     * @return Response result
     */
    Result<String> updateCertificate(CertificateForm certificateForm);

    /**
     * Delete a certificate
     * @param id ID of the certificate to be deleted
     * @return Response result
     */
    Result<String> deleteCertificate(Integer id);

    /**
     * Get paginated user certificates
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param examName Exam name
     * @return Response containing the paginated user certificate list information
     */
    Result<IPage<MyCertificateVO>> getMyCertificatePaging(Integer pageNum, Integer pageSize, String examName);
}
