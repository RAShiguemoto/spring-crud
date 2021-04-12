package com.ras.springcrud.controller;

import com.ras.springcrud.model.Carro;
import com.ras.springcrud.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarroController {
    @Autowired
    CarroRepository carroRepository;

    @GetMapping("/carros/list")
    public ModelAndView list() {
        List<Carro> carros = this.carroRepository.findAll();
        ModelAndView mv = new ModelAndView("carros/list");
        mv.addObject("carros", carros);
        return mv;
    }

    @GetMapping("/carros/novo")
    public ModelAndView novo(Carro carro) {
        ModelAndView mv = new ModelAndView("carros/novo");
        return mv;
    }

    @PostMapping("/carros/salvar")
    public ModelAndView salvar(@Valid Carro carro, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("carros/novo");
            return mv;
        } else {
            this.carroRepository.save(carro);
            return new ModelAndView("redirect:/carros/list");
        }
    }
}
