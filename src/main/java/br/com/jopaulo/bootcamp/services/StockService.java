package br.com.jopaulo.bootcamp.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jopaulo.bootcamp.dto.StockDTO;
import br.com.jopaulo.bootcamp.entities.Stock;
import br.com.jopaulo.bootcamp.exception.BusinessException;
import br.com.jopaulo.bootcamp.exception.NotFoundException;
import br.com.jopaulo.bootcamp.mapper.StockMapper;
import br.com.jopaulo.bootcamp.repositories.StockRepository;
import br.com.jopaulo.bootcamp.util.MessageUtils;

@Service
public class StockService {
	
	@Autowired
	private StockRepository repository;
	
	@Autowired
	private StockMapper mapper;
	
	@Transactional(readOnly = true)
	public List<StockDTO> findAll() {
		return mapper.toDto(repository.findAll());
	}
	
	@Transactional(readOnly = true)
	public StockDTO findById(Long id) {
		return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}
	
	@Transactional
	public StockDTO save(StockDTO dto) {
		Optional<Stock> optional = repository.findByNameAndDate(dto.getName(), dto.getDate());
		if (optional.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		Stock stock = mapper.toEntity(dto);
		repository.save(stock);
		return mapper.toDto(stock);
	}
	
	@Transactional(readOnly = true)
	public List<StockDTO> findByToday() {
		return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}

	public StockDTO update(StockDTO dto) {
		Optional<Stock> optional = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
		if (optional.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		Stock stock = mapper.toEntity(dto);
		repository.save(stock);
		return mapper.toDto(stock);
	}

	@Transactional
	public StockDTO delete(Long id) {
		StockDTO dto = this.findById(id);
		repository.deleteById(dto.getId());
		return dto;
	}
	
//	https://digitalinnovation.one/santander-dev-week-fullstack-developer/sessao/aula-03-345rJPGWLXE
//	2:23

}
