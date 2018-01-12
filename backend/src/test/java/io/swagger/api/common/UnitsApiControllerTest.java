
package io.swagger.api.common;


import io.swagger.configuration.EmployeeService;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
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
    public void whenAddingUnitItShouldReturnTheSavedUnit() {
        given(repository.save(UNIT3)).willReturn(UNIT3);
        assertThat(controller.createUnit(UNIT3))
                .isSameAs(UNIT3);
    }

    @Test
    public void whenUpdatingUnitItShouldReturnTheSavedUnit() {
        // Given that CHECKED_ITEM is returned when one is requested with CHECKED_ITEM_ID
        given(repository.getOne(CHECKED_ITEM_ID)).willReturn(CHECKED_ITEM);
        // Given that a CHECKED_ITEM is saved and flushed, a CHECKED_ITEM is returned
        given(repository.saveAndFlush(CHECKED_ITEM)).willReturn(CHECKED_ITEM);
        // When updating a CHECKED_ITEM
        assertThat(controller.updateUnit(CHECKED_ITEM, CHECKED_ITEM_ID))
                // Then it should return the CHECKED_ITEM
                .isSameAs(CHECKED_ITEM);
    }
}

