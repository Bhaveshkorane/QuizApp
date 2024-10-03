package com.bhavesh.quizApp.service;

import com.bhavesh.quizApp.dao.QuestionDao;
import com.bhavesh.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success! added question",HttpStatus.CREATED);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>("failed to create",HttpStatus.BAD_REQUEST);
    }

    public String deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return "deleted ";
    }

    public String updateQuestion(Question question) {
        questionDao.save(question);
        return  "updated question";
    }
}
