package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.Persona;
import hello.PersonaRepository;

@RestController    // Anotación especifica de controlador rest
@RequestMapping(path="/api") // url inicial del controlador
public class PersonaController {
	
	@Autowired 
	private PersonaRepository PersonaRepository;
	
	@GetMapping(path="/guardaPersona") //Creamos por get
	public @ResponseBody String creaPersonaGet(@RequestParam String nombre, @RequestParam String apellido) {		
		Persona n = new Persona();
		n.setNombre(nombre);
		n.setApellido(apellido);
		PersonaRepository.save(n);
		return "Guardado por get";
	}
	
	@PostMapping("/guardaPersona1") //Creamos por post
	public Persona  creaPersonaPost(@RequestBody Persona persona) {
	    PersonaRepository.save(persona);
		return persona;
	}
	
	@GetMapping(path="/listaPersonas") //Traemos todos por get
	public @ResponseBody Iterable<Persona> getListaPersonas() {//Retorna las personas en formato json
		return PersonaRepository.findAll();
	}
	
	@GetMapping("/buscaPersona/{id}") //Traemos solo uno por get
	public Persona buscaPersona(@PathVariable Integer id) throws Exception {
		java.util.Optional<Persona> persona = PersonaRepository.findById(id);
		if (!persona.isPresent())
			throw new Exception("id-" + id);
		return persona.get();
	}
	
	@DeleteMapping("/borrarPersona/{id}")//borramos uno por id
	public void  borrarPersona(@PathVariable Integer id){		
		PersonaRepository.deleteById(id);
		//return "Eliminado con exito";
	}
	
	
	@PutMapping("/actualizaPersona/{id}")
	public ResponseEntity<Object> actualizaPersona1(@RequestBody Persona persona, @PathVariable Integer id) {
		java.util.Optional<Persona> personaOptional = PersonaRepository.findById(id);
		if (!personaOptional.isPresent())
			return ResponseEntity.notFound().build();
		persona.setId(id);
		PersonaRepository.save(persona);
		return ResponseEntity.noContent().build();
	}
	
	
//	@PutMapping("/actualizaPersona/{id}")
//	public @ResponseBody String actualizaPersona(@RequestBody Persona persona, @PathVariable Integer id) {
//
//		persona.setId(id);
//		PersonaRepository.save(persona);
//		return"modificado";
//		//return ResponseEntity.noContent().build();
//	}
	
//	
//	@PostMapping("/personas") //creamos por post
//	public ResponseEntity<Object> createStudent(@RequestBody Persona student) {
//		Persona savedStudent = PersonaRepository.save(student);
//
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(savedStudent.getId()).toUri();
//
//		return PersonaRepository.created(location).build();
//
//	}
	
}