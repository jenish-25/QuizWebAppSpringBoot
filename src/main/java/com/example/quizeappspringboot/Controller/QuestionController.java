package com.example.quizeappspringboot.Controller;

import com.example.quizeappspringboot.Model.Question;
import com.example.quizeappspringboot.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
//    @GetMapping("allQuestions")
//    public List<Question> getAllQuestions(){
//        return questionService.getAllQuestions();
//    }
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);

    }
    @PostMapping("addQuestion")
    public Question addQuestions(@RequestBody Question question){
       return questionService.addQuestions(question);
    }
}
