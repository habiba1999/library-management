package com.maids.library_system.patron.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import com.maids.library_system.patron.entities.Patron;
import com.maids.library_system.patron.models.request.PatronReqModel;
import com.maids.library_system.patron.models.response.PatronResModel;
import com.maids.library_system.patron.repositories.PatronRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PatronServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PatronServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PatronRepository patronRepository;

    @Autowired
    private PatronServiceImpl patronServiceImpl;

    /**
     * Method under test: {@link PatronServiceImpl#createPatron(PatronReqModel)}
     */
    @Test
    void testCreatePatron() {
        // Arrange
        when(patronRepository.save(Mockito.<Patron>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> patronServiceImpl.createPatron(new PatronReqModel("jane.doe@example.org", "Name", "Mobile")));
        verify(patronRepository).save(isA(Patron.class));
    }

    /**
     * Method under test:
     * {@link PatronServiceImpl#updatePatron(long, PatronReqModel)}
     */
    @Test
    void testUpdatePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");
        when(patronRepository.save(Mockito.<Patron>any())).thenReturn(patron2);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        long actualUpdatePatronResult = patronServiceImpl.updatePatron(1L,
                new PatronReqModel("jane.doe@example.org", "Name", "Mobile"));

        // Assert
        verify(patronRepository).findById(eq(1L));
        verify(patronRepository).save(isA(Patron.class));
        assertEquals(1L, actualUpdatePatronResult);
    }

    /**
     * Method under test:
     * {@link PatronServiceImpl#updatePatron(long, PatronReqModel)}
     */
    @Test
    void testUpdatePatron2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.save(Mockito.<Patron>any())).thenThrow(new RuntimeException("foo"));
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> patronServiceImpl.updatePatron(1L, new PatronReqModel("jane.doe@example.org", "Name", "Mobile")));
        verify(patronRepository).findById(eq(1L));
        verify(patronRepository).save(isA(Patron.class));
    }

    /**
     * Method under test:
     * {@link PatronServiceImpl#updatePatron(long, PatronReqModel)}
     */
    @Test
    void testUpdatePatron3() {
        // Arrange
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> patronServiceImpl.updatePatron(1L, new PatronReqModel("jane.doe@example.org", "Name", "Mobile")));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronServiceImpl#getPatronById(long)}
     */
    @Test
    void testGetPatronById() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        PatronResModel buildResult = PatronResModel.builder()
                .email("jane.doe@example.org")
                .id(1)
                .mobile("Mobile")
                .name("Name")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PatronResModel>>any())).thenReturn(buildResult);

        // Act
        PatronResModel actualPatronById = patronServiceImpl.getPatronById(1L);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(patronRepository).findById(eq(1L));
        assertEquals("Mobile", actualPatronById.getMobile());
        assertEquals("Name", actualPatronById.getName());
        assertEquals("jane.doe@example.org", actualPatronById.getEmail());
        assertEquals(1, actualPatronById.getId());
    }

    /**
     * Method under test: {@link PatronServiceImpl#getPatronById(long)}
     */
    @Test
    void testGetPatronById2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PatronResModel>>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> patronServiceImpl.getPatronById(1L));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronServiceImpl#getPatronById(long)}
     */
    @Test
    void testGetPatronById3() {
        // Arrange
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Mock modelMapper to return the correct type
        PatronResModel buildResult = PatronResModel.builder()
                .email("jane.doe@example.org")
                .id(1)
                .mobile("Mobile")
                .name("Name")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PatronResModel>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> patronServiceImpl.getPatronById(1L));

        // Verify the interactions
        verify(modelMapper, never()).map(Mockito.<Object>any(), Mockito.<Class<PatronResModel>>any()); // Mapping should never occur if Patron is not found.
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronServiceImpl#getAllPatrons()}
     */
    @Test
    void testGetAllPatrons() {
        // Arrange
        when(patronRepository.findAll()).thenReturn(new ArrayList<>());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> patronServiceImpl.getAllPatrons());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(patronRepository).findAll();
    }

    /**
     * Method under test: {@link PatronServiceImpl#getAllPatrons()}
     */
    @Test
    void testGetAllPatrons2() {
        // Arrange
        when(patronRepository.findAll()).thenReturn(new ArrayList<>());
        PatronResModel buildResult = PatronResModel.builder()
                .email("jane.doe@example.org")
                .id(1)
                .mobile("Mobile")
                .name("Name")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(buildResult);

        // Act
        List<PatronResModel> actualAllPatrons = patronServiceImpl.getAllPatrons();

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(patronRepository).findAll();
        assertEquals(1, actualAllPatrons.size());
        PatronResModel getResult = actualAllPatrons.get(0);
        assertEquals("Mobile", getResult.getMobile());
        assertEquals("Name", getResult.getName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals(1, getResult.getId());
    }

    /**
     * Method under test: {@link PatronServiceImpl#deletePatronById(long)}
     */
    @Test
    void testDeletePatronById() {
        // Arrange
        doNothing().when(patronRepository).deleteById(Mockito.<Long>any());

        // Act
        patronServiceImpl.deletePatronById(1L);

        // Assert that nothing has changed
        verify(patronRepository).deleteById(eq(1L));
    }

    /**
     * Method under test: {@link PatronServiceImpl#deletePatronById(long)}
     */
    @Test
    void testDeletePatronById2() {
        // Arrange
        doThrow(new RuntimeException("foo")).when(patronRepository).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> patronServiceImpl.deletePatronById(1L));
        verify(patronRepository).deleteById(eq(1L));
    }
}
