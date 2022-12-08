package com.example.yquizizz.challenge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Challenge implements Serializable {

    private ArrayList<Quiz> quizList = new ArrayList<>();
    private Integer numberOfQuestion;
    private Integer totalPointOfChallenge = 0;

    public Challenge(ArrayList<Quiz> quizData) {

        Random generator = new Random();

        ArrayList<Integer> index = new ArrayList<>();

        while (index.size() < 5) {
            Integer c = generator.nextInt(quizData.size());
            if (index.contains(c)) continue;
            else index.add(c);
        }

        for (Integer i : index) {
            this.quizList.add(quizData.get(i));
            switch (quizData.get(i).getDifficulty()) {
                case "Easy":
                    totalPointOfChallenge += 100;
                    break;
                case "Normal":
                    totalPointOfChallenge += 120;
                    break;
                case "Hard":
                    totalPointOfChallenge += 150;
                    break;
                case "Nightmare":
                    totalPointOfChallenge += 200;
                    break;
            }
        }

        this.numberOfQuestion = index.size();
    }

    public Quiz getQuizByIndex(Integer index) {
        return quizList.get(index);
    }

    public Integer getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public Integer getTotalPointOfChallenge() {
        return totalPointOfChallenge;
    }

}
