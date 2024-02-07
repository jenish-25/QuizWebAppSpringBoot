package com.example.quizeappspringboot.Service;

import com.example.quizeappspringboot.DAO.QuestionDao;
import com.example.quizeappspringboot.DAO.QuizDao;
import com.example.quizeappspringboot.Model.Question;
import com.example.quizeappspringboot.Model.QuestionWrapper;
import com.example.quizeappspringboot.Model.Quiz;
import com.example.quizeappspringboot.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("SUCCESS ", HttpStatus.CREATED);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuizquestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDb=quiz.get().getQuestions();
        List<QuestionWrapper> questionFromUser=new ArrayList<>();
        for(Question q: questionsFromDb){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionFromUser.add(qw);
        }
        return new ResponseEntity<>(questionFromUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz=quizDao.findById(id).get();
        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for (Response response: responses){
            if(response.getResponse().equals(questions.get(i).getCorrectAnswer()))
                right++;

            i++;
        }
        return  new ResponseEntity<>(right,HttpStatus.OK);
    }
}
