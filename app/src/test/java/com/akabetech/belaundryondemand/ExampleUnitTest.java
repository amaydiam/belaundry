package com.akabetech.belaundryondemand;

import com.akabetech.belaundryondemand.util.AESUtil;
import com.akabetech.belaundryondemand.util.ByteUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void aesEncryptTest() throws Exception{
        String value = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:non=\"http://pocket.anabatic.com/ws/contract/transaction/nonfin\" xmlns:bean=\"http://pocket.anabatic.com/ws/contract/bean\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <non:RegisterCustomerRequest>\n" +
                "         <bean:AuthenticationMessage>\n" +
                "            <bean:username>DikiAFauzan</bean:username>\n" +
                "         </bean:AuthenticationMessage>\n" +
                "         <non:mid>123</non:mid>\n" +
                "         <non:phoneNumber>123</non:phoneNumber>\n" +
                "         <non:deviceId>1022211112</non:deviceId>\n" +
                "      </non:RegisterCustomerRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String key = "0987654321abcdef";
        byte[] encrypt = AESUtil.encrypt(value,key);
        String hex = ByteUtil.bytesToHex(encrypt);
        assertEquals("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:non=\"http://pocket.anabatic.com/ws/contract/transaction/nonfin\" xmlns:bean=\"http://pocket.anabatic.com/ws/contract/bean\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <non:RegisterCustomerRequest>\n" +
                "         <bean:AuthenticationMessage>\n" +
                "            <bean:username>DikiAFauzan</bean:username>\n" +
                "         </bean:AuthenticationMessage>\n" +
                "         <non:mid>123</non:mid>\n" +
                "         <non:phoneNumber>123</non:phoneNumber>\n" +
                "         <non:deviceId>1022211112</non:deviceId>\n" +
                "      </non:RegisterCustomerRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>",AESUtil.decrypt(ByteUtil.hexToBytes(hex),key));

    }
    @Test
    public void generationKeyTest(){
        String key = AESUtil.generateKey();
        System.out.println(key);
        assertEquals(16,key.length());

    }
}