package com.qa.hq.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.hq.domain.Ticket;
import com.qa.hq.domain.TicketRepo;
import com.qa.hq.domain.Trainee;
import com.qa.hq.dto.TicketDto;
import com.qa.hq.service.TicketService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class TicketUnitTest {
	
	@Autowired
	private TicketService tService;
	
	@MockBean
	private TicketRepo tRepo;
	

	@Test
	void testCreateTicket() {
		long ID = 1;
		Date date = new Date(1608026491L);
		Ticket newTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		Ticket savedTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		savedTicket.setId(ID);
		
		// When the function is called to save the Trainee
		Mockito.when(this.tRepo.save(newTicket)).thenReturn(savedTicket);	
	
		// Then check that the Trainee is saved correctly.
		assertThat(this.tService.createTicket(newTicket)).isEqualTo(new TicketDto(savedTicket));
	
		Mockito.verify(this.tRepo,Mockito.times(1)).save(newTicket);
	}
	
	@Test
	void testGetTicket() {
		// Given there are two Trainees, with ID's 1 & 2
				long ID = 1;
				Date date = new Date(1608026491L);
				Ticket Ticket1 = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
				Ticket1.setId(ID++);
				date = new Date(1608026584L);
				Ticket Ticket2 = new Ticket("Another Dummy Ticket", "This Ticket is another Dummy Ticket for Testing", "Testing", date, 3, "Open", null, null);
				Ticket2.setId(ID);
				
				List<Ticket> TicketList = new ArrayList<>();
				TicketList.add(Ticket1);
				TicketList.add(Ticket2);
					
				// When the function is called to get all the Trainees
				Mockito.when(this.tRepo.findAll()).thenReturn(TicketList);
				
				// Then check that the Trainees are all there.
				assertThat(this.tService.getTicket()).isEqualTo(TicketList.stream().map(TicketDto::new).collect(Collectors.toList()));

				Mockito.verify(this.tRepo, Mockito.times(1)).findAll();
	}
	
	@Test
	void testFindTicketById() {
		long ID = 1;
		Date date = new Date(1608026491L);
		Ticket Ticket1 = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		Ticket1.setId(ID++);
		date = new Date(1608026584L);
		Ticket Ticket2 = new Ticket("Another Dummy Ticket", "This Ticket is another Dummy Ticket for Testing", "Testing", date, 3, "Open", null, null);
		Ticket2.setId(ID++);
		date = new Date(1608026632L);
		Ticket Ticket3 = new Ticket("Yet Another Dummy Ticket", "This Ticket is also another Dummy Ticket for Testing", "Testing", date, 3, "Open", null, null);
		Ticket3.setId(ID);
		
		List<Ticket> TicketList = new ArrayList<>();
		TicketList.add(Ticket1);
		TicketList.add(Ticket2);
		TicketList.add(Ticket3);
			
		// When the function is called to get the 2nd Trainee
		Mockito.when(this.tRepo.findById(2L)).thenReturn(Optional.of(TicketList.get(1)));
		
		// Then check that the correct Trainee is retrieved
		assertThat(this.tService.findTicketById(2L)).isEqualTo(new TicketDto(Ticket2));
		Mockito.verify(this.tRepo, Mockito.times(1)).findById(2L);	
	}

	@Test
	void testFindTicketByStatus() {
		long ID = 1;
		Date date = new Date(1608026491L);
		Ticket Ticket1 = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		Ticket1.setId(ID++);
		date = new Date(1608026584L);
		Ticket Ticket2 = new Ticket("Another Dummy Ticket", "This Ticket is another Dummy Ticket for Testing", "Testing", date, 3, "Closed", null, null);
		Ticket2.setId(ID++);
		date = new Date(1608026632L);
		Ticket Ticket3 = new Ticket("Yet Another Dummy Ticket", "This Ticket is also another Dummy Ticket for Testing", "Don't see this test!", date, 3, "Open", null, null);
		Ticket3.setId(ID);
		
		List<Ticket> ReturnList = new ArrayList<>();
		ReturnList.add(Ticket1);
		ReturnList.add(Ticket3);
			
		// When the function is called to get the 2nd Ticket
		Mockito.when(this.tRepo.findByStatus("Open")).thenReturn(ReturnList);
		
		// Then check that the correct Ticket is retrieved
		assertThat(this.tService.findTicketByStatus("Open")).isEqualTo(ReturnList.stream().map(TicketDto::new).collect(Collectors.toList()));
		Mockito.verify(this.tRepo, Mockito.times(1)).findByStatus("Open");
	}

	@Test
	void testUpdateTicketStatus() {
		long ID = 1;
		Date date = new Date(1608026491L);
		Ticket oldTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		oldTicket.setId(ID);
		Ticket updatedTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Closed", null, null);
		updatedTicket.setId(ID);

		Mockito.when(this.tRepo.findById(ID)).thenReturn(Optional.of(oldTicket));
		Mockito.when(this.tRepo.save(oldTicket)).thenReturn(updatedTicket);

		assertThat(this.tService.updateTicketStatus(ID)).isEqualTo(new TicketDto(updatedTicket));

		Mockito.verify(this.tRepo, Mockito.times(1)).findById(ID);
		Mockito.verify(this.tRepo, Mockito.times(1)).save(oldTicket);
	}

	@Test
	void testFindTicketByTopic() {
		long ID = 1;
		Date date = new Date(1608026491L);
		Ticket Ticket1 = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		Ticket1.setId(ID++);
		date = new Date(1608026584L);
		Ticket Ticket2 = new Ticket("Another Dummy Ticket", "This Ticket is another Dummy Ticket for Testing", "Testing", date, 3, "Closed", null, null);
		Ticket2.setId(ID++);
		date = new Date(1608026632L);
		Ticket Ticket3 = new Ticket("Yet Another Dummy Ticket", "This Ticket is also another Dummy Ticket for Testing", "Don't see this test!", date, 3, "Open", null, null);
		Ticket3.setId(ID);
		
		List<Ticket> ReturnList = new ArrayList<>();
		ReturnList.add(Ticket1);
		ReturnList.add(Ticket2);
			
		// When the function is called to get the 2nd Ticket
		Mockito.when(this.tRepo.findByTopic("Testing")).thenReturn(ReturnList);
		
		// Then check that the correct Ticket is retrieved
		assertThat(this.tService.findTicketByTopic("Testing")).isEqualTo(ReturnList.stream().map(TicketDto::new).collect(Collectors.toList()));
		Mockito.verify(this.tRepo, Mockito.times(1)).findByTopic("Testing");
	}
	
	@Test
	void testAddTraineeToTicket() {
		// Given there are 2 trainees and one ticket
		long TIID = 1;
		Date date = new Date(1608026491L);
		Ticket oldTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, null);
		oldTicket.setId(TIID);
		
		List<Trainee> Ticket1Tr = new ArrayList<>();
		
		long TRID = 1;
		//Trainee 2 joins the ticket
		Trainee Trainee1 = new Trainee("DD", "Dummy", "Data", "Dev Ops", "DummyPass", "DD@Test.com", null);
		Trainee1.setId(TRID++);
		Ticket1Tr.add(Trainee1);
		oldTicket.setTrainees(Ticket1Tr);
		
		//Trainee 2 gets Ticket 2
		Trainee Trainee2 = new Trainee("Y0UL00K1N4M3", "Cannot", "Dev Ops", "Findme", "Software Engineering", "Hackerman@Realmail.com", null);
		Trainee2.setId(TRID++);
		
		List<Trainee> TraineeList = new ArrayList<>();
		TraineeList.add(Trainee1);
		TraineeList.add(Trainee2);
		
		
		Ticket newTicket = new Ticket("Dummy Ticket", "This Ticket is a Dummy Ticket for Testing", "Testing", date, 5, "Open", null, TraineeList);
		newTicket.setId(TIID);
					
		// When trainee 2 joins the ticket
		Mockito.when(this.tRepo.findById(1L)).thenReturn(Optional.of(oldTicket));
		Mockito.when(this.tRepo.save(oldTicket)).thenReturn(newTicket);
		// Then check that the both trainees are on the ticket
		assertThat(this.tService.addTraineeToTicket(1L, Trainee2)).isEqualTo(new TicketDto(newTicket));
		Mockito.verify(this.tRepo, Mockito.times(1)).findById(1L);
		Mockito.verify(this.tRepo, Mockito.times(1)).save(oldTicket);
	}

	
	@Test void testDeleteTicket() {
		Long ID = 1L;
		boolean idFound = true;
		Mockito.when(this.tRepo.existsById(ID)).thenReturn(idFound);
		assertThat(this.tService.deleteTicket(ID)).isEqualTo(!idFound);
		Mockito.verify(this.tRepo, Mockito.times(1)).existsById(ID);
	}
	/* 
	 * @Test void testUpdateTicket() { Optional<Ticket> optTicket =
	 * this.repo.findById(id); Ticket oldTicket = optTicket.orElseThrow(() -> new
	 * TicketNotFoundException());
	 * 
	 * oldTicket.setTitle(ticket.getTitle()); oldTicket.setIssue(ticket.getIssue());
	 * oldTicket.setTopic(ticket.getTopic());
	 * oldTicket.setSubmitDate(ticket.getSubmitDate());
	 * oldTicket.setUrgency(ticket.getUrgency());
	 * oldTicket.setStatus(ticket.getStatus());
	 * oldTicket.setTrainer(ticket.getTrainer());
	 * oldTicket.setTrainees(ticket.getTrainees());
	 * 
	 * Ticket updatedTicket = this.repo.save(oldTicket); return updatedTicket; }
	 */
}