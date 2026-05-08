/*
 * Criado em 23/10/2007
 *
 * Para alterar o gabarito para este arquivo gerado vá para
 * Janela&gt;Preferências&gt;Java&gt;Geração de Códigos&gt;Código e Comentários
 */
package bean;

import java.io.Serializable;
import java.util.Vector;
/**
 * @author tjgomes
 *
 * Para alterar o gabarito para este comentário do tipo gerado vá para
 * Janela&gt;Preferências&gt;Java&gt;Geração de Códigos&gt;Código e Comentários
 */
public class CorretorBean implements Serializable {

	private static final long serialVersionUID = 3117030616366338542L;

	private Vector codigo;
	private Vector descricao;
	
	public CorretorBean (){
		codigo= new Vector();
		descricao= new Vector();
	}
	public void addCodigo(String i){
		codigo.add(i);		
	}
	public void addDescricao(String i){
		descricao.add(i);		
	}
	public String getCodigo(int i) {
		String ret = (String)codigo.elementAt(i);
		return ret;
	}
	public String getDescricao(int i) {
		String ret = (String)descricao.elementAt(i);
		return ret;
	}
	public int posDescricao(String v){
			return(descricao.indexOf(v));
	}
	
	public int proxCorretor(String ini){
		for (int i=0;i<descricao.size();i++)
			if (getDescricao(i).startsWith(ini))
				return(i);
			return(-1);    
	}

	public int size(){
		return(descricao.size());
	}
}
