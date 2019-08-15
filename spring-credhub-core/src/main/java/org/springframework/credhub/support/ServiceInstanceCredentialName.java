/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.support;

import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * The client-provided name of a credential that stores service instance binding
 * credentials. Service instance binding credential names consist of four segments:
 * service broker name, service offering name, service binding GUID, and credential
 * name. When these values are combined the full name of the credential will be of
 * the form
 * {@literal /c/service-broker-name/service-offering-name/binding-GUID/credential-name}.
 *
 * Objects of this type are created by clients and included as part of requests.
 *
 * @author Scott Frederick
 */
public class ServiceInstanceCredentialName extends CredentialName {
	/**
	 * Create a {@link ServiceInstanceCredentialName} from the required name fields.
	 * Intended for internal use in tests. Clients should use
	 * {@link #builder()} to construct instances of this class.
	 *
	 * @param serviceBrokerName the human-readable name of the service broker
	 * @param serviceOfferingName the human-readable name of the service offering
	 * @param serviceBindingId the GUID of the service binding
	 * @param credentialName the name of the binding credential
	 */
	ServiceInstanceCredentialName(String serviceBrokerName, String serviceOfferingName,
								  String serviceBindingId, String credentialName) {
		super("c", serviceBrokerName, serviceOfferingName, serviceBindingId, credentialName);
	}

	/**
	 * Create a builder that provides a fluent API for providing the values required
	 * to construct a {@link ServiceInstanceCredentialName}.
	 *
	 * @return the builder
	 */
	public static ServiceInstanceCredentialNameBuilder builder() {
		return new ServiceInstanceCredentialNameBuilder();
	}

	@Override
	public String toString() {
		return "ServiceInstanceCredentialName{"
				+ "segments=" + Arrays.toString(segments)
				+ "}";
	}

	/**
	 * A builder that provides a fluent API for constructing
	 * {@link ServiceInstanceCredentialName} instances.
	 */
	public static class ServiceInstanceCredentialNameBuilder {
		private String serviceBrokerName;
		private String serviceOfferingName;
		private String serviceBindingId;
		private String credentialName;

		/**
		 * Create a {@link ServiceInstanceCredentialNameBuilder}
		 */
		ServiceInstanceCredentialNameBuilder() {
		}

		/**
		 * Set the service broker name segment of the credential name. This is typically
		 * a human-readable name and should be unique among all service brokers in
		 * Cloud Foundry.
		 *
		 * @param serviceBrokerName the service broker name; must not be {@literal null}
		 * @return the builder
		 */
		public ServiceInstanceCredentialNameBuilder serviceBrokerName(String serviceBrokerName) {
			Assert.notNull(serviceBrokerName, "serviceBrokerName must not be null");
			this.serviceBrokerName = serviceBrokerName;
			return this;
		}

		/**
		 * Set the service offering name segment of the credential name. This is typically
		 * a human-readable name and should be unique within the service broker.
		 *
		 * @param serviceOfferingName the service offering name; must not be {@literal null}
		 * @return the builder
		 */
		public ServiceInstanceCredentialNameBuilder serviceOfferingName(String serviceOfferingName) {
			Assert.notNull(serviceOfferingName, "serviceOfferingName must not be null");
			this.serviceOfferingName = serviceOfferingName;
			return this;
		}

		/**
		 * Set the service binding ID segment of the credential name. This value is
		 * generated by Cloud Foundry when a service instance is bound to an application
		 * and is in the form of a GUID.
		 *
		 * @param serviceBindingId the service binding ID; must not be {@literal null}
		 * @return the builder
		 */
		public ServiceInstanceCredentialNameBuilder serviceBindingId(String serviceBindingId) {
			Assert.notNull(serviceBindingId, "serviceBindingId must not be null");
			this.serviceBindingId = serviceBindingId;
			return this;
		}

		/**
		 * Set the credential name segment of the full credential name.
		 *
		 * @param credentialName the credential name; must not be {@literal null}
		 * @return the builder
		 */
		public ServiceInstanceCredentialNameBuilder credentialName(String credentialName) {
			Assert.notNull(credentialName, "credentialName must not be null");
			this.credentialName = credentialName;
			return this;
		}

		/**
		 * Create a {@link ServiceInstanceCredentialName} from the provided values.
		 *
		 * @return a {@link ServiceInstanceCredentialName}
		 */
		public ServiceInstanceCredentialName build() {
			return new ServiceInstanceCredentialName(serviceBrokerName,
					serviceOfferingName, serviceBindingId, credentialName);
		}
	}
}