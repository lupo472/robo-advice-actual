package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.logic.operator.RebalancingOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.*;
import it.uiip.digitalgarage.roboadvice.persistence.repository.*;
import it.uiip.digitalgarage.roboadvice.persistence.util.Mapper;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PasswordDTO;
import it.uiip.digitalgarage.roboadvice.service.util.ControllerConstants;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import it.uiip.digitalgarage.roboadvice.service.util.HashFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/admin")
@RestController
public class AdminController extends AbstractController {

	private static String password = "5167477de388c8f1564f46d332567cf0d41fc77436dba7434d5bd25b18be2e77";

	@RequestMapping("/scheduleTask")
	@ResponseBody
	public GenericResponse<?> scheduleTask(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.schedulingOp.scheduleTask();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	@RequestMapping("/updateFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> updateFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.updateFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	@RequestMapping("/initializeFinancialDataSet")
	@ResponseBody
	public GenericResponse<?> initializeFinancialDataSet(@RequestBody @Valid PasswordDTO request) {
		if (HashFunction.hashStringSHA256(request.getPassword()).equals(password)) {
			this.quandlOp.initializeFinancialDataSet();
			return new GenericResponse<String>(1, ControllerConstants.DONE);
		}
		return new GenericResponse<String>(0, ControllerConstants.UNAUTHORIZED);
	}

	//TODO remove from here down - methods to test the rebalancing
	@Autowired
	private AssetRepository assetRep;
	@Autowired
	private FinancialDataRepository financialDataRep;
	@Autowired
	private UserRepository userRep;
	@Autowired
	private PortfolioRepository portfolioRep;
	@Autowired
	private CapitalRepository capitalRep;
	@Autowired
	private CustomStrategyRepository customStrategyRep;
	@Autowired
	private RebalancingOperator rebalancingOp;

	@RequestMapping("/rebalance")
	@ResponseBody
	public boolean rebalance() {
		List<AssetEntity> assets = this.assetRep.findAll();
		Map<Long, List<AssetEntity>> mapAssets = Mapper.getMapAssets(assets);
		List<FinancialDataEntity> list = new ArrayList<>();
		for (AssetEntity asset : assets) {
			list.add(financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate()));
		}
		Map<Long, FinancialDataEntity> financialDataMap = Mapper.getMapFinancialData(list);
		UserEntity user = this.userRep.findByEmail("ciro@infante.com");
		List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		CapitalEntity capitalEntity = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		List<CustomStrategyEntity> strategy = this.customStrategyRep.findByUserAndActive(user, true);
		boolean r = this.rebalancingOp.rebalancePortfolio(mapAssets, financialDataMap, user, currentPortfolio, capitalEntity, strategy);
		return r;
	}

	@RequestMapping("/create")
	@ResponseBody
	public boolean create() {
		UserEntity user = this.userRep.findByEmail("ciro@infante.com");
		List<CustomStrategyEntity> strategyEntity = this.customStrategyRep.findByUser(user);
		List<AssetEntity> assets = this.assetRep.findAll();
		List<FinancialDataEntity> list = new ArrayList<>();
		for (AssetEntity asset : assets) {
			list.add(financialDataRep.findByAssetAndDate(asset, asset.getLastUpdate()));
		}
		Map<Long, FinancialDataEntity> mapFD = Mapper.getMapFinancialData(list);
		Map<Long, List<AssetEntity>> mapAssets = Mapper.getMapAssets(assets);
		CapitalEntity capital = this.capitalRep.findByUserAndDate(user, user.getLastUpdate());
		this.portfolioOp.createUserPortfolio(user, strategyEntity, capital, mapAssets, mapFD);
		List<PortfolioEntity> currentPortfolio = this.portfolioRep.findByUserAndDate(user, user.getLastUpdate());
		for(PortfolioEntity portfolio : currentPortfolio) {
			double random = Math.abs(Math.random());
			portfolio.setUnits(new BigDecimal(random));
			portfolio.setValue(mapFD.get(portfolio.getAsset().getId()).getValue().multiply(new BigDecimal(random)));
			this.portfolioRep.save(portfolio);
		}
		this.capitalOp.computeCapital(user, mapFD, currentPortfolio);
		return true;
	}

}