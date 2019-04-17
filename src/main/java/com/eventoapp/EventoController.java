package com.eventoapp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form(@Valid Evento ev,BindingResult result,RedirectAttributes attributes){
		if (result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos.");
			return "redirect:/cadastrarEvento";
		}
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso.");
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
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	@RequestMapping(value="/{codigo}",method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo,@Valid Convidado convidado, BindingResult result,RedirectAttributes attributes){
		
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		
		Evento evento = eventoRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado Salvo com Sucesso!");
		
		return "redirect:/{codigo}";
	}
}
