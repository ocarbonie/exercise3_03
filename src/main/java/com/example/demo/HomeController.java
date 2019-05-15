package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ListRepository listRepository;

    @RequestMapping("/")
    public String listCourses(Model model){
        model.addAttribute("todos", listRepository.findAll());
        return "list";

    }

    @GetMapping("/add")
    public String listForm(Model model){
        model.addAttribute("todo", new TodoList());
        return "todoform";
    }

    @PostMapping("/process")
    public String processForm(@Valid TodoList todo, BindingResult result, Model model){
        model.addAttribute("todo", todo);
        if(result.hasErrors()){
            return "todoform";
        }
       listRepository.save(todo);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showTodoList(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("todo", listRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateList(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("todo", listRepository.findById(id).get());
        return "todoform";
    }
    @RequestMapping("/delete/{id}")
    public String delToDo(@PathVariable("id") long id)
    {
        listRepository.deleteById(id);
        return "redirect:/";
    }
}