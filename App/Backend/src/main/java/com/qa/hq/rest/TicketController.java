package com.qa.hq.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.hq.domain.Ticket;
import com.qa.hq.domain.Trainee;
import com.qa.hq.dto.TicketDto;
import com.qa.hq.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/ticket")
public class TicketController {
	
	private TicketService service;

	private TicketController(TicketService service) {
		super();
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<TicketDto> createTicket(@RequestBody Ticket ticket) {
		return new ResponseEntity<TicketDto>(this.service.createTicket(ticket), HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<TicketDto>> getTicket() {
		return ResponseEntity.ok(this.service.getTicket());
	}
	
	@GetMapping("/findById")
	public ResponseEntity<TicketDto> findTicketById(Long id) {
		return ResponseEntity.ok(this.service.findTicketById(id));
	}
	
	@GetMapping("/findByTopic/{topic}")
	public ResponseEntity<List<TicketDto>> findTicketByTopic(@PathVariable String topic) {
		return ResponseEntity.ok(this.service.findTicketByTopic(topic));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Object> deleteTicket(@PathVariable Long id) {
		if (this.service.deleteTicket(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<TicketDto> updateTicket(@RequestBody Ticket ticket, @PathParam("id") Long id) {
		return new ResponseEntity<TicketDto>(this.service.updateTicket(ticket, id), HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/status/{id}")
	public ResponseEntity<TicketDto> updateTicketStatus(@PathVariable Long id) {
		return new ResponseEntity<TicketDto>(this.service.updateTicketStatus(id), HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/joinTicket/{id}")
    public ResponseEntity<TicketDto> addTraineeToTicket(@RequestBody Trainee trainee, @PathVariable Long id) {
    return new ResponseEntity<TicketDto>(this.service.addTraineeToTicket(id, trainee), HttpStatus.ACCEPTED);
    }
}
