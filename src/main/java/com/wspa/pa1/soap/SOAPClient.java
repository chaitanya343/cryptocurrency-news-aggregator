package com.wspa.pa1.soap;

import com.wspa.pa1.schemas.calculator.Divide;
import com.wspa.pa1.schemas.calculator.DivideResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SOAPClient extends WebServiceGatewaySupport {

//    public ListOfCurrenciesByNameResponse getCurrencyList(){
//        return (ListOfCurrenciesByNameResponse) getWebServiceTemplate().marshalSendAndReceive(null);
//    }

    public DivideResponse divide(int a, int b) {

        Divide request = new Divide();
        request.setIntA(a);
        request.setIntB(b);

        DivideResponse response = (DivideResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://www.dneonline.com/calculator.asmx", request, new SoapActionCallback("http://tempuri.org/Divide"));

        return response;
    }

}
