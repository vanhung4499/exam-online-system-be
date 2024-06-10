package com.hnv99.exam.controller;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.vo.repo.RepoListVO;
import com.hnv99.exam.model.vo.repo.RepoVO;
import com.hnv99.exam.service.RepoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/repo")
public class RepoController {

    @Resource
    private RepoService repoService;

    /**
     * Add a repository, only teachers and administrators can add repositories
     * @param repo Parameters for adding repositories
     * @return Response result
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addRepo(@Validated @RequestBody Repo repo) {
        // Get user id from token and put it into the creator id attribute
        return repoService.addRepo(repo);
    }


    /**
     * Update a repository
     * @param repo Parameters to be passed
     * @return Response
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateRepo(@Validated @RequestBody Repo repo, @PathVariable("id") Integer id) {
        return repoService.updateRepo(repo, id);
    }

    /**
     * Delete repository by repository id
     * @param id Repository id
     * @return Response result
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteRepoById(@PathVariable("id") Integer id) {
        return repoService.deleteRepoById(id);
    }

    /**
     * Get repository id and repository name, teachers get their own repositories, administrators get all repositories
     * @param repoTitle Repository name
     * @return Response result
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<RepoListVO>> getRepoList(@RequestParam(value = "repoTitle",required = false) String repoTitle) {
        return repoService.getRepoList(repoTitle);
    }

    /**
     * Paginate to query repositories
     * @param pageNum  Page number
     * @param pageSize Records per page
     * @param title    Repository name
     * @return Response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<RepoVO>> pagingRepo(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "title", required = false) String title) {
        return repoService.pagingRepo(pageNum, pageSize, title);
    }
}

