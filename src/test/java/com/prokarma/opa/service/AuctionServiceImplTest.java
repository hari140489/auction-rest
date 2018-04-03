package com.prokarma.opa.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.prokarma.opa.converter.AuctionConverter;
import com.prokarma.opa.exception.InvalidAuctionSearchException;
import com.prokarma.opa.exception.InvalidCreateAuctionException;
import com.prokarma.opa.exception.ProductNotFoundException;
import com.prokarma.opa.fixture.AddAuctionFixture;
import com.prokarma.opa.fixture.OnlineProductAuctionFixture;
import com.prokarma.opa.repository.AuctionRepository;
import com.prokarma.opa.repository.model.AuctionDto;
import com.prokarma.opa.repository.model.CompletedAuctionDto;
import com.prokarma.opa.service.validator.AuctionValidatorImpl;
import com.prokarma.opa.web.domain.AuctionVo;
import com.prokarma.opa.web.domain.CompletedAuctionVo;

@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceImplTest {

	private AuctionService auctionService;
	@Mock
	private AuctionValidatorImpl auctionValidator;
	@Mock
	private AuctionRepository auctionRepository;
	@Mock
	private AuctionConverter auctionConverter;

	@Captor
	ArgumentCaptor<List<AuctionDto>> auctionDtoListCaptor;
	@Captor
	ArgumentCaptor<List<CompletedAuctionDto>> completedAuctionDtoListCaptor;
	@Captor
	ArgumentCaptor<AuctionDto> auctionDtoCaptor;
	
	@Before
	public void setup() {
		auctionService = new AuctionServiceImpl(auctionRepository, auctionValidator, auctionConverter);
	}

	@Test
	public void testAddAuction_WithValidateData_BycallingSaveAuctionRepositoryMethod() {

		doNothing().when(auctionValidator).validate(any(AuctionVo.class));
		when(auctionConverter.toDto(any(AuctionVo.class))).thenReturn(AddAuctionFixture.auctionDto());

		auctionService.addAuction(AddAuctionFixture.auctionVo());
		ArgumentCaptor<AuctionDto> argumentCaptor = ArgumentCaptor.forClass(AuctionDto.class);

		verify(auctionValidator).validate(any(AuctionVo.class));
		verify(auctionConverter).toDto(any(AuctionVo.class));
		verify(auctionRepository).saveAuction(argumentCaptor.capture());

		AuctionDto auctionDto = argumentCaptor.getValue();

		assertEquals(AddAuctionFixture.auctionDto().getName(), auctionDto.getName());
		assertEquals(AddAuctionFixture.auctionDto().getType(), auctionDto.getType());
		assertEquals(AddAuctionFixture.auctionDto().getMin_bid_amount(), auctionDto.getMin_bid_amount());
		assertEquals(AddAuctionFixture.auctionDto().getOwner_email(), auctionDto.getOwner_email());
		assertArrayEquals(AddAuctionFixture.auctionDto().getImage(), auctionDto.getImage());
	}

	@Test(expected = InvalidCreateAuctionException.class)
	public void testAddAuction_WithInvalidAddAuction_ThrowInvalidCreateAuctionException() {
		doThrow(InvalidCreateAuctionException.class).when(auctionValidator).validate(any(AuctionVo.class));

		auctionService.addAuction(AddAuctionFixture.auctionVo());

		verify(auctionValidator).validate(any(AuctionVo.class));

	}

	@Test(expected = InvalidAuctionSearchException.class)
	public void testViewProducts_whenNameAndTypeAreEmpty_thenThrowInvalidAuctionSearchException() {
		auctionService.viewProducts("a@b.com", "", "");
	}

	@Test
	public void testViewProducts_whenNameIsEmpty_thenReturnProducts() {
		List<AuctionVo> auctionVoList = OnlineProductAuctionFixture.auctionVos();
		List<AuctionDto> auctionDtoList = OnlineProductAuctionFixture.auctionDtos();
		when(auctionRepository.viewAllProducts(anyString(), anyString(), anyString())).thenReturn(auctionDtoList);
		when(auctionConverter.toVoList(any())).thenReturn(auctionVoList);

		List<AuctionVo> auctionVos = auctionService.viewProducts("a@b.com", "", "type");

		verify(auctionRepository).viewAllProducts("a@b.com", "", "type");
		verify(auctionConverter).toVoList(auctionDtoListCaptor.capture());

		List<AuctionDto> auctionDtos = auctionDtoListCaptor.getValue();
		assertEquals(auctionDtoList.size(), auctionDtos.size());
		assertEquals(auctionDtoList.get(0).getName(), auctionDtos.get(0).getName());
		assertEquals(auctionDtoList.get(0).getType(), auctionDtos.get(0).getType());
		assertEquals(auctionDtoList.get(0).getMin_bid_amount(), auctionDtos.get(0).getMin_bid_amount());
		assertEquals(auctionDtoList.get(0).getActive(), auctionDtos.get(0).getActive());

		assertEquals(auctionVoList.size(), auctionVos.size());
		assertEquals(auctionVoList.get(0).getName(), auctionVos.get(0).getName());
		assertEquals(auctionVoList.get(0).getType(), auctionVos.get(0).getType());
		assertEquals(auctionVoList.get(0).getPrice(), auctionVos.get(0).getPrice());
		assertEquals(auctionVoList.get(0).getActive(), auctionVos.get(0).getActive());
	}

	@Test
	public void testViewProducts_whenNameAndTypeAreGiven_thenReturnProducts() {
		List<AuctionVo> auctionVoList = OnlineProductAuctionFixture.auctionVos();
		List<AuctionDto> auctionDtoList = OnlineProductAuctionFixture.auctionDtos();
		when(auctionRepository.viewAllProducts(anyString(), anyString(), anyString())).thenReturn(auctionDtoList);
		when(auctionConverter.toVoList(any())).thenReturn(auctionVoList);

		List<AuctionVo> auctionVos = auctionService.viewProducts("a@b.com", "name", "type");

		verify(auctionRepository).viewAllProducts("a@b.com", "name", "type");
		verify(auctionConverter).toVoList(auctionDtoListCaptor.capture());

		List<AuctionDto> auctionDtos = auctionDtoListCaptor.getValue();
		assertEquals(auctionDtoList.size(), auctionDtos.size());
		assertEquals(auctionDtoList.get(0).getName(), auctionDtos.get(0).getName());
		assertEquals(auctionDtoList.get(0).getType(), auctionDtos.get(0).getType());
		assertEquals(auctionDtoList.get(0).getMin_bid_amount(), auctionDtos.get(0).getMin_bid_amount());
		assertEquals(auctionDtoList.get(0).getActive(), auctionDtos.get(0).getActive());

		assertEquals(auctionVoList.size(), auctionVos.size());
		assertEquals(auctionVoList.get(0).getName(), auctionVos.get(0).getName());
		assertEquals(auctionVoList.get(0).getType(), auctionVos.get(0).getType());
		assertEquals(auctionVoList.get(0).getPrice(), auctionVos.get(0).getPrice());
		assertEquals(auctionVoList.get(0).getActive(), auctionVos.get(0).getActive());
	}

	@Test
	public void testFindMyActiveAuctions_whenEmailIsGiven_thenReturnActiveAuctions() {
		List<AuctionVo> auctionVoList = OnlineProductAuctionFixture.auctionVos();
		List<AuctionDto> auctionDtoList = OnlineProductAuctionFixture.auctionDtos();
		when(auctionRepository.findActiveAuctionsByEmail(anyString())).thenReturn(auctionDtoList);
		when(auctionConverter.toVoList(any(List.class))).thenReturn(auctionVoList);

		List<AuctionVo> auctionVos = auctionService.findMyActiveAuctions("a@b.com");

		verify(auctionRepository).findActiveAuctionsByEmail("a@b.com");
		verify(auctionConverter).toVoList(auctionDtoListCaptor.capture());

		List<AuctionDto> auctionDtos = auctionDtoListCaptor.getValue();
		assertEquals(auctionDtoList.size(), auctionDtos.size());
		assertEquals(auctionDtoList.get(0).getName(), auctionDtos.get(0).getName());
		assertEquals(auctionDtoList.get(0).getType(), auctionDtos.get(0).getType());
		assertEquals(auctionDtoList.get(0).getMin_bid_amount(), auctionDtos.get(0).getMin_bid_amount());
		assertEquals(auctionDtoList.get(0).getActive(), auctionDtos.get(0).getActive());

		assertEquals(auctionVoList.size(), auctionVos.size());
		assertEquals(auctionVoList.get(0).getName(), auctionVos.get(0).getName());
		assertEquals(auctionVoList.get(0).getType(), auctionVos.get(0).getType());
		assertEquals(auctionVoList.get(0).getPrice(), auctionVos.get(0).getPrice());
		assertEquals(auctionVoList.get(0).getActive(), auctionVos.get(0).getActive());
	}

	@Test
	public void testRetreiveCompletedAuctions_whenEmailIsGiven_thenReturnCompletedAuctions() {
		List<CompletedAuctionVo> completedAuctionVoList = OnlineProductAuctionFixture.completedAuctionVos();
		List<CompletedAuctionDto> completedAuctionDtoList = OnlineProductAuctionFixture.completedAuctionDtos();
		when(auctionRepository.findCompletedAuctionsByEmail(anyString())).thenReturn(completedAuctionDtoList);
		when(auctionConverter.toCompletedVoList(any(List.class))).thenReturn(completedAuctionVoList);
		
		List<CompletedAuctionVo> completedAuctionVos = auctionService.retrieveCompletedAuctions("a@b.com");

		verify(auctionRepository).findCompletedAuctionsByEmail("a@b.com");
		verify(auctionConverter).toCompletedVoList(completedAuctionDtoListCaptor.capture());
		
		List<CompletedAuctionDto> completedAuctionDtos = completedAuctionDtoListCaptor.getValue();
		assertEquals(completedAuctionDtoList.size(), completedAuctionDtos.size());
		assertEquals(completedAuctionDtoList.get(0).getName(), completedAuctionDtos.get(0).getName());
		assertEquals(completedAuctionDtoList.get(0).getBidderName(), completedAuctionDtos.get(0).getBidderName());
		assertEquals(completedAuctionDtoList.get(0).getMaxBidAmount(), completedAuctionDtos.get(0).getMaxBidAmount());
		assertEquals(completedAuctionDtoList.get(0).getMinBidAmount(), completedAuctionDtos.get(0).getMinBidAmount());
		
		assertEquals(completedAuctionVoList.size(), completedAuctionVos.size());
		assertEquals(completedAuctionVoList.get(0).getName(), completedAuctionVos.get(0).getName());
		assertEquals(completedAuctionVoList.get(0).getBidderName(), completedAuctionVos.get(0).getBidderName());
		assertEquals(completedAuctionVoList.get(0).getMaxBidAmount(), completedAuctionVos.get(0).getMaxBidAmount());
		assertEquals(completedAuctionVoList.get(0).getMinBidAmount(), completedAuctionVos.get(0).getMinBidAmount());
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testFindAuctionByIdWithMaximumBid_whenProductDoesNotExist_thenReturnProductNotFoundException() {
		when(auctionRepository.getAuctionByIdWithMaximumBid(any(long.class))).thenReturn(null);
		auctionService.findAuctionByIdWithMaximumBid(any(long.class));
	}
	
	@Test
	public void testFindAuctionByIdWithMaximumBid_whenProductExists_thenReturnAuctionDetails() throws Exception {
		AuctionDto refAuctionDto = OnlineProductAuctionFixture.auctionDtos().get(0);
		AuctionVo refAuctionVo = OnlineProductAuctionFixture.auctionVo();
		when(auctionRepository.getAuctionByIdWithMaximumBid(any(long.class))).thenReturn(refAuctionDto);
		when(auctionConverter.toVo(any(AuctionDto.class))).thenReturn(refAuctionVo);
		
		AuctionVo auctionVo = auctionService.findAuctionByIdWithMaximumBid(15l);
		
		verify(auctionRepository).getAuctionByIdWithMaximumBid(15l);
		verify(auctionConverter).toVo(auctionDtoCaptor.capture());
		
		AuctionDto auctionDto = auctionDtoCaptor.getValue();
		assertEquals(refAuctionDto.getName(), auctionDto.getName());
		assertEquals(refAuctionDto.getType(), auctionDto.getType());
		assertEquals(refAuctionDto.getMin_bid_amount(), auctionDto.getMin_bid_amount());
		assertEquals(refAuctionDto.getActive(), auctionDto.getActive());
		
		assertEquals(refAuctionVo.getName(), auctionVo.getName());
		assertEquals(refAuctionVo.getType(), auctionVo.getType());
		assertEquals(refAuctionVo.getPrice(), auctionVo.getPrice());
		assertEquals(refAuctionVo.getActive(), auctionVo.getActive());		
	}
	
}
