package com.hnv99.exam.model.form.question;

import com.hnv99.exam.model.entity.Option;
import com.hnv99.exam.util.excel.ExcelImport;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class QuestionExcelForm {
    @ExcelImport(value = "Question Type", required = true)
    private Integer quType;
    @ExcelImport(value = "Stem", required = true, unique = true)
    private String content;
    @ExcelImport(value = "Analysis")
    private String analysis;
    @ExcelImport(value = "Option 1 Content")
    private String option1;
    @ExcelImport(value = "Is Option 1 Correct")
    private Integer righted1;
    @ExcelImport(value = "Option 2 Content")
    private String option2;
    @ExcelImport(value = "Is Option 2 Correct")
    private Integer righted2;
    @ExcelImport(value = "Option 3 Content")
    private String option3;
    @ExcelImport(value = "Is Option 3 Correct")
    private Integer righted3;
    @ExcelImport(value = "Option 4 Content")
    private String option4;
    @ExcelImport(value = "Is Option 4 Correct")
    private Integer righted4;
    @ExcelImport(value = "Option 5 Content")
    private String option5;
    @ExcelImport(value = "Is Option 5 Correct")
    private Integer righted5;
    @ExcelImport(value = "Option 6 Content")
    private String option6;
    @ExcelImport(value = "Is Option 6 Correct")
    private Integer righted6;

    /**
     * Type conversion
     *
     * @param questionExcelForms List of question form obtained from the table
     * @return Converted result
     */
    public static List<QuestionForm> convertToQuestionForm(List<QuestionExcelForm> questionExcelForms) {
        List<QuestionForm> list = new ArrayList<>(300);
        for (QuestionExcelForm questionExcelForm : questionExcelForms) {
            QuestionForm questionForm = new QuestionForm();
            questionForm.setContent(questionExcelForm.getContent());
            questionForm.setQuType(questionExcelForm.getQuType());
            questionForm.setAnalysis(questionExcelForm.getAnalysis());
            List<Option> options = new ArrayList<>();
            if (questionExcelForm.getOption1() != null && !questionExcelForm.getOption1().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption1());
                option.setIsRight(questionExcelForm.getRighted1());
                options.add(option);
            }
            if (questionExcelForm.getOption2() != null && !questionExcelForm.getOption2().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption2());
                option.setIsRight(questionExcelForm.getRighted2());
                options.add(option);
            }
            if (questionExcelForm.getOption3() != null && !questionExcelForm.getOption3().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption3());
                option.setIsRight(questionExcelForm.getRighted3());
                options.add(option);
            }
            if (questionExcelForm.getOption4() != null && !questionExcelForm.getOption4().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption4());
                option.setIsRight(questionExcelForm.getRighted4());
                options.add(option);
            }
            if (questionExcelForm.getOption5() != null && !questionExcelForm.getOption5().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption5());
                option.setIsRight(questionExcelForm.getRighted5());
                options.add(option);
            }
            if (questionExcelForm.getOption6() != null && !questionExcelForm.getOption6().isEmpty()) {
                Option option = new Option();
                option.setContent(questionExcelForm.getOption6());
                option.setIsRight(questionExcelForm.getRighted6());
                options.add(option);
            }

            questionForm.setOptions(options);
            list.add(questionForm);
        }
        return list;
    }
}
