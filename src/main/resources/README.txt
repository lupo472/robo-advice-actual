*****************************************************************************************************************************
*																															*
*													 Rest-API's																*
*																															*
************************************************** UserController ***********************************************************
*																															*
* * registerUser																											*
*		Header 		[]																										*
*		Body 		[User {email, password}]																				*
*		Response	[response(0/1), data(String)]																			*
*		Note: 	It is not necessary to return the user that has been registered												*
*																															*
*****************************************************************************************************************************	
*																															*
* * loginUser																												*
*		Header		[]																										*
*		Body		[User {email, password}]																				*
*		Response	[response(0/1), data(String/Login{email, token})]														*
*		Note:	It is not necessary to return the user, the front-end needs just the token and (but it's not strictly 		*
*				necessary) the email																						*
*																															*			
************************************************ CapitalController **********************************************************
*																															*
* * addCapital																												*
*		Header 		[token]																									*
*		Body 		[Capital {amount}]																						*
*		Response	[response(0/1), data(String)]																			*
*		Note: 	This API may be called at the first login of the user, that is when the getCurrentCapital call fails		*
*																															*
*****************************************************************************************************************************
*																															*
* * getCurrentCapital																										*
*		Header 		[token]																									*
*		Body 		[]																										*
*		Response	[response(0/1), data(String/Capital {amount, date})]													*
*		Note: 																												*
*																															*
*****************************************************************************************************************************
*																															*
* * getCapitalForPeriod																										*
*		Header 		[token]																									*
*		Body 		[Period {period}]																						*
*		Response	[response(0/1), data(String/List<Capital>)]																*
*		Note: 																												*
*																															*
******************************************** DefaultStrategyController ******************************************************
*																															*
* * getDefaultStrategySet																									*
*		Header 		[token]																									*
*		Body 		[]																										*
*		Response	[response(1), data(List<DefaultStrategy>)]																*
*		Note: 																												*
*																															*
********************************************** AssetClassController *********************************************************
*																															*
* * getAssetClassSet																										*
*		Header 		[token]																									*
*		Body 		[]																										*
*		Response	[response(1), data(List<AssetClass>)]																	*
*		Note: 																												*
*																															*
********************************************** PortfolioController **********************************************************
*                                                                                                                           *
* * getCurrentPortfolio                                                                                                 	*
*       Header      [token]                                                                                                 *
*       Body        []                                                                                                      *
*       Response    [response(0/1), data(String/PortfolioDTO)]                                                              *
*       Note:                                                                                                               *
*                                                                                                                           *
*****************************************************************************************************************************
*                                                                                                                           *
* * getPortfolioForPeriod                                                                                                   *
*       Header      [token]                                                                                                 *
*       Body        [Period {period}]                                                                                       *
*       Response    [response(0/1), data(String/List<PortfolioDTO>)]                                                        *
*       Note:                                                                                                               *
*                                                                                                                           *
******************************************** CustomStrategyController *******************************************************
*																															*
* * setCustomStrategy																										*
*		Header 		[token]																									*
*		Body 		[CustomStrategy {List<AssetClassStrategy>}]																*
*		Response	[response(1), data(List<AssetClass>)]																	*
*		Note: 																												*
*																															*
*****************************************************************************************************************************
*																															*
* * getActiveStrategy																										*
*		Header 		[token]																									*
*		Body 		[]																										*
*		Response	[response(0/1), data(String/CustomStrategy {List<AssetClassStrategy>, active, date})]					*
*		Note: 																												*
*																															*
*****************************************************************************************************************************
*																															*
* * getCustomStrategyHistory																								*
*		Header 		[token]																									*
*		Body 		[Period {period}]																						*
*		Response	[response(0/1), data(String/List<CustomStrategy>)]														*
*		Note: 																												*
*																															*
********************************************* FinancialDataController *******************************************************
*																															*
* * getFinancialDataSet 																									*
*		Header 		[token]																									*
*		Body 		[]                                      																*
*		Response	[response(1), data(List<FinancialData>)]	    														*
*		Note:   FinancialData contains the AssetClass and a list of FinancialDataElement {data, value}						*
*																															*
*****************************************************************************************************************************
