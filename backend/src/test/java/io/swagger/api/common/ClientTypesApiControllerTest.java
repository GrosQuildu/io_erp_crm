
package io.swagger.api.common;


import io.swagger.model.common.ClientType;
import io.swagger.model.common.ClientTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ClientTypesApiControllerTest {
    private static final ClientType CLIENTTYPE1 = new ClientType(1, "name1");
    private static final ClientType CLIENTTYPE1_UPDATE = new ClientType(1, "name2");
    private static final ClientType CLIENTTYPE2 = new ClientType(2, "name2");
    private static final ClientType CLIENTTYPE3 = new ClientType(3, "name3");

    @InjectMocks
    private ClientTypesApiController controller;
    @Mock
    private ClientTypeRepository repository;

    @Test
    public void whenFindingClientTypeTypesItShouldReturnAllClientTypeTypes() {
        given(repository.findAll()).willReturn(Arrays.asList(CLIENTTYPE1, CLIENTTYPE2));
        assertThat(controller.getClientTypes().getBody())
                .containsOnly(CLIENTTYPE1, CLIENTTYPE2);
    }

//    @Test
//    public void whenAddingClientTypeItShouldBeSaved() {
//        given(repository.save(CLIENTTYPE3)).willReturn(CLIENTTYPE3);
//        given(repository.findOne(CLIENTTYPE3.getId())).willReturn(CLIENTTYPE3);
//        controller.createClientType(CLIENTTYPE3);
//        assertThat(controller.getClientType(CLIENTTYPE3.getId()).getBody()).isSameAs(CLIENTTYPE3);
//    }
//
//    @Test
//    public void whenUpdatingClientTypeItShouldNotChangeId() {
//        given(repository.findById(CLIENTTYPE1.getId())).willReturn(CLIENTTYPE1);
//        given(repository.findOne(CLIENTTYPE1.getId())).willReturn(CLIENTTYPE1);
//
//        controller.updateClientType(CLIENTTYPE1_UPDATE.getId(), CLIENTTYPE1_UPDATE);
//        assertThat(controller.getClientType(CLIENTTYPE1_UPDATE.getId()).getBody()).isSameAs(CLIENTTYPE1);
//    }
//
//    @Test
//    public void whenDeletingAnClientTypeItShouldUseTheRepository() {
//        given(repository.findById(CLIENTTYPE1.getId())).willReturn(CLIENTTYPE1);
//        given(repository.findOne(CLIENTTYPE1.getId())).willReturn(CLIENTTYPE1);
//
////        controller.deleteClientType(CLIENTTYPE1.getId());
////        verify(repository).delete(CLIENTTYPE1);
//    }
}

