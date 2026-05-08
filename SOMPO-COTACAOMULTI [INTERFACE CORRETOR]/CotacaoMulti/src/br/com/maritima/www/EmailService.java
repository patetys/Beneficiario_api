/**
 * EmailService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.maritima.www;

public interface EmailService extends javax.xml.rpc.Service {
    public java.lang.String getSOAP_HTTP_PortAddress();

    public br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType getSOAP_HTTP_Port() throws javax.xml.rpc.ServiceException;

    public br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType getSOAP_HTTP_Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
