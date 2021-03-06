package com.Data4Design.Workflows.Implementations;

import java.util.Collection;
import java.util.stream.Collectors;

import com.Data4Design.Interfaces.ICountryInfoItemService;
import com.Data4Design.Workflows.Interfaces.IGetCountryInfoWorkflow;

public class GetCountryInfoWorkflow implements IGetCountryInfoWorkflow {
	
	private Collection<ICountryInfoItemService> iCountryInfoItemServices;

	public GetCountryInfoWorkflow(Collection<ICountryInfoItemService> iCountryInfoItemServices) throws Exception {
		
		if(iCountryInfoItemServices == null)
			throw new Exception("iCountryInfoItemServices");
		
		this.iCountryInfoItemServices = iCountryInfoItemServices;
	}
	
	@Override
	public CountryInfo GetCountryInfo(Country thisCountry) throws Exception {
		
		if(thisCountry.countryName.equals(null))
			throw new Exception(thisCountry.countryName);
		
		CountryInfo countryInfo = new CountryInfo();
		countryInfo.CountryInfoItems = GetCountryInfoItems(thisCountry);
		return countryInfo;
	}
	
	private Collection<CountryInfoItem> GetCountryInfoItems(Country thisCountry){
		return  iCountryInfoItemServices
				.parallelStream()
				.map(iCountryInfoItemService -> iCountryInfoItemService.GetCountryInfoItem(thisCountry))
				.filter(countryInfoItem -> countryInfoItem != null)
				.collect(Collectors.toList());
	}

}
