package com.hnv99.exam.model.vo.score;

import com.hnv99.exam.util.excel.ExcelExport;
import lombok.Data;

@Data
public class ExportScoreVO {

    @ExcelExport("Name")
    private String realName;

    @ExcelExport("Grade")
    private String gradeName;

    @ExcelExport("Score")
    private Double score;

    @ExcelExport(value = "Ranking", sort = 1)
    private Integer ranking;
}
