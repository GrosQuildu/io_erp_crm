package io.swagger.api.common;


import io.swagger.model.common.Unit;
import io.swagger.model.common.UnitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UnitsApiControllerTest {
    private static final Unit UNIT1 = new Unit(1, "name", "ShortName");
    private static final Unit UNIT2 = new Unit(2, "name2", "ShortName2");
    private static final Unit UNIT3 = new Unit(3, "name3", "ShortName3");

    @InjectMocks
    private UnitsApiController controller;
    @Mock
    private UnitRepository repository;

    @Test
    public void whenFindingUnitsItShouldReturnAllUnits() {
        given(repository.findAll()).willReturn(Arrays.asList(UNIT1, UNIT2));
        assertThat(controller.getUnits().getBody())
                .containsOnly(UNIT1, UNIT2);
    }

    @Test
    public void whenAddingUnitItShouldBeSaved() {
        given(repository.save(UNIT3)).willReturn(UNIT3);
        given(repository.findOne(UNIT3.getId())).willReturn(UNIT3);
        controller.createUnit(UNIT3);
        assertThat(controller.getUnit(UNIT3.getId()).getBody()).isSameAs(UNIT3);
    }

    @Test
    public void whenUpdatingUnitItShouldNotChangeId() {
        given(repository.findById(UNIT1.getId())).willReturn(UNIT1);
        given(repository.findOne(UNIT1.getId())).willReturn(UNIT1);

        Unit UnitNull = new Unit(UNIT1.getId(), "test", "testShort");
        controller.updateUnit(UnitNull.getId(), UnitNull);
        assertThat(controller.getUnit(UnitNull.getId()).getBody()).isSameAs(UNIT1);
    }

    @Test
    public void whenDeletingAnUnitItShouldUseTheRepository() {
        given(repository.findById(UNIT1.getId())).willReturn(UNIT1);
        given(repository.findOne(UNIT1.getId())).willReturn(UNIT1);

//        controller.deleteUnit(UNIT1.getId());
//        verify(repository).delete(UNIT1);
    }
}

