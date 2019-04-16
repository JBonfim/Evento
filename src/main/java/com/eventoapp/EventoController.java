package com.eventoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository cr;
	
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
	
	@RequestMapping(value="/{codigo}",method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
		Evento evento = eventoRepository.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		
		return mv;
	}
	@RequestMapping(value="/{codigo}",method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo,Convidado convidado){
		Evento evento = eventoRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		
		return "redirect:/{codigo}";
	}
}
