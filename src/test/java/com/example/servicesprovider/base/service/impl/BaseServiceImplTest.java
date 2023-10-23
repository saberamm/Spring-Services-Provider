package com.example.servicesprovider.base.service.impl;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.base.repository.BaseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseServiceImplTest {
    @InjectMocks
    private TestServiceImpl baseService;

    @Mock
    private TestRepository testRepository;

    @Mock
    private Validator validator;

    @Test
    void saveValidModel() {
        TestModel testModel = new TestModel();
        when(validator.validate(testModel)).thenReturn(Collections.emptySet());
        when(testRepository.save(testModel)).thenReturn(testModel);

        TestModel savedModel = baseService.save(testModel);

        assertNotNull(savedModel);
        verify(testRepository, times(1)).save(testModel);
    }

    @Test
    void saveInvalidModel() {
        TestModel testModel = new TestModel();
        when(validator.validate(testModel)).thenReturn(Set.of(Mockito.mock(ConstraintViolation.class)));

        TestModel savedModel = baseService.save(testModel);

        assertNull(savedModel);
        verify(testRepository, never()).save(testModel);
    }

    @Test
    void updateValidModel() {
        TestModel testModel = new TestModel();
        when(validator.validate(testModel)).thenReturn(Collections.emptySet());
        when(testRepository.save(testModel)).thenReturn(testModel);

        TestModel updatedModel = baseService.update(testModel);

        assertNotNull(updatedModel);
        verify(testRepository, times(1)).save(testModel);
    }

    @Test
    void updateInvalidModel() {
        TestModel testModel = new TestModel();
        when(validator.validate(testModel)).thenReturn(Set.of(Mockito.mock(ConstraintViolation.class)));

        TestModel updatedModel = baseService.update(testModel);

        assertNotNull(updatedModel);
        verify(testRepository, never()).save(testModel);
    }

    @Test
    void deleteModel() {
        TestModel testModel = new TestModel();
        baseService.delete(testModel);

        verify(testRepository, times(1)).delete(testModel);
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        TestModel testModel = new TestModel();
        when(testRepository.findById(id)).thenReturn(Optional.of(testModel));

        TestModel foundModel = baseService.findById(id);

        assertNotNull(foundModel);
        verify(testRepository, times(1)).findById(id);
    }

    @Test
    void findAll() {
        List<TestModel> modelList = Arrays.asList(new TestModel(), new TestModel());
        when(testRepository.findAll()).thenReturn(modelList);

        List<TestModel> foundModels = baseService.findAll();

        assertEquals(modelList.size(), foundModels.size());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void existsById() {
        Long id = 1L;
        when(testRepository.existsById(id)).thenReturn(true);

        assertTrue(baseService.existsById(id));
        verify(testRepository, times(1)).existsById(id);
    }

    private static class TestModel extends BaseModel<Long> {
    }

    private interface TestRepository extends BaseRepository<TestModel, Long> {
    }

    private static class TestServiceImpl extends BaseServiceImpl<TestModel, Long, TestRepository> {
        public TestServiceImpl(TestRepository repository, Validator validator) {
            super(repository, validator);
        }
    }
}
