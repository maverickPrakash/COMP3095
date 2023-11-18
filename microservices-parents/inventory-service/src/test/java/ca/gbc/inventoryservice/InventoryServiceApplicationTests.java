package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import ca.gbc.inventoryservice.dto.InventoryResponse;
import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void isInStock() throws Exception {
		Inventory data1 = Inventory.builder()
				.id(14L)
				.quantity(12)
				.skuCode("sku345")
				.build();

		Inventory data2= Inventory.builder()
				.id(13L)
				.quantity(12)
				.skuCode("sku123")
				.build();
		inventoryRepository.save(data1);
		inventoryRepository.save(data2);
		Assertions.assertThat(inventoryRepository.findAll().size()>0);
		List<InventoryRequest> inventoryRequestsList = new ArrayList<>();
		InventoryRequest dataReq1 = InventoryRequest.builder().skuCode("sku123").quantity(2).build();
		InventoryRequest dataReq2 = InventoryRequest.builder().skuCode("sku345").quantity(2).build();
		inventoryRequestsList.add(dataReq1);
		inventoryRequestsList.add(dataReq2);
		String dataSender = objectMapper.writeValueAsString(inventoryRequestsList);
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dataSender));
		MvcResult result = response.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);
		int actualSize = jsonNodes.size();


		Assertions.assertThat(actualSize==2);






	}

}
