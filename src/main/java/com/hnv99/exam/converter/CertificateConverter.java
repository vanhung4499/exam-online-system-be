package com.hnv99.exam.converter;


import com.hnv99.exam.model.entity.Certificate;
import com.hnv99.exam.model.form.CertificateForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel="spring")
public interface CertificateConverter {

    @Mappings({
            @Mapping(target = "certificateName",source = "certificateName")
    })
    Certificate formToEntity(CertificateForm certificateForm);
}
