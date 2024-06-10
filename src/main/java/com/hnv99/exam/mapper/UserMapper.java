package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.model.form.count.ClassCountResult;
import com.hnv99.exam.model.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    Integer removeUserGrade(List<Integer> userIds);

    /**
     * Batch add users.
     *
     * @param list List of users
     * @return Number of affected records
     */
    Integer insertBatchUser(List<User> list);

    /**
     * Get user information.
     *
     * @param userId User ID
     * @return Response
     */
    UserVO info(Integer userId);

    /**
     * Get student IDs of a class by class ID.
     *
     * @param classId Class ID
     * @return Result set
     */
    List<Integer> selectIdsByClassId(Integer classId);

    /**
     * Paginate user information.
     *
     * @param page     Page information
     * @param gradeId  Class ID
     * @param realName Real name
     * @param userId   User ID
     * @param roleId   Role ID
     * @return Query result set
     */
    IPage<UserVO> pagingUser(IPage<UserVO> page, Integer gradeId, String realName, Integer userId, Integer roleId);

    /**
     * Remove grades.
     *
     * @param gradeIds List of grade IDs
     * @return Number of affected database records
     */
    Integer removeGradeIdByGradeIds(List<Integer> gradeIds);

    /**
     * Count and group by grade and role ID.
     *
     * @param roleId Role ID
     * @return Result set
     */
    List<ClassCountResult> countAndGroupByGradeAndRoleId(Integer roleId);

    Integer deleteByUserIds(List<Integer> userIds);

}
