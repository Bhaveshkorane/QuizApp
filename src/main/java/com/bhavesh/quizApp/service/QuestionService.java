package com.bhavesh.quizApp.service;

import com.bhavesh.quizApp.dao.QuestionDao;
import com.bhavesh.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions()
    {
       return questionDao.findAll();
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDao.getQeustionByCategory(category);
    }
}
