package com.eventoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.GET)
	public String form(){
		return "evento/formEvento";
	}
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.POST)
	public String form(Evento ev){
		eventoRepository.save(ev);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView ListaEventos(){
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
		
		
	}
}
