package com.ras.springcrud.controller;

import com.ras.springcrud.model.Carro;
import com.ras.springcrud.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carros")
public class CarroController {
    @Autowired
    CarroRepository carroRepository;

    @GetMapping("/list")
    public ModelAndView list() {
        List<Carro> carros = this.carroRepository.findAll();
        ModelAndView mv = new ModelAndView("carros/list");
        mv.addObject("carros", carros);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(Carro carro) {
        ModelAndView mv = new ModelAndView("carros/novo");
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Carro carro, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("carros/novo");
            return mv;
        } else {
            this.carroRepository.save(carro);
            return new ModelAndView("redirect:/carros/list");
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<Carro> op = this.carroRepository.findById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("carros/edit");
            mv.addObject("carro", op.get());
            return mv;

        } else {
            System.out.println("*** Nenhum registro encontrado!");
            ModelAndView mv = new ModelAndView("redirect:/carros/list");
            return mv;
        }
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid Carro carro, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("carros/edit");
            return mv;
        } else {
            System.out.println("*** Id: " + id);
            carro.setId(id);
            this.carroRepository.save(carro);
            return new ModelAndView("redirect:/carros/list");
        }
    }
}
