/**
 * 
 */
package org.tedros.ifood.domain;

import org.tedros.common.domain.TType;

/**
 * @author Davis Gordon
 *
 */
public enum IFoodPropertie {
	
	IFOOD_CLIENT_ID ("ifood.clientId", "The client identifier.", TType.SYSTEM),
	IFOOD_CLIENT_SECRET ("ifood.clientSecret", "The client secret.", TType.SYSTEM),
	IFOOD_AUTHORIZATION_CODE ("ifood.authCode","The authorization code that was returned "
			+ "after authorizing the application. It is only required when using the authorization code flow.", TType.SYSTEM),
	IFOOD_AUTHORIZATION_CODE_VERIFIER ("ifood.authCodeVerif", "The authorization code verifier that was returned "
			+ "in the user code request. It is only required when using the authorization code flow. "
			+ "The token request will fail in case the code verifier is not present or in case it does "
			+ "not match the one returned in the user code request.", TType.SYSTEM);
	
	private String value;
	private String description;
	private TType type;
	
	private IFoodPropertie(String v, String description, TType type) {
		this.value = v;
		this.description = description;
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public TType getType() {
		return type;
	}
}
