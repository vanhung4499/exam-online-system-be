package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.vo.repo.RepoListVO;
import com.hnv99.exam.model.vo.repo.RepoVO;
import com.hnv99.exam.model.vo.exercise.ExerciseRepoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoMapper extends BaseMapper<Repo> {

    /**
     * Paginate and query repositories.
     *
     * @param page   Pagination information
     * @param title  Repository name
     * @param userId User ID
     * @return Response result
     */
    IPage<RepoVO> pagingRepo(@Param("page") IPage<RepoVO> page, @Param("title") String title,
                             @Param("userId") Integer userId);

    /**
     * Delete repositories created by users.
     *
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Paginate and retrieve repositories available for practice.
     *
     * @param page  Pagination information
     * @param title Repository name
     * @return Result set
     */
    IPage<ExerciseRepoVO> selectRepo(IPage<ExerciseRepoVO> page, String title);

    List<RepoListVO> selectRepoList(String repoTitle, int userId);
}
