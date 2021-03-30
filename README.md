# toy_dApp
## 순서

### 0. fabricConfig setup

```yaml
caOrgCert: |
   -----BEGIN CERTIFICATE-----
   MIICFzCCAb2gAwIBAgIUG+uQ6PljgHTsc+8fntfc0BFu8lUwCgYIKoZIzj0EAwIw
   ZzELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
   EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRgwFgYDVQQDEw9jYS5vcmdh
   cGVlci5jb20wIBcNMjEwMzExMDEzOTAwWhgPMjEyMTAzMTIwMTM5MDBaMGcxCzAJ
   BgNVBAYTAlVTMRcwFQYDVQQIEw5Ob3J0aCBDYXJvbGluYTEUMBIGA1UEChMLSHlw
   ZXJsZWRnZXIxDzANBgNVBAsTBkZhYnJpYzEYMBYGA1UEAxMPY2Eub3JnYXBlZXIu
   Y29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEJH8VT5R9SF1z0fiF5UK40P6u
   Td1U8ZN1r/gcWDdURj9zIx8V9OEpIISCVmKx+L8LkpHiQNlZg6NjSW2hbIekzqNF
   MEMwDgYDVR0PAQH/BAQDAgEGMBIGA1UdEwEB/wQIMAYBAf8CAQEwHQYDVR0OBBYE
   FEEgRrMkjxNsgX4eyvOWypTw8kh7MAoGCCqGSM49BAMCA0gAMEUCIQDYitqlVZAt
   FD8N00hLZpb6SN05GSSloziZ9Fr2quqF2wIgJoIHZdSX61W5cauUjlhy6uXaAF7D
   5odhuJ+7aVP27os=
   -----END CERTIFICATE-----
caURL: http://172.30.1.7:7054
caId: admin
caPw: adminpw
mspId: apeerMSP
channelName: test1-channel
chaincodeName: helloworld-cc
```

