package com.hnv99.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.CertificateConverter;
import com.hnv99.exam.mapper.CertificateMapper;
import com.hnv99.exam.model.entity.Certificate;
import com.hnv99.exam.model.form.CertificateForm;
import com.hnv99.exam.model.vo.certificate.MyCertificateVO;
import com.hnv99.exam.service.CertificateService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, Certificate> implements CertificateService {

    @Resource
    private CertificateMapper certificateMapper;
    @Resource
    private CertificateConverter certificateConverter;

    // Add a certificate
    @Override
    public Result<String> addCertificate(CertificateForm certificateForm) {

        Certificate certificate = certificateConverter.formToEntity(certificateForm);
        // Automatically generate time
        int insertRows = certificateMapper.insert(certificate);

        if (insertRows > 0) {
            return Result.success("Certificate added successfully");
        } else {
            return Result.failed("Failed to add certificate");
        }
    }

    // Method to query paginated certificate information in the implementation class
    @Override
    public Result<IPage<Certificate>> pagingCertificate(Integer pageNum, Integer pageSize, String certificateName,
                                                        String certificationUnit, String image) {

        // Create a page object with the specified page number and page size
        Page<Certificate> page = new Page<>(pageNum, pageSize);

        // Define query conditions using LambdaQueryWrapper
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<Certificate>()
                .like(StringUtils.isNotBlank(certificateName), Certificate::getCertificateName, certificateName)
                .like(StringUtils.isNotBlank(certificationUnit), Certificate::getCertificationUnit, certificationUnit)
                .eq(StringUtils.isNotBlank(image), Certificate::getImage, image)
                .eq(Certificate::getIsDeleted, 0); // Ensure the certificate is not deleted

        // Perform the query and retrieve paginated results
        Page<Certificate> certificatePage = certificateMapper.selectPage(page, wrapper);

        // Return the result as a success response
        return Result.success("Query successful", certificatePage);
    }


    @Override
    @Transactional
    public Result<String> updateCertificate(CertificateForm certificateForm) {

        Certificate certificate = certificateConverter.formToEntity(certificateForm);

        // Call the mapper method to update the certificate
        int affectedRows = certificateMapper.updateById(certificate);

        if (affectedRows > 0) {
            return Result.success("Certificate updated successfully");
        } else {
            return Result.failed("Failed to update certificate");
        }
    }

//    // Update certificate
//    @Override
//    @Transactional
//    public Result<String> updateCertificate(Integer id, CertificateForm certificateForm) {
//        // Validate ID and form are not null
//        if (id == null || certificateForm == null) {
//            return Result.failed("Invalid parameters");
//        }
//
//        // Query existing certificate by ID
//        Certificate existingCertificate = certificateMapper.selectById(id);
//         if (existingCertificate == null) {
//            return Result.failed("Certificate to be updated does not exist");
//        }
//
//        // Update necessary fields
//        existingCertificate.setCertificateName(certificateForm.getCertificateName());
//        existingCertificate.setCertificationNuit(certificateForm.getCertificationNuit());
//
//        // Call the mapper method to update the certificate
//        int affectedRows = certificateMapper.updateById(existingCertificate);
//
//        if (affectedRows > 0) {
//            return Result.success("Certificate updated successfully");
//        } else {
//            return Result.failed("Failed to update certificate");
//        }
//    }

    @Override
    public Result<String> deleteCertificate(Integer id) {
        // Create a LambdaUpdateWrapper to update the isDeleted field to 1 for the specified certificate ID
        LambdaUpdateWrapper<Certificate> certificateLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        certificateLambdaUpdateWrapper.eq(Certificate::getId, id)
                .set(Certificate::getIsDeleted, 1);

        // Perform the update operation
        int affectedRows = certificateMapper.update(certificateLambdaUpdateWrapper);

        // Check if any rows were affected
        if (affectedRows > 0) {
            // Return a success message if deletion was successful
            return Result.success("Certificate deleted successfully");
        } else {
            // Return a failure message if deletion failed
            return Result.failed("Failed to delete certificate");
        }
    }

    @Override
    public Result<IPage<MyCertificateVO>> getMyCertificatePaging(Integer pageNum, Integer pageSize,String examName) {
        Page<MyCertificateVO> myCertificateVOPage = new Page<>();
        myCertificateVOPage = certificateMapper.selectMyCertificate(myCertificateVOPage,pageNum,pageSize, SecurityUtil.getUserId(),examName);
        return Result.success("Query successful", myCertificateVOPage);
    }
}
