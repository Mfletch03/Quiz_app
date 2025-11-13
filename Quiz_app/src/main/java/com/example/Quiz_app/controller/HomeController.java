package com.example.Quiz_app.controller;

import com.example.Quiz_app.model.User;
import com.example.Quiz_app.model.QuizResult;
import com.example.Quiz_app.repository.UserRepository;
import com.example.Quiz_app.repository.QuizResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    private final Map<Integer, String> categoryNames = Map.of(
            18, "Computers",
            15, "Video Games",
            12, "Music",
            10, "Books",
            27, "Animals",
            21, "Sports",
            11, "Film",
            9, "General Knowledge"
    );



    // Home page — shows logged in user's name
    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "home";
    }

    // Fetches quiz questions from the API and shows quiz page
    @GetMapping("/startQuiz")
    public String startQuiz(@RequestParam int category,
                            @RequestParam int amount,
                            Model model) {

        String apiUrl = "https://opentdb.com/api.php?amount=" + amount +
                        "&category=" + category + "&type=multiple";

        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(apiUrl, Map.class);

        List<Map<String, Object>> questions =
                (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> question : questions) {
            String correctAnswer = (String) question.get("correct_answer");
            List<String> incorrectAnswers =
                    (List<String>) question.get("incorrect_answers");

            List<String> allAnswers = new ArrayList<>(incorrectAnswers);
            allAnswers.add(correctAnswer);
            Collections.shuffle(allAnswers);

            question.put("all_answers", allAnswers);
        }

        model.addAttribute("questions", questions);
        model.addAttribute("category", category);
        model.addAttribute("amount", amount);
        model.addAttribute("categoryName", categoryNames.get(category));


        return "quizPage";  // quizPage.html
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(
            @RequestParam Map<String, String> params,
            Model model,
            Authentication authentication) {

        // Extract submitted answers
        List<String> userAnswers = new ArrayList<>();
        List<String> correctAnswers = new ArrayList<>();
        List<String> questionTexts = new ArrayList<>();

        int i = 0;
        while (params.containsKey("answers[" + i + "]")) {
            userAnswers.add(params.get("answers[" + i + "]"));
            correctAnswers.add(params.get("correct[" + i + "]"));
            questionTexts.add(params.get("questionText[" + i + "]"));
            i++;
        }

        int total = questionTexts.size();
        int correctCount = 0;
        List<Map<String, String>> resultsList = new ArrayList<>();

        for (int j = 0; j < total; j++) {

            String userAnswer = userAnswers.get(j);
            String correctAnswer = correctAnswers.get(j);
            boolean isCorrect = userAnswer.equals(correctAnswer);

            if (isCorrect) correctCount++;

            Map<String, String> result = new HashMap<>();
            result.put("question", questionTexts.get(j));
            result.put("userAnswer", userAnswer);
            result.put("correctAnswer", correctAnswer);
            result.put("isCorrect", Boolean.toString(isCorrect));
            resultsList.add(result);
        }

        double percentage = ((double) correctCount / total) * 100;

        // Save to database
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        String category = params.get("categoryName");

        QuizResult qr = new QuizResult(category, correctCount, total, percentage, user);
        quizResultRepository.save(qr);

        // Send to result page
        model.addAttribute("results", resultsList);
        model.addAttribute("score", correctCount);
        model.addAttribute("total", total);
        model.addAttribute("percentage", percentage);

        return "result";
    }


    // History Page
    @GetMapping("/history")
    public String showHistory(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        List<QuizResult> history = quizResultRepository.findByUserOrderByDateTakenDesc(user);
        model.addAttribute("historyList", history);
        return "history";
    }

    @PostMapping("/history/delete/{id}")
    public String deleteHistoryItem(@PathVariable Long id, Authentication auth) {

        QuizResult result = quizResultRepository.findById(id).orElse(null);

        // Extra safety — only allow owner to delete
        if (result != null && result.getUser().getUsername().equals(auth.getName())) {
            quizResultRepository.delete(result);
        }

        return "redirect:/history";
}



}


