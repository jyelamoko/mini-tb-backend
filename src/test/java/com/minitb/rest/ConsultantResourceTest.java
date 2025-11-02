package com.minitb.rest;

import com.minitb.entity.Consultant;
import com.minitb.repository.ConsultantRepository;
import com.minitb.services.ConsultantService;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("--- ConsultantResourceTest ----")
@ExtendWith(MockitoExtension.class)
class ConsultantResourceTest {
    @InjectMocks
    ConsultantService consultantService;

    @Mock
    ConsultantRepository consultantRepository;

    @Test
    @DisplayName("Should retrieve All (Consultants)")
    void testRetrieveAll() {
        Consultant c1 = new Consultant();
        c1.firstName = "Durand";
        c1.lastName = "Claire";
        c1.hireDate = LocalDate.of(2022, 3, 10);

        Consultant c2 = new Consultant();
        c2.firstName = "Martin";
        c2.lastName = "Lucas";
        c2.hireDate = LocalDate.of(2021, 9, 1);

        when(consultantRepository.listAll()).thenReturn(Arrays.asList(c1, c2));

        List<Consultant> result = consultantService.retrieveAll();
        assertEquals(2, result.size());
        verify(consultantRepository, times(1)).listAll();
    }

    @Test
    @DisplayName("Should test ADD New (Consultant) - POST HTTP")
    void testAddConsultant_New() {
        Consultant c = new Consultant();
        c.firstName = "New";
        c.lastName = "User";
        c.email = "new.user@example.com";
        c.phoneNumber = "0600000000";
        c.hireDate = LocalDate.now();

        when(consultantRepository.find("email = ?1 OR phoneNumber = ?2", c.email, c.phoneNumber))
                .thenReturn(mock(io.quarkus.hibernate.orm.panache.PanacheQuery.class));
        when(consultantRepository.find("email = ?1 OR phoneNumber = ?2", c.email, c.phoneNumber)
                .firstResult()).thenReturn(null);

        doAnswer(invocation -> {
            c.id = 99L;
            return null;
        }).when(consultantRepository).persist(c);

        Consultant created = consultantService.addConsultant(c);
        assertNotNull(created);
        assertEquals(99L, created.id);
        verify(consultantRepository).persist(c);
    }

    @Test
    @DisplayName("Should Add (Consultant) already Exists - GET HTTP")
    void testAddConsultant_AlreadyExists() {
        Consultant c = new Consultant();
        c.email = "existing@example.com";
        c.phoneNumber = "0600000000";

        when(consultantRepository.find("email = ?1 OR phoneNumber = ?2", c.email, c.phoneNumber))
                .thenReturn(mock(io.quarkus.hibernate.orm.panache.PanacheQuery.class));
        when(consultantRepository.find("email = ?1 OR phoneNumber = ?2", c.email, c.phoneNumber)
                .firstResult()).thenReturn(new Consultant());

        assertThrows(WebApplicationException.class, () -> consultantService.addConsultant(c));
    }

    @Test
    @DisplayName("Should updated (Consultants NotFound) - PUT HTTP")
    void testUpdateConsultant_NotFound() {
        when(consultantRepository.findById(1L)).thenReturn(null);

        Consultant upd = new Consultant();
        upd.firstName = "X";

        Consultant res = consultantService.updateConsultant(1L, upd);
        assertNull(res);
        verify(consultantRepository).findById(1L);
    }

    @Test
    @DisplayName("Should Update (Consultant) - Success")
    void testUpdateConsultant_Success() {
        Consultant existing = new Consultant();
        existing.id = 1L;
        existing.firstName = "Old";

        Consultant updated = new Consultant();
        updated.firstName = "New";
        updated.lastName = "Name";
        updated.jobTitle = "Dev";
        updated.status = "Active";
        updated.hireDate = LocalDate.of(2023, 1, 1);
        updated.email = "new@example.com";
        updated.phoneNumber = "0600000000";

        when(consultantRepository.findById(1L)).thenReturn(existing);

        Consultant result = consultantService.updateConsultant(1L, updated);

        assertEquals("New", result.firstName);
        assertEquals("Name", result.lastName);
        assertEquals("Dev", result.jobTitle);
        assertEquals("Active", result.status);
        assertEquals(LocalDate.of(2023, 1, 1), result.hireDate);
        assertEquals("new@example.com", result.email);
        assertEquals("0600000000", result.phoneNumber);
    }

    @Test
    @DisplayName("Should remove (Consultant) by ID - DELETE HTTP")
    void testRemoveConsultant() {
        when(consultantRepository.deleteById(1L)).thenReturn(true);

        boolean removed = consultantService.removeConsultant(1L);
        assertTrue(removed);
        verify(consultantRepository).deleteById(1L);
    }

}