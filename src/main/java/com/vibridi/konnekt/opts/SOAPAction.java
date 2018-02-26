package com.vibridi.konnekt.opts;

public enum SOAPAction {
	EMPTY(""),
	SAML_REQUEST("http://schemas.xmlsoap.org/ws/2005/02/trust/RST/SCT"),
	PROVIDE_AND_REGISTER("urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b"),
	RETRIEVE("urn:ihe:iti:2007:RetrieveDocumentSet"),
	ADHOC_QUERY("urn:ihe:iti:2007:RegistryStoredQuery"),
	PATIENT_FEED("patientFeed"),
	PATIENT_QUERY("patientQuery"),
	PEOPLE_SAVE("http://person.ws.noemalife.com/DemographicService/saveRequest"),
	PEOPLE_FIND_BY_KEYS("http://person.ws.noemalife.com/DemographicService/findByKeysRequest"),
	SUBSCRIBE("http://docs.oasis-open.org/wsn/bw-2/NotificationProducer/SubscribeRequest"),
	UNSUBSCRIBE("http://docs.oasis-open.org/wsn/bw-2/SubscriptionManager/UnsubscribeRequest"),
	CREATE_PULL_POINT("http://docs.oasis-open.org/wsn/bw-2/PullPoint/CreatePullPointRequest"),
	DESTROY_PULL_POINT("http://docs.oasis-open.org/wsn/bw-2/PullPoint/DestroyPullPointRequest"),
	FETCH_PULL_POINT("http://docs.oasis-open.org/wsn/bw-2/PullPoint/GetMessagesRequest"),
	GET_SUBSCRIPTIONS("urn:eu:dedalus:dsub:GetSubscriptions"),
	SESSION_GET("urn:eu:dedalus:x1v1:getValue"),
	SESSION_SET("urn:eu:dedalus:x1v1:setValue");
	
	private String action;
	
	private SOAPAction(String action) {
		this.action = action;
	}
	
	@Override
	public String toString() {
		return action;
	}
	
}