### 1. connection.yaml setup
```yaml
name: toy_dApp
version: 1.0.0
client:
  organization: apeer
  connection:
    timeout:
      peer:
        endorser: '300'
        
channels:
  test1-channel:
    orderers:
      - orderer0.orgorderer.com
      - orderer1.orgorderer.com
      - orderer2.orgorderer.com
    peers:
      peer0.orgapeer.com:
        endorsingPeer: true
        chaincodeQuery: true
        ledgerQuery: true
        eventSource: true
        discover: true
      peer1.orgapeer.com:
        endorsingPeer: true
        chaincodeQuery: true
        ledgerQuery: true
        eventSource: true     
        discover: true
organizations:
  apeer:
    mspid: apeerMSP
    peers:
    - peer0.orgapeer.com
    - peer1.orgapeer.com
    certificateAuthorities:
    - ca.orgapeer.com
    
orderers:
  orderer0.orgorderer.com:
    url: grpc://172.30.1.7:7050
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICGjCCAcGgAwIBAgIUVvxTn4aKayykeWWLcM4hj5F3KogwCgYIKoZIzj0EAwIw
        aTELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
        EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdv
        cmRlcmVyLmNvbTAgFw0yMTAzMTEwMTM5MDBaGA8yMTIxMDMxMjAxMzkwMFowaTEL
        MAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQKEwtI
        eXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdvcmRl
        cmVyLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABKnQbauVDMyY7FYkTJAx
        tW7dy7FsdWwSyunO9zC06KBLmXWQPFH/TLRBP+XBbFxUR/p0tnG5IxKCFzlXE6QZ
        xjyjRTBDMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAGAQH/AgEBMB0GA1Ud
        DgQWBBTTI6mEuULf93xE7PF8o+Sc4O5y7jAKBggqhkjOPQQDAgNHADBEAiAaFFbN
        VF687aYScsg0agnGGi79+gv+F1X9QzXHPzEFSgIgYDwaHAUU8iDzhHoNFTFZGy4O
        DoezTdP3bl6qcFbEEcc=
        -----END CERTIFICATE-----
  
  orderer1.orgorderer.com:
    url: grpc://172.30.1.7:8050
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICGjCCAcGgAwIBAgIUVvxTn4aKayykeWWLcM4hj5F3KogwCgYIKoZIzj0EAwIw
        aTELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
        EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdv
        cmRlcmVyLmNvbTAgFw0yMTAzMTEwMTM5MDBaGA8yMTIxMDMxMjAxMzkwMFowaTEL
        MAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQKEwtI
        eXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdvcmRl
        cmVyLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABKnQbauVDMyY7FYkTJAx
        tW7dy7FsdWwSyunO9zC06KBLmXWQPFH/TLRBP+XBbFxUR/p0tnG5IxKCFzlXE6QZ
        xjyjRTBDMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAGAQH/AgEBMB0GA1Ud
        DgQWBBTTI6mEuULf93xE7PF8o+Sc4O5y7jAKBggqhkjOPQQDAgNHADBEAiAaFFbN
        VF687aYScsg0agnGGi79+gv+F1X9QzXHPzEFSgIgYDwaHAUU8iDzhHoNFTFZGy4O
        DoezTdP3bl6qcFbEEcc=
        -----END CERTIFICATE-----      
  orderer2.orgorderer.com:
    url: grpc://172.30.1.7:9050
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICGjCCAcGgAwIBAgIUVvxTn4aKayykeWWLcM4hj5F3KogwCgYIKoZIzj0EAwIw
        aTELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
        EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdv
        cmRlcmVyLmNvbTAgFw0yMTAzMTEwMTM5MDBaGA8yMTIxMDMxMjAxMzkwMFowaTEL
        MAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQKEwtI
        eXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRowGAYDVQQDExFjYS5vcmdvcmRl
        cmVyLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABKnQbauVDMyY7FYkTJAx
        tW7dy7FsdWwSyunO9zC06KBLmXWQPFH/TLRBP+XBbFxUR/p0tnG5IxKCFzlXE6QZ
        xjyjRTBDMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAGAQH/AgEBMB0GA1Ud
        DgQWBBTTI6mEuULf93xE7PF8o+Sc4O5y7jAKBggqhkjOPQQDAgNHADBEAiAaFFbN
        VF687aYScsg0agnGGi79+gv+F1X9QzXHPzEFSgIgYDwaHAUU8iDzhHoNFTFZGy4O
        DoezTdP3bl6qcFbEEcc=
        -----END CERTIFICATE-----      
peers:
  peer0.orgapeer.com:
    url: grpcs://172.30.1.7:7051
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICFzCCAb2gAwIBAgIUG+uQ6PljgHTsc+8fntfc0BFu8lUwCgYIKoZIzj0EAwIw
        ZzELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
        EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRgwFgYDVQQDEw9jYS5vcmdh
        cGVlci5jb20wIBcNMjEwMzExMDEzOTAwWhgPMjEyMTAzMTIwMTM5MDBaMGcxCzAJ
        BgNVBAYTAlVTMRcwFQYDVQQIEw5Ob3J0aCBDYXJvbGluYTEUMBIGA1UEChMLSHlw
        ZXJsZWRnZXIxDzANBgNVBAsTBkZhYnJpYzEYMBYGA1UEAxMPY2Eub3JnYXBlZXIu
        Y29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEJH8VT5R9SF1z0fiF5UK40P6u
        Td1U8ZN1r/gcWDdURj9zIx8V9OEpIISCVmKx+L8LkpHiQNlZg6NjSW2hbIekzqNF
        MEMwDgYDVR0PAQH/BAQDAgEGMBIGA1UdEwEB/wQIMAYBAf8CAQEwHQYDVR0OBBYE
        FEEgRrMkjxNsgX4eyvOWypTw8kh7MAoGCCqGSM49BAMCA0gAMEUCIQDYitqlVZAt
        FD8N00hLZpb6SN05GSSloziZ9Fr2quqF2wIgJoIHZdSX61W5cauUjlhy6uXaAF7D
        5odhuJ+7aVP27os=
        -----END CERTIFICATE-----
          
    grpcOptions:
      ssl-target-name-override: peer0.orgapeer.com
      hostnameOverride: peer0.orgapeer.com
      
  peer1.orgapeer.com:
    url: grpcs://172.30.1.7:8051
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICFzCCAb2gAwIBAgIUG+uQ6PljgHTsc+8fntfc0BFu8lUwCgYIKoZIzj0EAwIw
        ZzELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMRQwEgYDVQQK
        EwtIeXBlcmxlZGdlcjEPMA0GA1UECxMGRmFicmljMRgwFgYDVQQDEw9jYS5vcmdh
        cGVlci5jb20wIBcNMjEwMzExMDEzOTAwWhgPMjEyMTAzMTIwMTM5MDBaMGcxCzAJ
        BgNVBAYTAlVTMRcwFQYDVQQIEw5Ob3J0aCBDYXJvbGluYTEUMBIGA1UEChMLSHlw
        ZXJsZWRnZXIxDzANBgNVBAsTBkZhYnJpYzEYMBYGA1UEAxMPY2Eub3JnYXBlZXIu
        Y29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEJH8VT5R9SF1z0fiF5UK40P6u
        Td1U8ZN1r/gcWDdURj9zIx8V9OEpIISCVmKx+L8LkpHiQNlZg6NjSW2hbIekzqNF
        MEMwDgYDVR0PAQH/BAQDAgEGMBIGA1UdEwEB/wQIMAYBAf8CAQEwHQYDVR0OBBYE
        FEEgRrMkjxNsgX4eyvOWypTw8kh7MAoGCCqGSM49BAMCA0gAMEUCIQDYitqlVZAt
        FD8N00hLZpb6SN05GSSloziZ9Fr2quqF2wIgJoIHZdSX61W5cauUjlhy6uXaAF7D
        5odhuJ+7aVP27os=
        -----END CERTIFICATE-----
          
    grpcOptions:
      ssl-target-name-override: peer1.orgapeer.com
      hostnameOverride: peer1.orgapeer.com
      
certificateAuthorities:
  ca.orgapeer.com:
    url: http://172.30.1.7:7054
    caName: ca.orgapeer.com


```
### 1. EnollAdmin
```java
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
	

```
### 2. controller
*controller
```java
	private final CommentService commentService ;
	
	@RequestMapping(value="/setCmt", method = RequestMethod.POST)
	public void setComment() {
		
		commentService.setComment();
	}
	
	@RequestMapping(value="/getCmt", method = RequestMethod.POST)
	public void getComment() {
		
		commentService.getComment();
	}
```
### 3. Gateway
```java
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
			
		} catch (IOException e) {
			System.out.println("Error Gateway Connecting");
		}
		return network;
	}	
```
### 4. service
```java
	public void setComment() {
		
		try {
			
			Network network = fabricHelper.gateway();
			Contract contract = network.getContract(fabricConfig.getChaincodeName());
			contract.submitTransaction("CreateComment", "1", "helloworld");
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
			
		}
	}
	
	public void getComment() {
		
		try {
			
			Network network = fabricHelper.gateway();
			Contract contract = network.getContract(fabricConfig.getChaincodeName());
			byte[] result = contract.evaluateTransaction("QueryComment", "1");
			System.out.println("result : "+ new String(result));
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
			
		}
	}
```
