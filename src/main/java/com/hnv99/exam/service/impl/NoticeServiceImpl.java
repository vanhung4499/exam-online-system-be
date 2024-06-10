package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.NoticeConverter;
import com.hnv99.exam.mapper.GradeMapper;
import com.hnv99.exam.mapper.NoticeGradeMapper;
import com.hnv99.exam.mapper.NoticeMapper;
import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.entity.Notice;
import com.hnv99.exam.model.form.NoticeForm;
import com.hnv99.exam.model.vo.NoticeVO;
import com.hnv99.exam.service.NoticeService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private NoticeConverter noticeConverter;

    @Resource
    private NoticeGradeMapper noticeGradeMapper;

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public Result<String> addNotice(NoticeForm noticeForm) {
        // Set creator
        noticeForm.setUserId(SecurityUtil.getUserId());
        // Add notice
        Notice notice = noticeConverter.formToEntity(noticeForm);
        int rowsAffected = noticeMapper.insert(notice);

        LambdaQueryWrapper<Grade> gradeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        gradeLambdaQueryWrapper.eq(Grade::getUserId,SecurityUtil.getUserId());
        List<Grade> grades = gradeMapper.selectList(gradeLambdaQueryWrapper);
        Integer noticeId = notice.getId();
        // grades.forEach(r->{
        //     System.out.println(r);
        // });
//        System.out.println(noticeId);
        int addNoticeGradeRow = noticeGradeMapper.addNoticeGrade(noticeId,grades);
        if (addNoticeGradeRow == 0) {
            return Result.failed("Failed to add notice");
        }
        return Result.success("Notice added successfully");
    }

    @Override
    public Result<String> deleteNotice(String ids) {
        // Convert to list
        List<Integer> noticeIds = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .toList();
        // Delete notice
        int rowsAffected = noticeMapper.removeNotice(noticeIds);
        if (rowsAffected == 0) {
            return Result.failed("Failed to delete notice");
        }
        return Result.success("Notice deleted successfully");
    }

    @Override
    public Result<String> updateNotice(String id, NoticeForm noticeForm) {
        // Create update condition
        LambdaUpdateWrapper<Notice> noticeWrapper = new LambdaUpdateWrapper<>();
        noticeWrapper.eq(Notice::getId, id)
                .eq(Notice::getIsDeleted,0)
                .set(Notice::getContent, noticeForm.getContent());
        // Update notice
        int rowsAffected = noticeMapper.update(noticeWrapper);
        if (rowsAffected == 0) {
            return Result.failed("Failed to update notice");
        }
        return Result.success("Notice updated successfully");
    }

    @Override
    public Result<IPage<NoticeVO>> getNotice(Integer pageNum, Integer pageSize, String title) {
        // Create page object
        Page<Notice> page = new Page<>(pageNum, pageSize);
        // Create query conditions
        LambdaQueryWrapper<Notice> noticeQueryWrapper = new LambdaQueryWrapper<>();
        noticeQueryWrapper.like(StringUtils.isNotBlank(title), Notice::getTitle, title)
                .eq(Notice::getUserId, SecurityUtil.getUserId())
                .eq(Notice::getIsDeleted,0);

        // Teacher paginated query for notices
        Page<Notice> gradePage = noticeMapper.selectPage(page, noticeQueryWrapper);
        Page<NoticeVO> noticeVOs = noticeConverter.pageEntityToVo(gradePage);
        return Result.success("Query successful", noticeVOs);
    }

    @Override
    public Result<IPage<NoticeVO>> getNewNotice(Integer pageNum, Integer pageSize) {
        // Create page object
        Page<NoticeVO> page = new Page<>(pageNum, pageSize);
        // Create query conditions

        // Student paginated query for notices
        page = noticeMapper.selectNewNoticePage(page, SecurityUtil.getUserId());
        return Result.success("Query successful", page);
    }
}
