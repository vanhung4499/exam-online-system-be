package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Certificate;
import com.hnv99.exam.model.vo.certificate.MyCertificateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;


@Repository
public interface CertificateMapper extends BaseMapper<Certificate> {

    Page<MyCertificateVO> selectMyCertificate(Page<MyCertificateVO> myCertificateVOPage, Integer pageNum, Integer pageSize, Integer userId, String examName);
}
