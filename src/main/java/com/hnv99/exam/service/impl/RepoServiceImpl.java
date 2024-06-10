package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.RepoConverter;
import com.hnv99.exam.mapper.QuestionMapper;
import com.hnv99.exam.mapper.RepoMapper;
import com.hnv99.exam.mapper.UserExerciseRecordMapper;
import com.hnv99.exam.model.entity.Question;
import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.entity.UserExerciseRecord;
import com.hnv99.exam.model.vo.repo.RepoListVO;
import com.hnv99.exam.model.vo.repo.RepoVO;
import com.hnv99.exam.model.vo.exercise.ExerciseRepoVO;
import com.hnv99.exam.service.RepoService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepoServiceImpl extends ServiceImpl<RepoMapper, Repo> implements RepoService {

    @Resource
    private RepoMapper repoMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private RepoConverter repoConverter;
    @Resource
    private UserExerciseRecordMapper userExerciseRecordMapper;

    @Override
    public Result<String> addRepo(Repo repo) {
        repoMapper.insert(repo);
        return Result.success("Successfully saved");
    }

    @Override
    public Result<String> updateRepo(Repo repo, Integer id) {
        LambdaUpdateWrapper<Repo> updateWrapper = new LambdaUpdateWrapper<Repo>()
                .eq(Repo::getId, id).set(Repo::getTitle, repo.getTitle());
        repoMapper.update(updateWrapper);
        return Result.success("Successfully updated");
    }

    @Override
    @Transactional
    public Result<String> deleteRepoById(Integer id) {
        LambdaUpdateWrapper<Question> wrapper = new LambdaUpdateWrapper<Question>()
                .eq(Question::getRepoId, id)
                .set(Question::getRepoId, null);
        questionMapper.update(wrapper);
        LambdaUpdateWrapper<Repo> repoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        repoLambdaUpdateWrapper.eq(Repo::getId,id)
                .set(Repo::getIsDeleted,1);
        int result = repoMapper.update(repoLambdaUpdateWrapper);
        if (result > 0) {
            return Result.success("Successfully deleted");
        }
        return Result.failed("Deletion failed");
    }

    @Override
    public Result<List<RepoListVO>> getRepoList(String repoTitle) {
        List<RepoListVO> list;
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            list = repoMapper.selectRepoList(repoTitle, SecurityUtil.getUserId());
        } else {
            list = repoMapper.selectRepoList(repoTitle, 0);
        }
        return Result.success(null, list);
    }

    @Override
    public Result<IPage<RepoVO>> pagingRepo(Integer pageNum, Integer pageSize, String title) {
        IPage<RepoVO> page = new Page<>(pageNum, pageSize);
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            page = repoMapper.pagingRepo(page, title, SecurityUtil.getUserId());
        } else {
            page = repoMapper.pagingRepo(page, title, 0);
        }
        return Result.success(null, page);
    }

    @Override
    public Result<IPage<ExerciseRepoVO>> getRepo(Integer pageNum, Integer pageSize, String title) {
        IPage<ExerciseRepoVO> page = new Page<>(pageNum, pageSize);
        page = repoMapper.selectRepo(page, title);
        page.getRecords().forEach(repoVO -> {
            int totalCount = questionMapper
                    .selectCount(new LambdaQueryWrapper<Question>().eq(Question::getRepoId, repoVO.getId()))
                    .intValue();
            repoVO.setTotalCount(totalCount);
            LambdaQueryWrapper<UserExerciseRecord> wrapper = new LambdaQueryWrapper<UserExerciseRecord>()
                    .select(UserExerciseRecord::getExerciseCount)
                    .eq(UserExerciseRecord::getUserId, SecurityUtil.getUserId())
                    .eq(UserExerciseRecord::getRepoId, repoVO.getId());
            UserExerciseRecord userExerciseRecord = userExerciseRecordMapper.selectOne(wrapper);
            if (userExerciseRecord != null && userExerciseRecord.getExerciseCount() != 0) {
                repoVO.setExerciseCount(userExerciseRecord.getExerciseCount());
            } else {
                repoVO.setExerciseCount(0);
            }
        });
        return Result.success(null, page);
    }
}
