package com.cls.toyDApp.fabric;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.stereotype.Component;

import com.cls.toyDApp.common.util.HelperUtil;
import com.cls.toyDApp.config.FabricConfig;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FabricHelper {
	
	private final FabricConfig fabricConfig;
	
	/*
	 * 첫번째 작업 
	 */
	@PostConstruct
	public void enollAdmin() throws Exception{
		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		// CA 인증서를 불러온다.
		props.put("pemFile",fabricConfig.getCaOrgCert());
		//props.put("allowAllHostNames", "true");
		
		/*
		 *  param1 : ca address
		 *  param2 : ca Properties
		 */
		HFCAClient caClient = HFCAClient.createNewInstance(fabricConfig.getCaURL(), props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);
		
		// Create a wallet for managing identities
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
		
		// Check to see if we've already enrolled the admin user.
		if (wallet.get("admin") != null) {
			System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
			return;
		}

		// Enroll the admin user, and import the new identity into the wallet.
		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		
		// Fabric CA가 TLS 설정 시 사용
		//enrollmentRequestTLS.addHost("localhost");
		//enrollmentRequestTLS.setProfile("tls");
		
		Enrollment enrollment = caClient.enroll(fabricConfig.getCaId(), fabricConfig.getCaPw(), enrollmentRequestTLS);
		Identity user = Identities.newX509Identity(fabricConfig.getMspId(),Identities.readX509Certificate(enrollment.getCert()),enrollment.getKey());
		wallet.put("admin", user); //? - 1
		System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
	}
	
	/*
	 * 두번째 작업
	 */
	public Network gateway() throws Exception {
		
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletPath);
		
		// load a CCP
		// connection.json OR connection.yaml
		Path networkConfigPath = HelperUtil.resourcesUrlPath("connection.yaml");
		
		// create a gateway connection
		Network network = null;
		try {
			/* builder.identity
			 * @param1 : Wallet 지갑 객체
			 * @param2 : Wallet 이름
			 */
			Gateway.Builder builder = Gateway.createBuilder();
			// ? - 2
			// discovery 허용시 true
			builder.identity(wallet, "admin").networkConfig(networkConfigPath).discovery(false);
			
			// get the network and contract
			Gateway gateway = builder.connect();
			network = gateway.getNetwork(fabricConfig.getChannelName());
			
			// 외부로 빼기
			/*
			Contract contract = network.getContract("fabcar");

			byte[] result;

			result = contract.evaluateTransaction("queryAllCars");
			System.out.println(new String(result));

			contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");

			result = contract.evaluateTransaction("queryCar", "CAR10");
			System.out.println(new String(result));

			contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

			result = contract.evaluateTransaction("queryCar", "CAR10");
			System.out.println(new String(result));
			*/
		} catch (IOException e) {
			System.out.println("Error Gateway Connecting");
		}
		return network;
	}	
	// 사용하지 않음
	private void registerUser() throws Exception{

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		props.put("pemFile",
			"../../test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
//		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the user.
		if (wallet.get("appUser") != null) {
			System.out.println("An identity for the user \"appUser\" already exists in the wallet");
			return;
		}

		X509Identity adminIdentity = (X509Identity)wallet.get("admin");
		if (adminIdentity == null) {
			System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
			return;
		}
		User admin = new User() {

			@Override
			public String getName() {
				return "admin";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "org1.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return Identities.toPemString(adminIdentity.getCertificate());
					}
				};
			}

			@Override
			public String getMspId() {
				return "Org1MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest("appUser");
		registrationRequest.setAffiliation("org1.department1");
		registrationRequest.setEnrollmentID("appUser");
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll("appUser", enrollmentSecret);
		Identity user = Identities.newX509Identity("Org1MSP", adminIdentity.getCertificate(), adminIdentity.getPrivateKey());
		wallet.put("appUser", user);
		System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
	}
}
