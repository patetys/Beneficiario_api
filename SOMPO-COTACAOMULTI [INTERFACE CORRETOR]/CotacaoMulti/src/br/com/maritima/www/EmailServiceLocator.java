/**
 * EmailServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.maritima.www;

public class EmailServiceLocator extends org.apache.axis.client.Service implements br.com.maritima.www.EmailService {

    public EmailServiceLocator() {
    }


    public EmailServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EmailServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SOAP_HTTP_Port
    private java.lang.String SOAP_HTTP_Port_address = "http://10.110.0.25:7080/EmailWS";

    public java.lang.String getSOAP_HTTP_PortAddress() {
        return SOAP_HTTP_Port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SOAP_HTTP_PortWSDDServiceName = "SOAP_HTTP_Port";

    public java.lang.String getSOAP_HTTP_PortWSDDServiceName() {
        return SOAP_HTTP_PortWSDDServiceName;
    }

    public void setSOAP_HTTP_PortWSDDServiceName(java.lang.String name) {
        SOAP_HTTP_PortWSDDServiceName = name;
    }

    public br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType getSOAP_HTTP_Port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SOAP_HTTP_Port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSOAP_HTTP_Port(endpoint);
    }

    public br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType getSOAP_HTTP_Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPSOAP_HTTP_BindingStub _stub = new br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPSOAP_HTTP_BindingStub(portAddress, this);
            _stub.setPortName(getSOAP_HTTP_PortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSOAP_HTTP_PortEndpointAddress(java.lang.String address) {
        SOAP_HTTP_Port_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPSOAP_HTTP_BindingStub _stub = new br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPSOAP_HTTP_BindingStub(new java.net.URL(SOAP_HTTP_Port_address), this);
                _stub.setPortName(getSOAP_HTTP_PortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SOAP_HTTP_Port".equals(inputPortName)) {
            return getSOAP_HTTP_Port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.maritima.com.br", "EmailService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.maritima.com.br", "SOAP_HTTP_Port"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SOAP_HTTP_Port".equals(portName)) {
            setSOAP_HTTP_PortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
