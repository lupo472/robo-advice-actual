package it.uiip.digitalgarage.roboadvice.logic.converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.uiip.digitalgarage.roboadvice.persistence.entity.FinancialDataEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.FinancialDataDTO;

public class FinancialDataConverter implements GenericConverter<FinancialDataEntity, FinancialDataDTO> {

	private AssetConverter assetConv = new AssetConverter();
	
	@Override
	public FinancialDataEntity convertToEntity(FinancialDataDTO dto) {
		FinancialDataEntity entity = new FinancialDataEntity();
		entity.setValue(dto.getValue());
		entity.setDate(LocalDate.parse(dto.getDate()));
		entity.setAsset(this.assetConv.convertToEntity(dto.getAsset()));
		return entity;
	}

	@Override
	public FinancialDataDTO convertToDTO(FinancialDataEntity entity) {
		FinancialDataDTO dto = new FinancialDataDTO();
		dto.setAsset(this.assetConv.convertToDTO(entity.getAsset()));
		dto.setDate(entity.getDate().toString());
		dto.setValue(entity.getValue());
		return dto;
	}

	@Override
	public List<FinancialDataEntity> convertToEntity(List<FinancialDataDTO> dto) {
		List<FinancialDataEntity> entity = new ArrayList<>();
		for (FinancialDataDTO financialDataDTO : dto) {
			entity.add(this.convertToEntity(financialDataDTO));
		}
		return entity;
	}

	@Override
	public List<FinancialDataDTO> convertToDTO(List<FinancialDataEntity> entity) {
		List<FinancialDataDTO> dto = new ArrayList<>();
		for (FinancialDataEntity financialDataEntity : entity) {
			dto.add(this.convertToDTO(financialDataEntity));
		}
		return dto;
	}

}
