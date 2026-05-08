package br.com.maritima.www;

public class MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy implements br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType {
  private String _endpoint = null;
  private br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType mSE_ServicosCorporativos_EMAIL_MSPPortType = null;
  
  public MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy() {
    _initMSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy();
  }
  
  public MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initMSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy();
  }
  
  private void _initMSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy() {
    try {
      mSE_ServicosCorporativos_EMAIL_MSPPortType = (new br.com.maritima.www.EmailServiceLocator()).getSOAP_HTTP_Port();
      if (mSE_ServicosCorporativos_EMAIL_MSPPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mSE_ServicosCorporativos_EMAIL_MSPPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mSE_ServicosCorporativos_EMAIL_MSPPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mSE_ServicosCorporativos_EMAIL_MSPPortType != null)
      ((javax.xml.rpc.Stub)mSE_ServicosCorporativos_EMAIL_MSPPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortType getMSE_ServicosCorporativos_EMAIL_MSPPortType() {
    if (mSE_ServicosCorporativos_EMAIL_MSPPortType == null)
      _initMSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy();
    return mSE_ServicosCorporativos_EMAIL_MSPPortType;
  }
  
  public void sendEmail(java.lang.String from, java.lang.String to, java.lang.String cc, java.lang.String bcc, java.lang.String replyTo, java.lang.String subject, java.lang.String body, java.lang.String nameFile_1, java.lang.String attachB64_1, java.lang.String nameFile_2, java.lang.String attachB64_2, java.lang.String nameFile_3, java.lang.String attachB64_3, java.lang.String nameFile_4, java.lang.String attachB64_4, java.lang.String nameFile_5, java.lang.String attachB64_5, java.lang.String nameFile_6, java.lang.String attachB64_6, java.lang.String nameFile_7, java.lang.String attachB64_7, java.lang.String nameFile_8, java.lang.String attachB64_8, java.lang.String nameFile_9, java.lang.String attachB64_9, java.lang.String nameFile_10, java.lang.String attachB64_10) throws java.rmi.RemoteException{
    if (mSE_ServicosCorporativos_EMAIL_MSPPortType == null)
      _initMSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy();
    mSE_ServicosCorporativos_EMAIL_MSPPortType.sendEmail(from, to, cc, bcc, replyTo, subject, body, nameFile_1, attachB64_1, nameFile_2, attachB64_2, nameFile_3, attachB64_3, nameFile_4, attachB64_4, nameFile_5, attachB64_5, nameFile_6, attachB64_6, nameFile_7, attachB64_7, nameFile_8, attachB64_8, nameFile_9, attachB64_9, nameFile_10, attachB64_10);
  }
  
  
}