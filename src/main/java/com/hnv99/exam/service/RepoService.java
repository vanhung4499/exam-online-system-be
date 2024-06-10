package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.vo.repo.RepoListVO;
import com.hnv99.exam.model.vo.repo.RepoVO;
import com.hnv99.exam.model.vo.exercise.ExerciseRepoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RepoService extends IService<Repo> {

    /**
     * Add a repository
     *
     * @param repo Parameters
     * @return Result
     */
    Result<String> addRepo(Repo repo);

    /**
     * Modify the repository
     *
     * @param repo Modified content
     * @param id   Repository ID in the path
     * @return Response result
     */
    Result<String> updateRepo(Repo repo, Integer id);

    /**
     * Delete repository by repository ID and clear the repository to which the question belongs
     *
     * @param id Repository ID
     * @return Response result
     */
    Result<String> deleteRepoById(Integer id);

    /**
     * Get your own repository by user ID. Teachers get their own, administrators can get all.
     * @param repoTitle Repository name
     * @return Response result
     */
    Result<List<RepoListVO>> getRepoList(String repoTitle);

    /**
     * Query repositories by page
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @param title    Title
     * @return Response result
     */
    Result<IPage<RepoVO>> pagingRepo(Integer pageNum, Integer pageSize, String title);

    /**
     * Get pageable exercise repositories list
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @param title    Repository name
     * @return Response result
     */
    Result<IPage<ExerciseRepoVO>> getRepo(Integer pageNum, Integer pageSize, String title);
}
