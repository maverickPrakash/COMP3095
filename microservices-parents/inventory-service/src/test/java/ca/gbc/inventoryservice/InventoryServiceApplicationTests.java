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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
class InventoryServiceApplicationTests extends AbstractBaseContainerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void isInStock() throws Exception {
		Inventory data1 = Inventory.builder()
				.id(new Random().nextLong())
				.quantity(2)
				.skuCode("sku123")
				.build();

		inventoryRepository.save(data1);

		Assertions.assertThat(inventoryRepository.findAll().size()>0);
		List<InventoryRequest> inventoryRequestsList = new ArrayList<>();
		InventoryRequest dataReq1 = InventoryRequest.builder().skuCode("sku123").quantity(2).build();
		inventoryRequestsList.add(dataReq1);
		String dataSender = objectMapper.writeValueAsString(inventoryRequestsList);
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dataSender));
		MvcResult result = response.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);
		int actualSize = jsonNodes.size();


		Assertions.assertThat(actualSize>0);






	}

}
