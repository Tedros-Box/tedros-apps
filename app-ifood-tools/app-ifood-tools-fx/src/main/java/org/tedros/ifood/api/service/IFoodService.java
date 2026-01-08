package org.tedros.ifood.api.service;

import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.ifood.api.client.IFoodAuthClient;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodMerchantClient;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.api.model.IFoodAuthRequest;
import org.tedros.ifood.api.model.IFoodAuthResponse;
import org.tedros.ifood.domain.IFoodPropertie;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

public class IFoodService {
		
	private static IFoodService instance;
	
	private final String token;
	
	private IFoodOrderClient orderClient;
	
	private IFoodEventsClient eventClient;
	
	private IFoodMerchantClient merchantClient;
	
	private IFoodService(String token) {
		this.token = token;
	}
	
	public static IFoodService getInstance() {
		
		if(instance==null) {
			
			try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
			
				TPropertieController serv = locator.lookup(TPropertieController.JNDI_NAME);
				
				TSelect<TPropertie> query = new TSelect<>(TPropertie.class);
				query.addAndCondition("key", TCompareOp.LIKE, "ifood");
				
				TResult<List<TPropertie>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), query);
				
				if(res.getState().equals(TState.SUCCESS)) {
					
					List<TPropertie> props = res.getValue();
					
					String grantType = "client_credentials";
					
					
					Optional<String> clientIdOpt = props.stream()
							.filter(p -> p.getKey().equals(IFoodPropertie.IFOOD_CLIENT_ID.getValue()))
							.map(p -> p.getValue())
							.findFirst();
					
					String clientId = clientIdOpt.orElse(null);							
							
					
					Optional<String> clientSecretOpt = props.stream()
							.filter(p -> p.getKey().equals(IFoodPropertie.IFOOD_CLIENT_SECRET.getValue()))
							.map(p -> p.getValue())
							.findFirst();
					
					String clientSecret = clientSecretOpt.orElse(null);
					
					Optional<String> authorizationCodeOpt = props.stream()
							.filter(p -> p.getKey().equals(IFoodPropertie.IFOOD_AUTHORIZATION_CODE.getValue()))
							.map(p -> p.getValue())
							.findFirst();
					
					String authorizationCode = authorizationCodeOpt.orElse(null);
					
					Optional<String> authorizationCodeVerifierOpt = props.stream()
							.filter(p -> p.getKey().equals(IFoodPropertie.IFOOD_AUTHORIZATION_CODE_VERIFIER.getValue()))
							.map(p -> p.getValue())
							.findFirst();
					
					String authorizationCodeVerifier = authorizationCodeVerifierOpt.orElse(null);
					
					if(clientId==null || clientSecret==null || authorizationCode==null || authorizationCodeVerifier==null) {
						throw new RuntimeException("iFood properties not configured.");
					}
				
					IFoodAuthClient client = IFoodClientBuilder.createClient(
		                    IFoodAuthClient.class,
		                    "https://merchant-api.ifood.com.br",
		                    () -> null );
					
					IFoodAuthRequest request = IFoodAuthRequest.builder()
		                    .grantType(grantType)
		                    .clientId(clientId)
		                    .clientSecret(clientSecret)
		                    .authorizationCode(authorizationCode)
		                    .authorizationCodeVerifier(authorizationCodeVerifier)
		                    .refreshToken("")
		                    .build();
		
					IFoodAuthResponse response = client.auth(
		                    request.getGrantType(),
		                    request.getClientId(),
		                    request.getClientSecret(),
		                    request.getAuthorizationCode(),
		                    request.getAuthorizationCodeVerifier(),
		                    request.getRefreshToken());
					
					instance = new IFoodService(response.getAccessToken());
					
				}else {
					throw new RuntimeException("Error getting iFood properties: " + res.getMessage());
				}
			} catch (NamingException e) {
				TLoggerUtil.error(IFoodService.class, e.getMessage(), e);
			}
		}
		
		return instance;
	}
	
	public IFoodOrderClient getOrderClient() {
		if(orderClient==null) {
			orderClient = IFoodClientBuilder.createClient(
					IFoodOrderClient.class,
					"https://merchant-api.ifood.com.br/order/v1.0",
					this::getToken);
		}
		return orderClient;
	}
	
	public IFoodEventsClient getEventClient() {
		if(eventClient==null) {
			eventClient = IFoodClientBuilder.createClient(
					IFoodEventsClient.class,
					"https://merchant-api.ifood.com.br/events/v1.0",
					this::getToken);
		}
		return eventClient;
	}
	
	public IFoodMerchantClient getMerchantClient() {
		if(merchantClient==null) {
			merchantClient = IFoodClientBuilder.createClient(
					IFoodMerchantClient.class,
					"https://merchant-api.ifood.com.br/merchant/v1.0",
					this::getToken);
		}
		return merchantClient;
	}
	
	private String getToken() {
		return token;
	}

}
