package com.bhavesh.quizApp.service;

import com.bhavesh.quizApp.dao.QuestionDao;
import com.bhavesh.quizApp.dao.QuizDao;
import com.bhavesh.quizApp.model.Question;
import com.bhavesh.quizApp.model.QuestionWrapper;
import com.bhavesh.quizApp.model.Quiz;
import com.bhavesh.quizApp.model.Response;
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

        try {
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);


            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        try {

            List<Question> questionFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for (Question q : questionFromDB) {

                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Integer> calcualateResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Question> questions = quiz.getQuestions();

            int right = 0;
            int i = 0;

            for (Response response : responses) {
                    if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                        right++;

                    }
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);

    }
}
