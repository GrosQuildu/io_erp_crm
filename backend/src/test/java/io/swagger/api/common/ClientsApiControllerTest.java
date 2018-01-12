package io.swagger.api.common;


import io.swagger.model.common.Client;
import io.swagger.model.common.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientsApiControllerTest {
    private static final Client CLIENT1 = new Client(1, "name1", "asd1@asd.com", null);
    private static final Client CLIENT1_UPDATE = new Client(1, "name2", "asd2@asd.com", null);
    private static final Client CLIENT2 = new Client(2, "name2", "asd2@asd.com", null);
    private static final Client CLIENT3 = new Client(3, "name3", "asd3@asd.com", null);

    @InjectMocks
    private ClientsApiController controller;
    @Mock
    private ClientRepository repository;

    @Test
    public void whenFindingClientsItShouldReturnAllClients() {
        given(repository.findAll()).willReturn(Arrays.asList(CLIENT1, CLIENT2));
        assertThat(controller.getClients().getBody())
                .containsOnly(CLIENT1, CLIENT2);
    }

//    @Test
//    public void whenAddingClientItShouldBeSaved() {
//        given(repository.save(CLIENT3)).willReturn(CLIENT3);
//        given(repository.findOne(CLIENT3.getId())).willReturn(CLIENT3);
//        controller.createClient(CLIENT3);
//        assertThat(controller.getClient(CLIENT3.getId()).getBody()).isSameAs(CLIENT3);
//    }
//
//    @Test
//    public void whenUpdatingClientItShouldNotChangeId() {
//        given(repository.findById(CLIENT1.getId())).willReturn(CLIENT1);
//        given(repository.findOne(CLIENT1.getId())).willReturn(CLIENT1);
//
//        controller.updateClient(CLIENT1_UPDATE.getId(), CLIENT1_UPDATE);
//        assertThat(controller.getClient(CLIENT1_UPDATE.getId()).getBody()).isSameAs(CLIENT1);
//    }
//
//    @Test
//    public void whenDeletingAnClientItShouldUseTheRepository() {
//        given(repository.findById(CLIENT1.getId())).willReturn(CLIENT1);
//        given(repository.findOne(CLIENT1.getId())).willReturn(CLIENT1);
//
////        controller.deleteClient(CLIENT1.getId());
////        verify(repository).delete(CLIENT1);
//    }
}

